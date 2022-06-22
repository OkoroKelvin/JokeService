package com.transition.jokemysql.data.inputDto;

public class CommentInputDto {
    private Integer jokeId;
    private String words;

    public Integer getJokeId() {
        return jokeId;
    }

    public void setJokeId(Integer jokeId) {
        this.jokeId = jokeId;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
