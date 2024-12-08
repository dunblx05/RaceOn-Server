package com.parting.dippin.core.common.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.api.member.exception.FailedUploadImageException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PresignedUrlGenerator {

    public static final int EXPIRATION_TIME = 1000 * 60 * 10;
    private final AmazonS3 amazonS3;

    public PatchProfileImageResDto generatePreSignedUrl(String bucketPath) {
        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = generatePresignedUrlRequest(bucketPath);

            URL preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            return new PatchProfileImageResDto(preSignedUrl);
        } catch (Exception e) {
            throw new FailedUploadImageException("이미지 업로드 실패");
        }
    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucketPath) {
        UUID uniqueKey = UUID.randomUUID();
        Date expirationTime = generateExpirationTime();

        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucketPath, uniqueKey.toString())
                .withMethod(HttpMethod.PUT)
                .withExpiration(expirationTime);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    private Date generateExpirationTime() {
        Date expirationTime = new Date();
        long expirationTimeMillis = expirationTime.getTime() + EXPIRATION_TIME;
        expirationTime.setTime(expirationTimeMillis);

        return expirationTime;
    }
}
