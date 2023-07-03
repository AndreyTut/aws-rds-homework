package ua.study.my.awsrdshw.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class S3Service {

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public Boolean saveToBucket(MultipartFile file, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(bucketName, fileName, inputStream, metadata);
        } catch (IOException e) {
            log.error("Failed saving object to S3", e);
            return false;
        }
        return true;
    }

    public File downloadFromBucket(String name) {
        S3Object object = s3Client.getObject(bucketName, name);
        File file = new File(name);
        try (InputStream inputStream = object.getObjectContent(); FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(inputStream.readAllBytes());
        } catch (IOException e) {
            log.error("Failed downloading image name = {}", name, e);
        }
        return file;
    }

    public Boolean deleteFromBucket(String fileName) {
        try {
            s3Client.deleteObject(bucketName, fileName);
        } catch (SdkClientException e) {
            log.error("Failed delete object name = {}", fileName, e);
            return false;
        }
        return true;
    }
}
