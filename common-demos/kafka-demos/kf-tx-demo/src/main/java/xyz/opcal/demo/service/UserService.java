package xyz.opcal.demo.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class UserService {

	Logger producerLogger = LoggerFactory.getLogger("producer");
	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	private @Autowired KafkaTemplate<String, xyz.opcal.tools.response.result.User> kafkaTemplate;
	private @Autowired UserRepository userRepository;
	private AtomicLong testCounter = new AtomicLong();

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	@Transactional
	public List<xyz.opcal.tools.response.result.User> generate(int batch) {
		var users = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults();
		for (xyz.opcal.tools.response.result.User user : users) {
			kafkaTemplate.send("randomuser", user);
			producerLogger.info("#### send user [{}]", user);
		}
		return users;
	}

	@Transactional
	@KafkaListener(id = "savegroup", topics = "randomuser")
	public void listen(xyz.opcal.tools.response.result.User userResult) {
		User user = new User();
		user.setFirstName(userResult.getName().getFirst());
		user.setLastName(userResult.getName().getLast());
		user.setGender(userResult.getGender());
		user.setAge(userResult.getDob().getAge());
		userRepository.save(user);
		consumerLogger.info("**** consume user [{}]", user);
		if (testCounter.incrementAndGet() % 10 == 0) {
			throw new RuntimeException("mock something wrong here");
		}
	}

	public long getSaveCount() {
		return testCounter.get();
	}

}
