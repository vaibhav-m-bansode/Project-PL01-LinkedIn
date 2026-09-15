package com.vaibhavbansode.connectionService.service;

import com.vaibhavbansode.connectionService.entity.Person;
import com.vaibhavbansode.connectionService.repository.PersonRepository;
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

    public List<Person> findAllConnections(Long userId) {
        log.info("finding all connections from user {}", userId);
        return personRepository.getAllConnections(userId);
    }
}
