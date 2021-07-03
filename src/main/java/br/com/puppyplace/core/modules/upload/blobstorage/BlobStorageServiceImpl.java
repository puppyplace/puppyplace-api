package br.com.puppyplace.core.modules.upload.blobstorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.util.Context;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobContainerProperties;
import com.azure.storage.blob.models.BlobCopyInfo;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobListDetails;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.blob.models.BlobRange;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.models.DownloadRetryOptions;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.sas.BlobContainerSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.common.sas.SasProtocol;

@Service
public class BlobStorageServiceImpl implements BlobStorageService {

	private final static Logger LOGGER = LoggerFactory.getLogger(BlobStorageServiceImpl.class);
	private static final Integer MAX_RETRY_REQUESTS = 5;

	@Value("${blob-storage-account-name}")
	private String accountName;

	@Value("${blob-storage-account-key}")
	private String accountKey;

	@Value("${blob-storage-endpoint-suffix}")
	private String endpointSuffix;

	private BlobServiceClient client = null;

	public BlobStorageServiceImpl() {

	}

	private BlobServiceClient getClient() {
		if (client == null) {
			BlobServiceClientBuilder builder = new BlobServiceClientBuilder();
			builder.connectionString("DefaultEndpointsProtocol=https;AccountName=" + this.accountName + ";AccountKey="
					+ this.accountKey + ";EndpointSuffix=" + this.endpointSuffix + ";");
			client = builder.buildClient();
		}
		return client;
	}

	private BlobContainerClient getContainer(String containerName) {
		BlobContainerClient container = getClient().getBlobContainerClient(containerName);
		if (!container.exists()) {
			container = getClient().createBlobContainer(containerName);
			container.setAccessPolicy(PublicAccessType.BLOB, null);
		}
		return container;
	}

	private BlobClient getBlobByContainerAndFileName(String containerName, String fileName) {
		BlobContainerClient container = getContainer(containerName);
		return container.getBlobClient(fileName);
	}

	@Override
	public String criarContainer(String containerName) {
		BlobContainerClient container = getClient().createBlobContainer(containerName);
		BlobContainerProperties properties = container.getProperties();
		return String.format("Name: %s, LastModified: %s", container.getBlobContainerName(),
				properties.getLastModified());
	}

	@Override
	public void deleteContainer(String containerName) {
		BlobContainerClient container = getClient().getBlobContainerClient(containerName);

		if (container.exists()) {
			container.delete();
		}
	}

	@Override
	public List<String> listarContainers() {
		try {
			return getClient().listBlobContainers().stream().map(BlobContainerItem::getName)
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void upload(String containerName, String fileName, byte[] file, String contentType) {
		BlobClient blobClient = getBlobByContainerAndFileName(containerName, fileName);	
		BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();
		var inputStream = new ByteArrayInputStream(file);
		
		BlobHttpHeaders blobHttpHeaders = new BlobHttpHeaders();		
		blockBlobClient.upload(inputStream, file.length);
		
		blobHttpHeaders.setContentType(contentType);		
		blockBlobClient.setHttpHeaders(blobHttpHeaders);
	}

	@Override
	public String copyBlob(String sourceContainerName, String sourceFileName, String destinationContainerName,
			String destinationFileName) {
		BlobClient sourceBlob = getBlobByContainerAndFileName(sourceContainerName, sourceFileName);
		BlobClient destinationBlob = getBlobByContainerAndFileName(destinationContainerName, destinationFileName);

		try {
			final SyncPoller<BlobCopyInfo, Void> poller = destinationBlob.beginCopy(sourceBlob.getBlobUrl(),
					Duration.ofSeconds(1));
			PollResponse<BlobCopyInfo> pollResponse = poller.poll();
			return pollResponse.getValue().getVersionId();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public void uploadBlock(String containerName, String fileName, byte[] chunk, String blockId, Long count) {
		BlobClient blobClient = getBlobByContainerAndFileName(containerName, fileName);
		BlockBlobClient blockClient = blobClient.getBlockBlobClient();

		InputStream input = null;
		try {
			input = new ByteArrayInputStream(chunk);
			blockClient.stageBlock(blockId, input, count);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public String commitBlocks(String containerName, String fileName, List<String> blockIds, boolean overwrite) {
		try {
			BlobClient blobClient = getBlobByContainerAndFileName(containerName, fileName);
			BlockBlobClient blockClient = blobClient.getBlockBlobClient();
			BlockBlobItem item = blockClient.commitBlockList(blockIds, overwrite);
			return item.getVersionId();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public byte[] download(String containerName, String fileName) {
		BlobClient blobClient = getBlobByContainerAndFileName(containerName, fileName);
		ByteArrayOutputStream file = new ByteArrayOutputStream();

		try {
			blobClient.download(file);
			return file.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public ByteArrayOutputStream download(String containerName, String fileName, Long offset, Long count) {
		BlobClient blobClient = getBlobByContainerAndFileName(containerName, fileName);
		BlobRange range = new BlobRange(offset, count);
		DownloadRetryOptions options = new DownloadRetryOptions().setMaxRetryRequests(MAX_RETRY_REQUESTS);
		ByteArrayOutputStream file = new ByteArrayOutputStream();

		try {
			blobClient.downloadWithResponse(file, range, options, null, false, null, Context.NONE);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public List<String> listarBlobs(String containerName, String path) {
		try {
			BlobContainerClient container = getContainer(containerName);
			ListBlobsOptions options = new ListBlobsOptions();
			BlobListDetails details = new BlobListDetails();
			details.setRetrieveMetadata(true);

			if (path != null && path != "") {
				options.setPrefix(path);
			}
			options.setDetails(details);

			return container.listBlobs(options, Duration.ofSeconds(15)).stream().map(BlobItem::getName)
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	public BlobProperties getProperties(String containerName, String fileName) {
		try {
			BlobContainerClient container = getClient().getBlobContainerClient(containerName);
			BlobClient blob = container.getBlobClient(fileName);
			return blob.getProperties();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Deprecated
	@Override
	public List<String> listarRevisoes(String containerName, String fileName) {
		try {
			BlobContainerClient _client = new BlobContainerClientBuilder()
					.credential(new StorageSharedKeyCredential(this.accountName, this.accountKey))
					.endpoint(String.format("https://%s.%s/%s/%s?include=versions", this.accountName,
							this.endpointSuffix, containerName, fileName))
					.buildClient();

			return _client.listBlobs().stream()
					.map(item -> String.format("%s :: %s :: %s", item.getName(), item.getVersionId(), item.isDeleted()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	@Override
	public String getDownloadUrl(String blobname, String containerName, String versionBlob, String filename) {
		try {
			BlobContainerSasPermission blobContainerSasPermission = new BlobContainerSasPermission()
					.setReadPermission(true);

			BlobServiceSasSignatureValues builder = new BlobServiceSasSignatureValues(OffsetDateTime.now().plusSeconds(30),	blobContainerSasPermission);
			
			builder.setProtocol(SasProtocol.HTTPS_ONLY);
			builder.setStartTime(OffsetDateTime.now());
			builder.setContentDisposition("attachment;filename=" + filename);

			BlobContainerClient container = getClient().getBlobContainerClient(containerName);
			BlobClient client = container.getBlobClient(blobname);

			String sas = client.generateSas(builder);
			
			return String.format("https://%s.blob.%s/%s/%s?%s", client.getAccountName(), this.endpointSuffix, containerName,
					blobname, sas);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getDirectUrl(String blobname, String containerName) {
		return String.format("https://%s.blob.%s/%s/%s", client.getAccountName(), this.endpointSuffix, containerName,
				blobname);
	}
}
