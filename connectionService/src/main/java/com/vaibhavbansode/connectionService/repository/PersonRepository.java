package com.vaibhavbansode.connectionService.repository;

import com.vaibhavbansode.connectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

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

    // Checks whether A and B are already connected,
    // regardless of relationship direction.
    @Query("""
            MATCH (p1:Person)-[:CONNECTED_TO]-(p2:Person)
            WHERE p1.userId = $userId1
              AND p2.userId = $userId2
            RETURN COUNT(*) > 0
            """)
    boolean alreadyConnected(Long userId1, Long userId2);


    // Used when SENDING a request.
    // Checks both A -> B and B -> A.
    @Query("""
            MATCH (p1:Person), (p2:Person)
            WHERE p1.userId = $userId1
              AND p2.userId = $userId2
            
            RETURN EXISTS {
                MATCH (p1)-[:REQUESTED_TO]-(p2)
            }
            """)
    boolean connectionRequestExistsBetween(Long userId1, Long userId2);


    // Used when ACCEPTING/REJECTING.
    // Checks specifically sender -> receiver.
    @Query("""
            MATCH (p1:Person)-[:REQUESTED_TO]->(p2:Person)
            WHERE p1.userId = $senderId
              AND p2.userId = $receiverId
            RETURN COUNT(*) > 0
            """)
    boolean connectionRequestExistsFromSender(
            Long senderId,
            Long receiverId
    );


    // Create request in one direction.
    @Query("""
            MATCH (p1:Person), (p2:Person)
            WHERE p1.userId = $senderId
              AND p2.userId = $receiverId
            
            MERGE (p1)-[:REQUESTED_TO]->(p2)
            """)
    void addConnectionRequest(Long senderId, Long receiverId);


    // Accept request.
    //
    // Delete BOTH possible pending requests:
    // A -> B
    // B -> A
    //
    // Then create one connection.
    @Query("""
            MATCH (p1:Person), (p2:Person)
            WHERE p1.userId = $senderId
              AND p2.userId = $receiverId
            
            OPTIONAL MATCH (p1)-[r1:REQUESTED_TO]->(p2)
            OPTIONAL MATCH (p2)-[r2:REQUESTED_TO]->(p1)
            
            DELETE r1, r2
            
            MERGE (p1)-[:CONNECTED_TO]->(p2)
            """)
    void acceptConnectionRequest(Long senderId, Long receiverId);


    // Reject ONLY the request sender -> receiver.
    @Query("""
            MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)
            WHERE p1.userId = $senderId
              AND p2.userId = $receiverId
            
            DELETE r
            """)
    void rejectConnectionRequest(Long senderId, Long receiverId);

    boolean existsByUserId(Long userId);
}