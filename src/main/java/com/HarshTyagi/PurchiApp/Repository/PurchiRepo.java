package com.HarshTyagi.PurchiApp.Repository;

import com.HarshTyagi.PurchiApp.domain.Purchi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchiRepo extends MongoRepository<Purchi,String> {

    List<Purchi> findByEmail(String email);

    @Query("{'email': ?0, 'troliHolder': ?1}")
    List<Purchi> findByEmailAndTroliHolderName(String email,String troliHolder);

    @Query("{'email': ?0, 'purchiHolderName': ?1}")
    List<Purchi> findByEmailAndPurchiHolderName(String email,String purchiHolderName);
}
