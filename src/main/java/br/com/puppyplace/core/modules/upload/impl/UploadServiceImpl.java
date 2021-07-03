package br.com.puppyplace.core.modules.upload.impl;

import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.puppyplace.core.modules.upload.UploadService;
import br.com.puppyplace.core.modules.upload.blobstorage.BlobStorageService;

@Service
public class UploadServiceImpl implements UploadService {
	
	@Autowired
	private BlobStorageService blobStorageService;
	
	public String uploadFile(MultipartFile file) {
		String containerName = String.valueOf(LocalDate.now().getYear());
		String originalFilename = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFD);		
		String blobname = generateBlobname(originalFilename);
		
		try {
			blobStorageService.upload(containerName, blobname, file.getBytes(), file.getContentType());
		} catch (IOException e) {			
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		return blobStorageService.getDirectUrl(blobname, containerName);
	}

	private String generateBlobname(String originalFilename) {
		StringBuilder sb = new StringBuilder();
		sb.append(getFilename(originalFilename));
		sb.append("_");
		sb.append(System.currentTimeMillis());
		sb.append(getFileExtension(originalFilename));		
		return sb.toString();
	}
	
	private String getFileExtension(String fullname) {
		int indexOfDot = fullname.lastIndexOf(".");
		
		if(indexOfDot < 0) {
			return "";
		}
		
		return fullname.substring(indexOfDot, fullname.length());
	}
	
	private String getFilename(String fullname) {
		int indexOfDot = fullname.lastIndexOf(".");
		
		if(indexOfDot < 0) {
			return fullname;
		}
		
		return fullname.substring(0, indexOfDot);
	}
}
