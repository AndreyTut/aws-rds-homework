package ua.study.my.awsrdshw.service;

import com.google.common.io.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.study.my.awsrdshw.entity.ImageInfo;
import ua.study.my.awsrdshw.model.ImageInfoDto;
import ua.study.my.awsrdshw.repository.ImageInfoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ImageInfoService {

    private final ImageInfoRepository repository;

    public ImageInfoService(ImageInfoRepository repository) {
        this.repository = repository;
    }

    public ImageInfo saveInfo(MultipartFile file, String name) {
        ImageInfo existing = repository.findByName(name);
        if (existing != null) {
            existing.setUpdated(LocalDateTime.now());
            return repository.save(existing);
        }
        ImageInfo info = ImageInfo.builder()
                .name(name)
                .extension(getExtension(file))
                .size(file.getSize())
                .updated(LocalDateTime.now())
                .build();
        return repository.save(info);
    }

    private String getExtension(MultipartFile file) {
        return Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
    }

    public void delete(String fileName) {
        repository.deleteByName(fileName);
    }

    public List<ImageInfoDto> getAll() {
        return repository.findAll().stream()
                .map(ImageInfoDto::new)
                .collect(Collectors.toList());
    }

    public ImageInfoDto find(String name) {
        return Optional.ofNullable(repository.findByName(name))
                .map(ImageInfoDto::new)
                .orElse(null);
    }

    public ImageInfoDto getRandom() {
        List<ImageInfo> infos = repository.findAll();
        Random random = new Random();
        int index = random.nextInt(infos.size());
        return new ImageInfoDto(infos.get(index));
    }
}
