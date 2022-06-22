package com.transition.jokemysql.data.inputDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInputDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
