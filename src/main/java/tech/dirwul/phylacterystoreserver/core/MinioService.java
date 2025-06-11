package tech.dirwul.phylacterystoreserver.core;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MinioService {

	private final Logger log = LoggerFactory.getLogger(MinioService.class);

	private final MinioClient minioClient;

	public MinioService(
		@Value("${minio.user}") String minioUser,
		@Value("${minio.password}") String minioPass
	) {
		this.minioClient = MinioClient.builder()
			.endpoint("http://localhost:9000")
			.credentials(minioUser, minioPass)
			.build();
	}

	public void createUserBucket(UUID id) {
		try {
			boolean bucketExists =
				minioClient.bucketExists(BucketExistsArgs.builder().bucket(id.toString()).build());
			if (bucketExists) {
				log.debug("User bucket already exists: {}", id);
				return;
			}
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(id.toString()).build());
			log.info("Bucket created: {}", id);
		} catch (Exception e) {
			log.error("Error on checking/creating bucket: {}", id);
		}
	}
}
