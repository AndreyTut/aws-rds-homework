package ua.study.my.awsrdshw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.study.my.awsrdshw.model.ImageInfoDto;
import ua.study.my.awsrdshw.service.ImageInfoService;

import java.util.List;

@RestController
@RequestMapping("/image-info")
public class InfoController {
    private final ImageInfoService infoService;

    public InfoController(ImageInfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public List<ImageInfoDto> getAll() {
        return infoService.getAll();
    }

    @GetMapping("/by-name/{name}")
    public ImageInfoDto getByName(@PathVariable String name) {
        return infoService.find(name);
    }

    @GetMapping("/random")
    public ImageInfoDto getRandom() {
        return infoService.getRandom();
    }
}
