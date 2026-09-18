package com.vaibhavbansode.connectionService.service;

import com.vaibhavbansode.connectionService.auth.AuthContextHolder;
import com.vaibhavbansode.connectionService.entity.Person;
import com.vaibhavbansode.connectionService.repository.PersonRepository;
import com.vaibhavbansode.userService.event.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;


    public List<Person> findSecondDegreeConnections(Long userID) {
        log.info("finding second degree connections from user {}", userID);
        return personRepository.getSecondDegreeConnectionsByUserId(userID);
    }

    public List<Person> findThirdDegreeConnections(Long userID) {
        log.info("finding third degree connections from user {}", userID);
        return personRepository.getThirdDegreeConnectionsByUserId(userID);
    }

    public List<Long> getFirstDegreeConnection(Long userId) {
        log.info("finding all connections from user {}", userId);
        return personRepository.getFirstDegreeConnection(userId);
    }


    public void createPerson(UserCreated userCreated) {
        log.info("creating person {}", userCreated);
        if (personRepository.existsByUserId(userCreated.getUserId())) {
            throw new RuntimeException("Person already exists for user: " + userCreated.getUserId());
        }

        Person person = new Person();
        person.setUserId(userCreated.getUserId());
        person.setName(userCreated.getName());
        personRepository.save(person);
    }

    public void sendConnectionRequest(Long receiverId) {

        Long senderId = AuthContextHolder.getCurrentUserId();

        log.info("sendConnectionRequest({}, {})", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Sender and Receiver are the same");
        }

        // Check A -> B AND B -> A
        boolean requestExists =
                personRepository.connectionRequestExistsBetween(
                        senderId,
                        receiverId
                );

        if (requestExists) {
            throw new RuntimeException(
                    "Connection request already exists"
            );
        }

        boolean alreadyConnected =
                personRepository.alreadyConnected(
                        senderId,
                        receiverId
                );

        if (alreadyConnected) {
            throw new RuntimeException(
                    "Already connected users, cannot send connection request"
            );
        }

        personRepository.addConnectionRequest(
                senderId,
                receiverId
        );

        log.info("Successfully sent connection request");
    }

    public void acceptConnectionRequest(Long senderId) {

        Long receiverId = AuthContextHolder.getCurrentUserId();

        log.info(
                "accepting connection request: ({}, {})",
                senderId,
                receiverId
        );

        if (senderId.equals(receiverId)) {
            throw new RuntimeException(
                    "Sender and Receiver are the same"
            );
        }

        boolean alreadyConnected =
                personRepository.alreadyConnected(
                        senderId,
                        receiverId
                );

        if (alreadyConnected) {
            throw new RuntimeException(
                    "Already connected users, cannot accept connection request"
            );
        }

        // IMPORTANT:
        // Verify specifically sender -> receiver.
        boolean requestExists =
                personRepository.connectionRequestExistsFromSender(
                        senderId,
                        receiverId
                );

        if (!requestExists) {
            throw new RuntimeException(
                    "No connection request exists, cannot accept without request"
            );
        }

        personRepository.acceptConnectionRequest(
                senderId,
                receiverId
        );

        log.info("Successfully accepted connection request");
    }

    public void rejectConnectionRequest(Long senderId) {

        Long receiverId = AuthContextHolder.getCurrentUserId();

        log.info(
                "Rejecting connection request: ({}, {})",
                senderId,
                receiverId
        );

        if (senderId.equals(receiverId)) {
            throw new RuntimeException(
                    "Sender and Receiver are the same"
            );
        }

        // Verify specifically sender -> receiver
        boolean requestExists =
                personRepository.connectionRequestExistsFromSender(
                        senderId,
                        receiverId
                );

        if (!requestExists) {
            throw new RuntimeException(
                    "No connection request exists, cannot reject connection request"
            );
        }

        personRepository.rejectConnectionRequest(
                senderId,
                receiverId
        );

        log.info("Successfully rejected connection request");
    }
}
