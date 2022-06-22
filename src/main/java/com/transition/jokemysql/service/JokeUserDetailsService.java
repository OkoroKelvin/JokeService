//package com.transition.jokemysql.service;
//
//import com.transition.jokemysql.data.model.UserAccount;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JokeUserDetailsService implements UserDetailsService {
//
//
//    @Autowired
//    private JokeService jokeService;
//    @Override
//    public UserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
//        return jokeService.findUserByEmail(username);
//    }
//}
