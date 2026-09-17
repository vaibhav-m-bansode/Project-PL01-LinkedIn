package com.vaibhavbansode.notificationService.service;

import com.vaibhavbansode.notificationService.entity.Notification;
import com.vaibhavbansode.notificationService.repository.NotificationRepository;
import com.vaibhavbansode.postsService.event.PostCreated;
import com.vaibhavbansode.postsService.event.PostLiked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void consumePostLiked(PostLiked event) {

        String message = String.format(
                "Your post with Id: %d was liked by the user with Id: %d",
                event.getPostId(),
                event.getLikedByUserId()
        );

        Notification notification = new Notification();

        notification.setMessage(message);
        notification.setUserId(event.getOwnerId());

        notificationRepository.save(notification);
    }

    public void consumePostCreated(PostCreated event) {

        String message = String.format(
                "Your Connection User with Id: %d has posted a new Post: %s",
                event.getUserId(),
                event.getMessage()
        );
        List<Long> connections = event.getConnections();
        for (Long connectionId : connections) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUserId(connectionId);
            notificationRepository.save(notification);
        }

    }
}