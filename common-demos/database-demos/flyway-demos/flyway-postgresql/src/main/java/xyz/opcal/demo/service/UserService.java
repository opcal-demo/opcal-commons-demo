package xyz.opcal.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	public List<User> generate(int batch) {
		return randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults().stream().map(this::toUser).toList();
	}

	User toUser(xyz.opcal.tools.response.result.User result) {
		User user = new User();
		user.setFirstName(result.getName().getFirst());
		user.setLastName(result.getName().getLast());
		user.setGender(result.getGender());
		user.setAge(result.getDob().getAge());
		return user;
	}

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
}
