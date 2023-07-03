package ua.study.my.awsrdshw.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class LambdaTriggerService {

    @Value("${amazon.lambda.url}")
    private String lambdaUrl;
    private final HttpClient httpClient;

    public LambdaTriggerService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void triggerLambda() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(lambdaUrl))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
