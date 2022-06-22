package com.transition.jokemysql.data.repository;

import com.transition.jokemysql.data.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JokeRepository extends JpaRepository<Joke,Integer> {

    @Query(nativeQuery = true,value = "select * from joke ORDER BY likes DESC")
    List<Joke> findAllJokesFromBestToLeastLikes();

    @Query(nativeQuery = true,value = "select * from joke ORDER BY likes ASC")
    List<Joke> findAllJokesFromLeastToBestLikes();


    @Query(nativeQuery = true, value = "select * from joke WHERE user_id=:userId")
    List<Joke> findJokesByUserId(Integer userId);
}
