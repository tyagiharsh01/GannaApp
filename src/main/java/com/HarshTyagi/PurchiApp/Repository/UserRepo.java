package com.HarshTyagi.PurchiApp.Repository;

import com.HarshTyagi.PurchiApp.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    @Query("{'email': ?0, 'password': ?1}")
    Optional<User> findByEmailAndPassword(String email, String password);


}
