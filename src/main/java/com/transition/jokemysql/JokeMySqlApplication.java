package com.transition.jokemysql;



import com.transition.jokemysql.data.inputDto.UserAccountInputDto;
import com.transition.jokemysql.service.UserService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableRabbit
@SpringBootApplication
public class JokeMySqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(JokeMySqlApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:3002/");
                registry.addMapping("/**").allowedOrigins("http://localhost:3002/");
            }
        };
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.createUserAccount(new UserAccountInputDto("Kelvin", "Okoro", "okorokelvinemoakpo@yahoo.com", "1234"));
            userService.createUserAccount(new UserAccountInputDto("Ovie", "Okoro", "okoroovieemoakpo@yahoo.com", "1234"));
            userService.createUserAccount(new UserAccountInputDto("Lawerence", "Oyor", "lawerenceoyor@yahoo.com", "1234"));
            userService.createUserAccount(new UserAccountInputDto("Steve", "Job", "stevejobs@yahoo.com", "1234"));
            userService.createUserAccount(new UserAccountInputDto("Mike", "Key", "mikekey@yahoo.com", "1234"));
            userService.createUserAccount(new UserAccountInputDto("Michał ", "Bogacewicz", "michalbogacewicz@yahoo.com", "1234"));
        };
    }



}

