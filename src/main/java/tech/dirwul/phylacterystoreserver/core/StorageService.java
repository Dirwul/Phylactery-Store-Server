package tech.dirwul.phylacterystoreserver.core;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.dirwul.phylacterystoreserver.data.model.User;

import java.util.List;

@Service
public class StorageService {

	private static final Logger log = LoggerFactory.getLogger(StorageService.class);

	private final MinioClient minioClient;
	private final MinioAdminClient adminClient;

	@Autowired
	public StorageService(MinioClient minioClient, MinioAdminClient minioAdminClient) {
		this.minioClient = minioClient;
		this.adminClient = minioAdminClient;
	}

	public void setupUserStorage(User user) {
		String bucketName = user.getId() + "-bucket";
		String policyName = user.getId() + "-full-access-policy";

		// 1. create bucket
		try {
			if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
				log.debug("Create bucket {} successfully", bucketName);
			}
		} catch (Exception e) {
			log.error("Failed to create bucket: {}", e.getMessage());
		}
		// 2. generate policy
		try {
			String policyJson = generateFullAccessPolicy(bucketName);
			adminClient.addCannedPolicy(policyName, policyJson);
			log.debug("Create policy {} successfully", policyName);
		} catch (Exception e) {
			log.error("Failed to create policy: {}", e.getMessage());
		}
		// 3. create user
		try {
			adminClient.addUser(
				user.getUsername(),
				UserInfo.Status.ENABLED,
				user.getPassword(),
				null,
				List.of());
			log.debug("Minio user {} created successfully", user.getUsername());
		} catch (Exception e) {
			log.error("Failed to add user bucket {} to the minio bucket: {}", user.getUsername(), e.getMessage());
		}
		// 4. set policy to user
		try {
			adminClient.setPolicy(user.getUsername(), false, policyName);
			log.debug("Set policy {} successfully", policyName);
		} catch (Exception e) {
			log.error("Failed to set policy: {}", e.getMessage());
		}
	}

	private String generateFullAccessPolicy(String bucketName) {
		return """
        {
          "Version": "2012-10-17",
          "Statement": [
            {
              "Effect": "Allow",
              "Action": [
                "s3:*"
              ],
              "Resource": [
                "arn:aws:s3:::%s",
                "arn:aws:s3:::%s/*"
              ]
            }
          ]
        }
        """.formatted(bucketName, bucketName);
	}
}
