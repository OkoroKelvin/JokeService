package com.transition.jokemysql.service;


import com.transition.jokemysql.data.inputDto.JokeInputDto;
import com.transition.jokemysql.data.inputDto.LoginDto;
import com.transition.jokemysql.data.inputDto.UserAccountInputDto;
import com.transition.jokemysql.data.model.UserAccount;
import com.transition.jokemysql.data.outputDto.JokeResponseDto;
import com.transition.jokemysql.data.outputDto.LoginResponseDTO;
import com.transition.jokemysql.data.outputDto.Response;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Response createUserAccount(UserAccountInputDto inputDto);
    LoginResponseDTO login(LoginDto login);

    JokeResponseDto createJoke(Integer userId, String joke);

    JokeResponseDto findAllJokesCreatedByAUser(Integer userId);

    UserAccount getUser(String email);
}
