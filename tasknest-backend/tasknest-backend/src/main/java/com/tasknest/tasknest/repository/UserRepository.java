package com.tasknest.tasknest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasknest.tasknest.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
