package xyz.opcal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.opcal.demo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
