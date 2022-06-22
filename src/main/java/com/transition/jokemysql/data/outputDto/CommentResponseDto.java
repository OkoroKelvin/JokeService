package com.transition.jokemysql.data.outputDto;

import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.inputDto.PageInfo;
import com.transition.jokemysql.data.model.Comment;
import com.transition.jokemysql.data.model.Joke;

import java.util.List;

public class CommentResponseDto extends StandardResponseDto {
    private List<Comment> comments;
    private Comment comment;


    public CommentResponseDto(){

    }

    public CommentResponseDto(Status status){
        this.status = status;
    }

    public CommentResponseDto(Status status, ApiFieldError data) {
        this.data = data;
        this.status = status;
    }

    public CommentResponseDto(List<Comment> comments, Status status, PageInfo page){
        super(status);
        this.comments = comments;
        this.page = page;
    }

    public CommentResponseDto(Comment comment, Status status) {
        super(status);
        this.comment = comment;
    }

    public CommentResponseDto(Status status, Comment comment) {
        super(status);
        this.comment= comment;
    }

    public CommentResponseDto(List<Comment> comments, Status status) {
        super(status);
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
