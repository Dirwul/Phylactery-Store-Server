package tech.dirwul.phylacterystoreserver.config;

import io.minio.MinioClient;
import io.minio.admin.MinioAdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

	@Value("${minio.endpoint}")
	private String endpoint;
	@Value("${minio.root.accessKey}")
	private String rootAccessKey;
	@Value("${minio.root.secretKey}")
	private String rootSecretKey;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
			.endpoint(endpoint)
			.credentials(rootAccessKey, rootSecretKey)
			.build();
	}

	@Bean
	public MinioAdminClient minioAdminClient() {
		return MinioAdminClient.builder()
			.endpoint(endpoint)
			.credentials(rootAccessKey, rootSecretKey)
			.build();
	}
}
