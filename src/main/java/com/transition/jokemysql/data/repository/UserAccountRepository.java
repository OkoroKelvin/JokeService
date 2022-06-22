package com.transition.jokemysql.data.repository;

import com.transition.jokemysql.data.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Integer> {
     @Query(nativeQuery = true,value = "select * from user_account WHERE email=:username")
    UserAccount findByEmail(String username);
}
