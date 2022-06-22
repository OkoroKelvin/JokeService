package com.transition.jokemysql.data.outputDto;

import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.inputDto.PageInfo;
import com.transition.jokemysql.data.model.Joke;
import lombok.Data;

import java.util.List;
@Data
public class JokeResponseDto extends StandardResponseDto{
    private List<Joke> jokes;
    private Joke joke;

    public JokeResponseDto(){

    }

    public JokeResponseDto(Status status){
        this.status = status;
    }

    public JokeResponseDto(Status status, ApiFieldError data) {
        this.data = data;
        this.status = status;
    }

    public JokeResponseDto(List<Joke> jokes, Status status, PageInfo page){
        super(status);
        this.jokes = jokes;
        this.page = page;
    }

    public JokeResponseDto(Joke joke, Status status) {
        super(status);
        this.joke = joke;
    }

    public JokeResponseDto(Status status, Joke joke) {
        super(status);
        this.joke= joke;
    }

    public JokeResponseDto(List<Joke> jokes, Status status) {
        super(status);
        this.jokes = jokes;
    }
}
