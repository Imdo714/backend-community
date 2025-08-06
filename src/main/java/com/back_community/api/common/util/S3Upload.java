package com.back_community.api.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.back_community.global.exception.handleException.S3UploadFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Upload {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        String fileName = generateFileName(dirName, multipartFile);
        ObjectMetadata metadata = createMetadata(multipartFile);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, metadata);
            amazonS3Client.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error("S3 업로드 실패 - 파일명: {}, 에러: {}", fileName, e.getMessage(), e);
            throw new S3UploadFailException("S3 업로드 중 오류가 발생했습니다.");
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private String generateFileName(String dirName, MultipartFile multipartFile) {
        String extension = getExtension(multipartFile);
        return dirName + "/" + UUID.randomUUID() + extension;
    }

    private ObjectMetadata createMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        return metadata;
    }

    private static String getExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return "";
    }

}
