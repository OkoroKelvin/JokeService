package com.transition.jokemysql.data.outputDto;

import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.inputDto.PageInfo;

import java.util.ArrayList;
import java.util.List;

public class JokeCompositeResponseDto extends StandardResponseDto{
    private JokeComposite jokeWithComment;
    private List<JokeComposite> jokeWithComments = new ArrayList<>();



    public JokeCompositeResponseDto(){

    }

    public JokeCompositeResponseDto(Status status){
        this.status = status;
    }

    public JokeCompositeResponseDto(Status status, ApiFieldError data) {
        this.data = data;
        this.status = status;
    }

    public JokeCompositeResponseDto(List<JokeComposite> jokeWithComments, Status status, PageInfo page){
        super(status);
        this.jokeWithComments = jokeWithComments;
        this.page = page;
    }

    public JokeCompositeResponseDto(JokeComposite jokeWithComment, Status status) {
        super(status);
        this.jokeWithComment = jokeWithComment;
    }

    public JokeCompositeResponseDto(Status status, JokeComposite jokeWithComment) {
        super(status);
        this.jokeWithComment= jokeWithComment;
    }

    public JokeCompositeResponseDto(List<JokeComposite> jokeWithComments, Status status) {
        super(status);
        this.jokeWithComments = jokeWithComments;
    }

    public JokeComposite getJokeWithComment() {
        return jokeWithComment;
    }

    public void setJokeWithComment(JokeComposite jokeWithComment) {
        this.jokeWithComment = jokeWithComment;
    }

    public List<JokeComposite> getJokeWithComments() {
        return jokeWithComments;
    }

    public void setJokeWithComments(List<JokeComposite> jokeWithComments) {
        this.jokeWithComments = jokeWithComments;
    }
}
