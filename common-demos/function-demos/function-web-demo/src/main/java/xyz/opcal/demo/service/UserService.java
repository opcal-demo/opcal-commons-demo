package xyz.opcal.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import xyz.opcal.demo.model.User;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class UserService {

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	private List<User> users = new ArrayList<>();

	private AtomicLong idGenerator = new AtomicLong();

	public List<xyz.opcal.tools.response.result.User> generate(int batch) {
		var result = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults();
		for (xyz.opcal.tools.response.result.User user : result) {
			users.add(new User(idGenerator.incrementAndGet(), user.getName().getFirst(), user.getName().getLast(), user.getGender(), user.getDob().getAge()));
		}
		return result;
	}

	public List<User> findAll() {
		return users;
	}

}
