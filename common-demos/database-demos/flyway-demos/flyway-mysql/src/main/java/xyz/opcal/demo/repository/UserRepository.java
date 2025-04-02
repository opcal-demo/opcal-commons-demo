package xyz.opcal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.opcal.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

