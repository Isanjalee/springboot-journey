package com.isanjalee.demo.springbootdemo.repository;

import com.isanjalee.demo.springbootdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
