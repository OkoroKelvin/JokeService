package com.transition.jokemysql.service;

import com.transition.jokemysql.data.inputDto.JokeInputDto;
import com.transition.jokemysql.data.inputDto.LoginDto;
import com.transition.jokemysql.data.inputDto.UserAccountInputDto;
import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.model.UserAccount;
import com.transition.jokemysql.data.outputDto.JokeResponseDto;
import com.transition.jokemysql.data.outputDto.LoginResponseDTO;
import com.transition.jokemysql.data.outputDto.Response;
import com.transition.jokemysql.data.outputDto.Status;
import com.transition.jokemysql.data.repository.JokeRepository;
import com.transition.jokemysql.data.repository.UserAccountRepository;
import com.transition.jokemysql.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserAccountRepository userAccountRepository;


    private final JokeRepository jokeRepository;


    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.error("Username {} not found in the database zzzzzzzzzzzzzz",username);
        UserAccount user = userAccountRepository.findByEmail(username);
        log.error("Username {} not found in the database zzzzzzzzzzzzzz",username);
        if (user == null) {
            log.error("User not found in the database zzzzzzzzzzzzzz");
            throw new UsernameNotFoundException("User not found in the database zzzzzzzzzzzzz");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public Response createUserAccount(UserAccountInputDto inputDto) {
        try {
            UserAccount user = new UserAccount();
            user.setFirstName(inputDto.getFirstName());
            user.setLastName(inputDto.getLastName());
            user.setEmail(inputDto.getEmail());
            user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
            log.info("Saving new user {} to the database", user.getEmail());
            userAccountRepository.save(user);
            return new Response(Status.SUCCESS, "Successfully created");

        } catch (Exception e) {
            return new Response(Status.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public LoginResponseDTO login(LoginDto login) {

        UserAccount user = userAccountRepository.findByEmail(login.getEmail());

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new ApplicationException("Invalid Password");
        }

       // UserAccount userDetails = jokeUserDetailsService.loadUserByUsername(login.getEmail());
       // log.info("user details->{}", userDetails);
        return null;
    }


    @Override
    public JokeResponseDto createJoke(Integer userId, String jokeInput) {

        try {
            Optional<UserAccount> userRepo = userAccountRepository.findById(userId);
            UserAccount user = userRepo.get();

            if (user == null) {
                throw new ApplicationException("User not found");
            }
            Joke joke = new Joke();
            joke.setContent(jokeInput);
            joke.setCreatedDate(LocalDateTime.now());
            joke.setUserId(user);
            jokeRepository.save(joke);
            return new JokeResponseDto(Status.SUCCESS, joke);
        } catch (Exception e) {
            return new JokeResponseDto(Status.INTERNAL_ERROR);
        }
    }

    @Override
    public JokeResponseDto findAllJokesCreatedByAUser(Integer userId) {
        List<Joke> jokes = jokeRepository.findJokesByUserId(userId);
        return new JokeResponseDto(jokes, Status.SUCCESS);
    }

    @Override
    public UserAccount getUser(String email) {
        UserAccount user = userAccountRepository.findByEmail(email);
        return user;
    }
}