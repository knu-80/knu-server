package kr.co.knuserver.infra.s3;

import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${admin.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${admin.cloud.aws.region.static}")
    private String region;

    public String upload(MultipartFile file, String folder) {
        String key = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
        } catch (IOException e) {
            throw new BusinessException(BusinessErrorCode.INTERNAL_SERVER_ERROR);
        }
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
    }

    public void delete(String imageUrl) {
        String key = imageUrl.substring(imageUrl.indexOf(".amazonaws.com/") + ".amazonaws.com/".length());
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build()
        );
    }
}
