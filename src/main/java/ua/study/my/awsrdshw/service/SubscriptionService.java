package ua.study.my.awsrdshw.service;

import org.springframework.stereotype.Service;
import ua.study.my.awsrdshw.entity.SubscriptionInfo;
import ua.study.my.awsrdshw.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void createSubscription(SubscriptionInfo subscriptionInfo) {
        subscriptionRepository.save(subscriptionInfo);
    }

    public SubscriptionInfo getSubscription(String email) {
        return subscriptionRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Subscription not found, email = " + email));
    }

    public void deleteSubscription(String email) {
        subscriptionRepository.deleteById(email);
    }
}
