package com.example.demo.percistence.repository;

import com.example.demo.percistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {

}
