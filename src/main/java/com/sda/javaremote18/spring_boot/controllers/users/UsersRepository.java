package com.sda.javaremote18.spring_boot.controllers.users;

import com.sda.javaremote18.spring_boot.models.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserModel, Integer> {

    @Query("SELECT user from UserModel user where user.email=?1")
    UserModel findByEmail(String email);

    @Query("SELECT user from UserModel user where user.email=?1")
    Optional<UserModel> findUserByEmail(String email);

    List<UserModel> findAll();
}
