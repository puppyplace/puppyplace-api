package br.com.puppyplace.core.modules.upload.blobstorage;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.azure.storage.blob.models.BlobProperties;

public interface BlobStorageService {
	String criarContainer(String containerName);
	void deleteContainer(String containerName);
	List<String> listarContainers();
	void upload(String containerName, String blobname, byte[] file, String contentType);
	String copyBlob(String sourceContainerName, String sourceFileName, String destinationContainerName, String destinationFileName);
	void uploadBlock(String containerName, String fileName, byte[] chunk, String blockId, Long contentLength);
	String commitBlocks(String containerName, String fileName, List<String> blockIds, boolean overwrite);
	byte[] download(String containerName, String fileName);
	ByteArrayOutputStream download(String containerName, String fileName, Long offset, Long count);	
	List<String> listarRevisoes(String containerName, String fileName);
	List<String> listarBlobs(String containerName, String path);
	BlobProperties getProperties(String containerName, String fileName);	
	String getDownloadUrl(String blobname, String containerName, String versionBlob, String filename);
	String getDirectUrl(String blobname, String containerName);
}
