package xyz.opcal.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import xyz.opcal.demo.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

}
