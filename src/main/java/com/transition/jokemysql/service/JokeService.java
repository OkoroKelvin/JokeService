package com.transition.jokemysql.service;

import com.transition.jokemysql.data.inputDto.JokeInputDto;
import com.transition.jokemysql.data.model.UserAccount;
import com.transition.jokemysql.data.outputDto.CommentResponseDto;
import com.transition.jokemysql.data.outputDto.JokeResponseDto;
import com.transition.jokemysql.data.outputDto.JokeCompositeResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JokeService {
    JokeResponseDto saveJoke(JokeInputDto joke1);

    //JokeResponseDto findJokeById(Integer jokeId);

    JokeResponseDto removeJoke(Integer jokeId);

    JokeResponseDto likeJoke(Integer jokeId);

    JokeResponseDto findAllJokesByBestToLeastLikes();

    JokeResponseDto findAllJokesByLeastToBestLikes();

   JokeCompositeResponseDto findAllJokesWithItsComment();

    JokeResponseDto findAllJokes();

    JokeResponseDto findJokeByItsId(Integer jokeId);


    JokeResponseDto sendJokeToEmail(Integer jokeId, String email);

    UserAccount findUserByEmail(String username);
}
