package com.transition.jokemysql.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    @JoinColumn(name = "joke_id", referencedColumnName ="id",nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Joke jokeId;
}
