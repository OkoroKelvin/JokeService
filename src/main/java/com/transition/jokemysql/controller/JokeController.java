package com.transition.jokemysql.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transition.jokemysql.data.inputDto.*;
import com.transition.jokemysql.data.model.UserAccount;
import com.transition.jokemysql.data.outputDto.*;
import com.transition.jokemysql.data.repository.JokeRepository;
import com.transition.jokemysql.data.repository.UserAccountRepository;
import com.transition.jokemysql.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3002", allowedHeaders = "*", allowCredentials = "true", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class JokeController {

    private final EmailSenderService emailSenderService;

    private final JokeRepository jokeRepository;
    private final JokeService jokeService;
    private final UserService userService;
    private final CommentService commentService;
    private final AuthenticationManager authenticationManager;

    private final UserAccountRepository userAccountRepository;


    @GetMapping("/jokes")
    public JokeResponseDto getAllJokeOnly() {
        return jokeService.findAllJokes();
    }

    @PostMapping("/jokes")
    public JokeResponseDto createJoke(@RequestBody JokeInputDto jokeInputDto) {
        return jokeService.saveJoke(jokeInputDto);
    }

    @PostMapping("/jokes/{id}")
    public CommentResponseDto createJokeComment(@PathVariable Integer id, @RequestBody JokeCommentInputDto jokeInputDto) {
        return commentService.createJokeComment(id, jokeInputDto);
    }


    @DeleteMapping("/jokes/{id}")
    public JokeResponseDto deleteJoke(@PathVariable Integer id) {
        return jokeService.removeJoke(id);
    }


    @GetMapping("/jokes/{id}")
    public JokeResponseDto getJokeById(@PathVariable Integer id) {
        return jokeService.findJokeByItsId(id);
    }

    @GetMapping("/jokes/most")
    public JokeResponseDto getJokesByMostLike() {
        return jokeService.findAllJokesByBestToLeastLikes();
    }

    @GetMapping("/jokes/least")
    public JokeResponseDto getJokesByLeastLike() {
        return jokeService.findAllJokesByLeastToBestLikes();
    }


    @PatchMapping("jokes/{id}")
    public JokeResponseDto likeJoke(@PathVariable Integer id) {
        return jokeService.likeJoke(id);
    }

    @GetMapping("jokes/comment/{id}")
    public CommentResponseDto getAllJokeComment(@PathVariable Integer id) {
        return commentService.getAllCommentOfAJoke(id);
    }

    @DeleteMapping("jokes/comment/{id}")
    public CommentResponseDto deleteCommentById(@PathVariable Integer id) {
        return commentService.deleteCommentByItsId(id);

    }

    @PostMapping("/jokes/send-email")
    public String sendEmail(@RequestBody EmailMessage message) {
        emailSenderService.sendEmail(message.getTo(), message.getBody(), message.getSubject());
        return "Sent";
    }

    @PostMapping("jokes/send-jokes/{id}")
    public JokeResponseDto sendJokeToEmail(@PathVariable Integer id, @RequestBody EmailInputDto email) {
        jokeService.sendJokeToEmail(id, email.getEmail());
        return new JokeResponseDto(Status.SUCCESS);
    }

    @PostMapping("jokes/register-user")
    public Response registerUser(@RequestBody UserAccountInputDto inputDto) {
        return userService.createUserAccount(inputDto);
    }

    @PostMapping(value = "/jokes/login-user")
    public LoginResponseDTO loginUser(@RequestBody LoginDto loginRequest,HttpServletRequest request) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        User user = (User) authentication.getPrincipal();
        UserAccount foundUser = userService.getUser(loginRequest.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date expireDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
       // String expiredDate = dateFormat.format(expireDate.getTime());


        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expireDate)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        foundUser.setLastLogonTime(new Date());
        userAccountRepository.save(foundUser);


        return new LoginResponseDTO(Status.SUCCESS, token, foundUser,expireDate,foundUser.getId(),foundUser.getEmail(),foundUser.getFirstName());

    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserAccount foundUser = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(foundUser.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("jokes/user-create-jokes/")
    public JokeResponseDto userCreateJokes(@RequestBody RegisteredUserJokeDTO inputDto){
        userService.createJoke(inputDto.getUserId(),inputDto.getContent());
        //response.setHeader("Access-Control-Allow-Origin","*");

        return new JokeResponseDto(Status.SUCCESS);
    }

    @GetMapping("jokes/user-jokes/{id}")
    public JokeResponseDto userGetsAllJokes(@PathVariable Integer id){
        return userService.findAllJokesCreatedByAUser(id);
    }
}

