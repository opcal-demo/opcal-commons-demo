package xyz.opcal.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.demo.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

	Mono<User> findFirstByOrderByIdDesc();

	Flux<User> findBy(Pageable pageable);
}
