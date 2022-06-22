package com.transition.jokemysql.data.outputDto;

import lombok.Data;

@Data
public class RefreshTokenDTO {
    private String createdToken;
    private String refreshToken;
}
