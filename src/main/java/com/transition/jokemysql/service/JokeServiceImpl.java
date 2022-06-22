package com.transition.jokemysql.service;

import com.transition.jokemysql.amgp.ExchangeLink;
import com.transition.jokemysql.amgp.FinalConsumer;
import com.transition.jokemysql.amgp.RabbitMQMessageProducer;
import com.transition.jokemysql.data.inputDto.JokeInputDto;
import com.transition.jokemysql.data.model.Comment;
import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.model.User;
import com.transition.jokemysql.data.model.UserAccount;
import com.transition.jokemysql.data.outputDto.*;
import com.transition.jokemysql.data.repository.CommentRepository;
import com.transition.jokemysql.data.repository.JokeRepository;
import com.transition.jokemysql.data.repository.UserAccountRepository;
import com.transition.jokemysql.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JokeServiceImpl implements  JokeService {
    @Autowired
    JokeRepository jokeRepository;
    @Autowired
    RabbitMQMessageProducer rabbitMQMessageProducer;

    @Autowired
    private ExchangeLink exchangeLink;

    @Autowired
    FinalConsumer finalConsumer;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    public JokeResponseDto saveJoke(JokeInputDto jokeInput) {
        try {
            Joke joke = new Joke();
            joke.setContent(jokeInput.getContent());
            joke.setCreatedDate(LocalDateTime.now());
            joke.setLikes(0);
            Joke savedJoke = jokeRepository.save(joke);
            return new JokeResponseDto(Status.SUCCESS, savedJoke);
        } catch (Exception e) {
            return new JokeResponseDto(Status.INTERNAL_ERROR);
        }
    }


    public Joke findJokeById(Integer jokeId) {
        Optional<Joke> jokeRepo = jokeRepository.findById(jokeId);
        Joke foundJoke = jokeRepo.get();
        return foundJoke;
    }

    @Override
    public JokeResponseDto removeJoke(Integer jokeId) {
        try {
            Joke foundJoke = findJokeById(jokeId);
            List<Comment> comments = commentRepository.findCommentByJokeId(jokeId);
            if (comments != null) {
                commentRepository.deleteAll(comments);
            }
            jokeRepository.delete(foundJoke);
            return new JokeResponseDto(Status.SUCCESS);
        } catch (Exception e) {
            return new JokeResponseDto(Status.INTERNAL_ERROR);
        }
    }

    @Override
    public JokeResponseDto likeJoke(Integer jokeId) {
        Joke foundJoke = findJokeById(jokeId);
        foundJoke.like();
        Joke savedJoke = jokeRepository.save(foundJoke);
        return new JokeResponseDto(savedJoke, Status.SUCCESS);
    }

    @Override
    public JokeResponseDto findAllJokesByBestToLeastLikes() {
        List<Joke> jokes = jokeRepository.findAllJokesFromBestToLeastLikes();
        return new JokeResponseDto(jokes, Status.SUCCESS);
    }

    @Override
    public JokeResponseDto findAllJokesByLeastToBestLikes() {
        List<Joke> jokes = jokeRepository.findAllJokesFromLeastToBestLikes();
        return new JokeResponseDto(jokes, Status.SUCCESS);
    }

    @Override
    public JokeCompositeResponseDto findAllJokesWithItsComment() {
        List<Joke> jokes = jokeRepository.findAll();
        List<JokeComposite> jokeWithComments = new ArrayList<>();

        if (jokes == null) {
            throw new ApplicationException("No Joke available");
        }

        jokes.stream().forEach((joke) -> {
                    JokeComposite jokeWithComment = new JokeComposite();
                    List<Comment> comments = commentRepository.findCommentByJokeId(joke.getId());
                    jokeWithComment.setJoke(joke);


                    if (comments != null) {
                        jokeWithComment.setComment(comments);
                        jokeWithComments.add(jokeWithComment);
                    } else {
                        jokeWithComments.add(jokeWithComment);
                    }
                }
        );
        return new JokeCompositeResponseDto(jokeWithComments, Status.SUCCESS);
    }

    @Override
    public JokeResponseDto findAllJokes() {
        List<Joke> jokes = jokeRepository.findAll();
        return new JokeResponseDto(jokes, Status.SUCCESS);
    }

    @Override
    public JokeResponseDto findJokeByItsId(Integer jokeId) {
        Joke joke = findJokeById(jokeId);
        if (joke == null) {
            throw new ApplicationException("Joke not found");
        }
        return new JokeResponseDto(joke, Status.SUCCESS);
    }

    @Override
    public JokeResponseDto sendJokeToEmail(Integer jokeId, String email) {
        Joke foundJoke = findJokeById(jokeId);
        User newUser = new User();
        newUser.setJoke(foundJoke);
        newUser.setEmail(email);
        rabbitMQMessageProducer.publish(newUser, exchangeLink.getInternalExchange(),
                exchangeLink.getInternalEmailsRoutingKey());
        finalConsumer.jokeConsumer(newUser);
        return new JokeResponseDto(Status.SUCCESS);
    }

    @Override
    public UserAccount findUserByEmail(String username) {
        UserAccount account = userAccountRepository.findByEmail(username);
        if(account == null){
            throw new ApplicationException("Invalid Email");
        }
        return account;
    }
}
