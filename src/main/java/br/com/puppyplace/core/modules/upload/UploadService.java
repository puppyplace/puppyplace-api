package br.com.puppyplace.core.modules.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	public String uploadFile(MultipartFile file);
}
