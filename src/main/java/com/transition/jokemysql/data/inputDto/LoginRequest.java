package com.transition.jokemysql.data.inputDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {
    private String userName;
    private String password;
}
