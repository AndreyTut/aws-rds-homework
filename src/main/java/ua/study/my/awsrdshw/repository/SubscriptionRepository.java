package ua.study.my.awsrdshw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.study.my.awsrdshw.entity.SubscriptionInfo;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionInfo, String> {
}
