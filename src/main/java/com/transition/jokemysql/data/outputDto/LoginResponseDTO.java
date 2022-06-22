package com.transition.jokemysql.data.outputDto;

import com.transition.jokemysql.data.inputDto.ApiFieldError;
import com.transition.jokemysql.data.model.UserAccount;
import lombok.Data;

import java.util.Date;
@Data

public class LoginResponseDTO extends  StandardResponseDto{

    private String token;
    private UserAccount user;

    private String expiresIn;
    private Date expiresInDate;

    private Integer localId;

    private String firstName;

    private String email;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Status status) {
        super(status);
    }

    public LoginResponseDTO(Status status, String token, UserAccount user) {
        super(status);
        this.token = token;
        this.user = user;
    }

    public LoginResponseDTO(Status status, String token, UserAccount user, String expireTime, Integer id,String email,String firstName) {
        super(status);
        this.token = token;
        this.user = user;
        this.expiresIn = expireTime;
        this.localId = id;
        this.email = email;
        this.firstName=firstName;
    }

    public LoginResponseDTO(Status status, String token, UserAccount user, Date expireTimeDate, Integer id,String email,String firstName) {
        super(status);
        this.token = token;
        this.user = user;
        this.expiresInDate = expireTimeDate;
        this.localId = id;
        this.email = email;
        this.firstName=firstName;
    }

    public LoginResponseDTO(Status status, ApiFieldError data) {
        this.data = data;
        this.status = status;
    }

}
