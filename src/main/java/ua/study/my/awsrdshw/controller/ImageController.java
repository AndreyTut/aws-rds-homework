package ua.study.my.awsrdshw.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.study.my.awsrdshw.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public void upload(@RequestPart MultipartFile file, @RequestPart(required = false) String name) {
        imageService.uploadToS3(file, name);
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadByName(@PathVariable String name) {
        Resource resource = new FileSystemResource(imageService.getFile(name));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename:=\"" + name + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/delete/{name}")
    public void delete(@PathVariable(value = "name") String fileName) {
        imageService.delete(fileName);
    }
}
