package com.transition.jokemysql.data.inputDto;

import lombok.Data;

@Data
public class EmailMessage {
    private String to;
    private String body;
    private String subject;
}
