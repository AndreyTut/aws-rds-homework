package ua.study.my.awsrdshw.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_info_id_seq"
    )
    @SequenceGenerator(
            name = "image_info_id_seq",
            allocationSize = 1,
            initialValue = 1000
    )
    private Long id;
    @LastModifiedDate
    private LocalDateTime updated;
    private String name;
    private Long size;
    private String extension;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
