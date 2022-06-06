package com.example.demo.percistence.repository;

import com.example.demo.commands.UserDto;
import com.example.demo.percistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);
    /*@Modifying
    @Query("update u from User u set u.username = :username where u.id = :id")
    User updateUser(String username);*/
    //@Query("select u from User u where u.id = %s")
    //Optional<User> findById(Integer id);
}
