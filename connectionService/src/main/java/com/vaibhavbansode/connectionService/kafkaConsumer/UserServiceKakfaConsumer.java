package com.vaibhavbansode.connectionService.kafkaConsumer;

import com.vaibhavbansode.connectionService.service.PersonService;
import com.vaibhavbansode.userService.event.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceKakfaConsumer {
    private final PersonService personService;

    @KafkaListener(topics = "user_created_topic",
            groupId = "connection-service")
    private void listenUserCreatedEvent(UserCreated userCreated) {
        log.info("received user created event {}", userCreated);
        personService.createPerson(userCreated);
    }

    ;

}
