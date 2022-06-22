package com.transition.jokemysql.data.repository;

import com.transition.jokemysql.data.model.Comment;
import com.transition.jokemysql.data.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Query(nativeQuery = true,value = "select * from comment WHERE joke_id=:jokeId")
    List<Comment> findCommentByJokeId(Integer jokeId);
}
