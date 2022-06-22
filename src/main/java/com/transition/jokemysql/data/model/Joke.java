package com.transition.jokemysql.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity(name = "joke")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Joke implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName ="id",nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserAccount userId;

    private String content;

    private LocalDateTime createdDate;
    private Integer likes =0;
    public void like() {
        this.likes +=1;
    }
}
