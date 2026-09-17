package com.vaibhavbansode.connectionService.repository;

import com.vaibhavbansode.connectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person,Long> {

    @Query("""
            MATCH (p:Person {currentUserId: $currentUserId})-[:CONNECTED_TO*2..2]-(q:Person)
            WHERE p <> q
            RETURN DISTINCT q
            """)
    List<Person> getSecondDegreeConnectionsByUserId(Long userID);

    @Query("""
            MATCH (p:Person {currentUserId: $currentUserId})-[:CONNECTED_TO*3..]-(q:Person)
            WHERE p <> q
            RETURN DISTINCT q
            """)
    List<Person> getThirdDegreeConnectionsByUserId(Long userID);

    @Query("""
            MATCH (p:Person {currentUserId: $currentUserId})-[:CONNECTED_TO*1..1]-(q:Person)
            WHERE p <> q
            RETURN DISTINCT q
            """)
    List<Long> getFirstDegreeConnection(Long currentUserId);

    Optional<Person> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
