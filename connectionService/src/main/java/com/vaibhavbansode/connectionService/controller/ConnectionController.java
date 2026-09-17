package com.vaibhavbansode.connectionService.controller;

import com.vaibhavbansode.connectionService.entity.Person;
import com.vaibhavbansode.connectionService.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final PersonService personService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Long>> getFirstDegreeConnection(@PathVariable Long userId, @RequestHeader("X-User-Id") Long userIdFromHeader) {
        log.info("getFirstDegreeConnection({}, {})", userId, userIdFromHeader);
        var personList = personService.getFirstDegreeConnection(userId);
        return ResponseEntity.ok().body(personList);
    }
    @GetMapping("/{userId}/second-degree")
    public ResponseEntity<List<Person>> findSecondDegreeConnections(@PathVariable Long userId) {
        var personList = personService.findSecondDegreeConnections(userId);
        return ResponseEntity.ok(personList);
    }
    @GetMapping("/{userID}/third-degree")
    public ResponseEntity<List<Person>> findThirdDegreeConnections(@PathVariable Long userID) {
        var personList = personService.findThirdDegreeConnections(userID);
        return ResponseEntity.ok(personList);
    }

//    @PostMapping("/internal")
//    public ResponseEntity<Void> createPerson(
//            @RequestParam Long userId) {
//
//        personService.createPerson(userId);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
