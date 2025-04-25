package com.adv.userservice.repository;

import com.adv.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findByFirstName(String firstName);

    Optional<User>  findByEmail(String email);

    /*@Query("MATCH (u:User)-[:FRIENDS_WITH]->(f:User) WHERE u.username = $username RETURN f")
    List<User> findFirstLevelFriendsByUsername(String username);*/

}

