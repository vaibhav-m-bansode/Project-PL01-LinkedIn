package com.vaibhavbansode.notificationService.consumer;

import com.vaibhavbansode.notificationService.service.NotificationService;
import com.vaibhavbansode.postsService.event.PostCreated;
import com.vaibhavbansode.postsService.event.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "post_liked_topic",
            groupId = "notification-service"
    )
    public void consumePostLiked(PostLiked postLikedEvent) {
        log.info("received post liked event: {}", postLikedEvent);
        notificationService.consumePostLiked(postLikedEvent);
    }

    @KafkaListener(
            topics = "post_created_topic",
            groupId = "notification-service"
    )
    public void consumePostCreated(PostCreated event) {
        log.info("received post created event: {}", event);

        notificationService.consumePostCreated(event);
    }
}
