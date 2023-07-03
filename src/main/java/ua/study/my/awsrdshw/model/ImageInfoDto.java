package ua.study.my.awsrdshw.model;

import lombok.Getter;
import lombok.Setter;
import ua.study.my.awsrdshw.entity.ImageInfo;

import java.time.LocalDateTime;

@Setter
@Getter
public class ImageInfoDto {
    private LocalDateTime updated;
    private String name;
    private Long size;
    private String extension;

    public ImageInfoDto(ImageInfo entity) {
        this.updated = entity.getUpdated();
        this.name = entity.getName();
        this.extension = entity.getExtension();
        this.size = entity.getSize();
    }
}
