package ua.study.my.awsrdshw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.study.my.awsrdshw.service.MessageService;

@RestController
@RequestMapping("/notification")
public class MessageSubscribeController {

    private final MessageService messageService;

    public MessageSubscribeController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/subscribe")
    public void subscribe(@RequestParam String email) {
        messageService.subscribeEmail(email);
    }

    @GetMapping("/unsubscribe")
    public void unsubscribe(@RequestParam String email) {
        messageService.unsubscribeEmail(email);
    }
}
