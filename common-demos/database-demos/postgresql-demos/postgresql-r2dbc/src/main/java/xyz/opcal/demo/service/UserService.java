package xyz.opcal.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class UserService {

	private @Autowired UserRepository userRepository;
	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	public List<User> generate(int batch) {
		return randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults().stream().map(this::toUser).toList();
	}

	User toUser(xyz.opcal.tools.response.result.User result) {
		return new User(null, LocalDateTime.now(), LocalDateTime.now(), result.getName().getFirst(), result.getName().getLast(), result.getGender(),
				result.getDob().getAge());
	}

	public Mono<User> save(User user) {
		return userRepository.save(user);
	}

	public Flux<User> getAll() {
		return userRepository.findAll();
	}

}
