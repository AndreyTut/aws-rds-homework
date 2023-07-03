package ua.study.my.awsrdshw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.study.my.awsrdshw.service.LambdaTriggerService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/lambda")
public class LambdaTriggerController {

    private final LambdaTriggerService lambdaTriggerService;

    public LambdaTriggerController(LambdaTriggerService lambdaTriggerService) {
        this.lambdaTriggerService = lambdaTriggerService;
    }

    @GetMapping("/trigger")
    public void goLambda() throws URISyntaxException, IOException, InterruptedException {
        lambdaTriggerService.triggerLambda();
    }
}
