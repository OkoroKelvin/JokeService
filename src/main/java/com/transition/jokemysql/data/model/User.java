package com.transition.jokemysql.data.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Joke joke;
    private String email;
}
