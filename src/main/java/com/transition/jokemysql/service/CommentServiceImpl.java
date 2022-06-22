package com.transition.jokemysql.service;


import com.transition.jokemysql.data.inputDto.CommentDeleteDto;
import com.transition.jokemysql.data.inputDto.CommentInputDto;
import com.transition.jokemysql.data.inputDto.JokeCommentInputDto;
import com.transition.jokemysql.data.model.Comment;
import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.outputDto.CommentResponseDto;
import com.transition.jokemysql.data.outputDto.Status;
import com.transition.jokemysql.data.repository.CommentRepository;
import com.transition.jokemysql.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    JokeServiceImpl jokeServiceImpl;

    @Autowired
    CommentRepository commentRepository;
    @Override
    public CommentResponseDto createComment(CommentInputDto inputDto) {
        Joke foundJoke = jokeServiceImpl.findJokeById(inputDto.getJokeId());
        if(foundJoke != null){
            Comment comment = new Comment();
            comment.setComment(inputDto.getWords());
            comment.setJokeId(foundJoke);
            Comment savedComment = commentRepository.save(comment);
            return new CommentResponseDto(Status.SUCCESS,savedComment);
        }else {
            throw new ApplicationException("Joke not found");
        }

    }

    @Override
    public CommentResponseDto removeCommentFromJoke(CommentDeleteDto deleteDto) {
        Joke foundJoke = jokeServiceImpl.findJokeById(deleteDto.getJokeId());
        if(foundJoke!=null){
            Optional<Comment> commentRepo = commentRepository.findById(deleteDto.getCommentId());
            if(commentRepo.isPresent()){
                commentRepository.delete(commentRepo.get());
            }else {
                throw new ApplicationException("Comment does not exist");
            }

        }else{
            throw new ApplicationException("Joke is not found");
        }

        return  new CommentResponseDto(Status.SUCCESS);
    }

    @Override
    public CommentResponseDto createJokeComment(Integer id, JokeCommentInputDto jokeInputDto) {
        Joke foundJoke = jokeServiceImpl.findJokeById(id);
        if(foundJoke == null){
            throw new ApplicationException("Joke not found");
        }
        Comment comment = new Comment();
        comment.setComment(jokeInputDto.getWords());
        comment.setJokeId(foundJoke);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(Status.SUCCESS,savedComment);
    }

    @Override
    public CommentResponseDto getAllCommentOfAJoke(Integer jokeId) {
        List<Comment> comments =  commentRepository.findCommentByJokeId(jokeId);
        if(comments==null){
            throw new ApplicationException("comments not found");
        }
        return new CommentResponseDto(comments,Status.SUCCESS);
    }

    @Override
    public CommentResponseDto deleteCommentByItsId(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        Comment foundComment= comment.get();
        commentRepository.delete(foundComment);
        return new CommentResponseDto(Status.SUCCESS);
    }

    public Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId).get();
    }
}
