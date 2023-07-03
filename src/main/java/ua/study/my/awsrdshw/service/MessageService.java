package ua.study.my.awsrdshw.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.UnsubscribeRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.study.my.awsrdshw.entity.ImageInfo;
import ua.study.my.awsrdshw.entity.SubscriptionInfo;

import java.util.List;

@Slf4j
@Service
public class MessageService {

    private final AmazonSNS snsClient;
    private final AmazonSQS sqsClient;
    private final SubscriptionService subscriptionService;
    @Value("${amazon.s3.bucket.name}")
    private String bucketName;
    @Value("${amazon.queue.url}")
    private String queueUrl;
    @Value("${amazon.topic.arn}")
    private String topicArn;

    public MessageService(AmazonSNS snsClient, AmazonSQS sqsClient, SubscriptionService subscriptionService) {
        this.snsClient = snsClient;
        this.sqsClient = sqsClient;
        this.subscriptionService = subscriptionService;
    }

    public void sendUploadNotification(ImageInfo info) {
        String message = constructMessage(info);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
        sqsClient.sendMessage(sendMessageRequest);
    }

//    @Scheduled(fixedDelay = 60000)
    public void checkQueuePutToTopic() {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(10);
        List<Message> messages = sqsClient.receiveMessage(request).getMessages();
        messages.forEach(this::publishToTopicAndDelete);
    }

    public void subscribeEmail(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest()
                .withProtocol("email")
                .withTopicArn(topicArn)
                .withEndpoint(email)
                .withReturnSubscriptionArn(true);
        String subscriptionArn = snsClient.subscribe(subscribeRequest).getSubscriptionArn();
        subscriptionService.createSubscription(new SubscriptionInfo(email, subscriptionArn));
        log.info("Subscription for email {} added, subscription arn: {}", email, subscriptionArn);
    }

    public void unsubscribeEmail(String email) {
        UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest(
                subscriptionService.getSubscription(email).getSubscriptionArn()
        );
        snsClient.unsubscribe(unsubscribeRequest);
        subscriptionService.deleteSubscription(email);
        log.info("Subscription for email {} cancelled", email);
    }

    private void publishToTopicAndDelete(Message message) {
        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(message.getBody());
        snsClient.publish(publishRequest);

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest()
                .withQueueUrl(queueUrl)
                .withReceiptHandle(message.getReceiptHandle());
        sqsClient.deleteMessage(deleteMessageRequest);
    }

    private String constructMessage(ImageInfo info) {
        return "New image was uploaded" +
                System.lineSeparator() +
                "Image metadata:" +
                System.lineSeparator() +
                "size: " + info.getSize() + " Bytes" +
                System.lineSeparator() +
                "name: " + info.getName() +
                System.lineSeparator() +
                "extension: " + info.getExtension() +
                System.lineSeparator() +
                "link for download: " +
                String.format("https://%s.s3.amazonaws.com/%s", bucketName, info.getName());
    }
}
