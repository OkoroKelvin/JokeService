package com.transition.jokemysql.service;


import com.transition.jokemysql.data.inputDto.*;
import com.transition.jokemysql.data.model.Joke;
import com.transition.jokemysql.data.outputDto.*;
import com.transition.jokemysql.data.repository.CommentRepository;
import com.transition.jokemysql.data.repository.JokeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JokeServiceTest {

    @Autowired
    private JokeService jokeService;



    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;


    @Autowired
    private JokeRepository jokeRepository;


    @Autowired
    private CommentRepository commentRepository;
    JokeInputDto joke1;
    JokeInputDto joke2;


    private


    @BeforeEach
    void setUp() {
//        commentRepository.deleteAll();
//        jokeRepository.deleteAll();
//
//
//        jokeRepository.save(Joke.builder().content("Funny")
//                .createdDate(LocalDateTime.now())
//                .likes(3)
//                .build());
//        jokeRepository.save(Joke.builder().content("Comedy")
//                .createdDate(LocalDateTime.now())
//                .likes(5)
//                .build());
//        jokeRepository.save(Joke.builder().content("Hilarious")
//                .createdDate(LocalDateTime.now())
//                .likes(2)
//                .build());
//        jokeRepository.save(Joke.builder().content("Mr Sabinus")
//                .createdDate(LocalDateTime.now())
//                .likes(7)
//                .build());
//
//        joke1 = new JokeInputDto();
//        joke1.setContent("Laughter is a way of life");
//        jokeService.saveJoke(joke1);
//
//        CommentInputDto inputDto = new CommentInputDto();
//        inputDto.setJokeId(1);
//        inputDto.setWords("I like it");
//        commentService.createComment(inputDto );
//
//        CommentInputDto input2 = new CommentInputDto();
//        input2.setJokeId(2);
//        input2.setWords("I hate it");
//        commentService.createComment(input2 );
//
//        CommentInputDto input3 = new CommentInputDto();
//        input3.setJokeId(2);
//        input3.setWords("cool");
//        commentService.createComment(input3 );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test That Joke Can Be Created")
    void testThatJokeCanBeCreated(){
        joke2 = new JokeInputDto();
        joke2.setContent("Just Dream");
        JokeResponseDto response = jokeService.saveJoke(joke2);
        assertNotNull(response.getJoke());
        assertEquals(response.getJoke().getContent(),"Just Dream");
        assertThat(jokeRepository.findAll()).hasSize(6);
    }

    @Test
    @DisplayName("Test That Joke can be deleted")
    void testThatJokeCanBeDeleted(){
        assertEquals(jokeRepository.findAll().size(),5);
        jokeService.removeJoke(4);
        assertEquals(jokeRepository.findAll().size(),4);
    }

    @Test
    @DisplayName("Test that Jokes can be liked")
    void testThatJokeCanBeLiked(){
        jokeService.likeJoke(4);
        jokeService.likeJoke(4);
        jokeService.likeJoke(4);
       JokeResponseDto response = jokeService.likeJoke(4);
        assertEquals(response.getJoke().getLikes(),11);
        JokeResponseDto response2 = jokeService.likeJoke(1);
        assertEquals(response2.getJoke().getLikes(),4);
    }

    @Test
    @DisplayName("Test that shows List of all joke as best to worst")
    void testThatJokesCanBeSortedViaLikesBestToWorst(){
        JokeResponseDto response = jokeService.findAllJokesByBestToLeastLikes();
        response.getJokes().forEach(System.out::println);
        assertEquals(response.getJokes().get(0).getLikes(),7);
        assertEquals(response.getJokes().get(4).getLikes(),0);
    }

    @Test
    @DisplayName("Test that shows List of all joke as worst to best")
    void testThatJokesCanBeSortedViaLikesWorstToBest(){
        JokeResponseDto response = jokeService.findAllJokesByLeastToBestLikes();
        response.getJokes().forEach(System.out::println);
        assertEquals(response.getJokes().get(0).getLikes(),0);
        assertEquals(response.getJokes().get(4).getLikes(),7);
    }

    @Test
    @DisplayName("Test that Comment Comment Can Be added to a Joke")
    void testThatCommentCanBeAddedToJokes(){
        CommentInputDto inputDto = new CommentInputDto();
        inputDto.setJokeId(2);
        inputDto.setWords("I like it");
        CommentResponseDto response = commentService.createComment(inputDto );
        assertEquals(response.getComment().getComment(),"I like it");
        assertEquals(commentRepository.findAll().size(),4);


        CommentInputDto inputDto2 = new CommentInputDto();
        inputDto2.setJokeId(2);
        inputDto2.setWords("Lively Joke");
        CommentResponseDto response2 = commentService.createComment(inputDto2);
        assertEquals(response2.getComment().getComment(),"Lively Joke");
        assertEquals(commentRepository.findAll().size(),5);

        CommentInputDto inputDto3 = new CommentInputDto();
        inputDto3.setJokeId(1);
        inputDto3.setWords("Excellent vibe");
        CommentResponseDto response3 = commentService.createComment(inputDto3);
        assertEquals(response3.getComment().getComment(),"Excellent vibe");
        assertEquals(commentRepository.findAll().size(),6);
    }

    @Test
    @DisplayName("Comment can be deleted from Jokes")
    void testThatShowsCommentCanBeDeletedFromJoke(){
        CommentDeleteDto deleteDto = new CommentDeleteDto();
        deleteDto.setJokeId(2);
        deleteDto.setCommentId(2);
        commentService.removeCommentFromJoke(deleteDto);
        assertEquals(commentRepository.findAll().size(),2);
    }

    @Test
    @DisplayName("Find all Jokes with its Comment")
    void testThatAllJokesCanBeReadWithItsComment(){
        JokeCompositeResponseDto responseDto = jokeService.findAllJokesWithItsComment();
        List<JokeComposite> allJokes = responseDto.getJokeWithComments();
        allJokes.forEach(System.out::println);
    }

    @Test
    @DisplayName("Find all Jokes ")
    void testThatAllJokesCanBeSeen(){
        JokeResponseDto responseDto = jokeService.findAllJokes();
        List<Joke> allJokes = responseDto.getJokes();
        allJokes.forEach(System.out::println);
    }

    @Test
    @DisplayName("Test that jokes can be sent to mails")
    void tesThatJokeCanBeMailed(){
        JokeResponseDto service = jokeService.sendJokeToEmail(1,"okorokelvinemoakpo@gmail.com");

    }

    @Test
    @DisplayName("Test that user can be created")
    void testThatUserCanBeCreated(){
        UserAccountInputDto inputDto = new UserAccountInputDto();
        inputDto.setFirstName("Kelvin");
        inputDto.setLastName("Okoro");
        inputDto.setEmail("okorokelvinemoakpo@yahoo.com");
        inputDto.setPassword("kel12345");
      Response message =  userService.createUserAccount(inputDto);
        assertEquals(message.getReport(),"Successfully created");
    }
//    @Test
//    @DisplayName("Test that Registered User Can Login")
//    void testThatUserCanLogin(){
//        LoginDto login = new LoginDto();
//        login.setEmail("okorokelvinemoakpo@yahoo.com");
//        login.setPassword("kel12345");
//        LoginResponseDTO token = userService.login(login);
//        log.info("Token --> {}", token);
//        assertNotNull(token.getToken());
//    }
    @Test
    void testThatInvalidPasswordThrowsApplicationException(){
        UserAccountInputDto inputDto = new UserAccountInputDto();
        inputDto.setFirstName("Kelvin");
        inputDto.setLastName("Okoro");
        inputDto.setEmail("okorokelvinemoakpo@gmail.com");
        inputDto.setPassword("oko12345");
        Response message =  userService.createUserAccount(inputDto);
        assertEquals(message.getReport(),"Successfully created");
    }
//    @Test
//    void testThatUser2CanLogin(){
//        LoginDto login = new LoginDto();
//        login.setEmail("okorokelvinemoakpo@gmail.com");
//        login.setPassword("oko12345");
//        LoginResponseDTO token = userService.login(login);
//        log.info("Token --> {}", token);
//        assertNotNull(token.getToken());
//    }

//    @Test
//    void testThatUserCanCreateJokes(){
//       JokeInputDto joke = new JokeInputDto();
//       joke.setContent("Comic politics");
//        JokeResponseDto savedJoke = userService.createJoke(1,joke);
//        assertEquals("Comic politics",savedJoke.getJoke().getContent());
//    }
    @Test
    void testThatUserCanSeeItsCreatedJoke(){
        JokeResponseDto allJokes = userService.findAllJokesCreatedByAUser(1);
        assertEquals(allJokes.getJokes().size(),1);

    }
}