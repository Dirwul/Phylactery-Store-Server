package tech.dirwul.phylacterystoreserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StorageService {

	private static final Logger log = LoggerFactory.getLogger(StorageService.class);

	private final MinioService minioService;

	@Autowired
	public StorageService(MinioService minioService) {
		this.minioService = minioService;
	}

	public void setupUserStorage(UUID id) {
		log.info("Preparing user storage for user id {}", id);
		minioService.createUserBucket(id);
		log.info("Storage service setup complete.");
	}
}
