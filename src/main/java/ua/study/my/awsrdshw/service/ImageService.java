package ua.study.my.awsrdshw.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.study.my.awsrdshw.entity.ImageInfo;

import java.io.File;
import java.util.Optional;

@Service
public class ImageService {

    private final S3Service s3Service;
    private final ImageInfoService infoService;
    private final MessageService messageService;

    public ImageService(
            S3Service s3Service,
            ImageInfoService infoService,
            MessageService messageService
    ) {
        this.s3Service = s3Service;
        this.infoService = infoService;
        this.messageService = messageService;
    }

    public void uploadToS3(MultipartFile file, String name) {
        String fileName = Optional.ofNullable(name)
                .orElse(file.getOriginalFilename());
        if (s3Service.saveToBucket(file, fileName)) {
            ImageInfo info = infoService.saveInfo(file, fileName);
            messageService.sendUploadNotification(info);
        }
    }

    public File getFile(String name) {
        return s3Service.downloadFromBucket(name);
    }

    public void delete(String fileName) {
        if (s3Service.deleteFromBucket(fileName)) {
            infoService.delete(fileName);
        }
    }
}
