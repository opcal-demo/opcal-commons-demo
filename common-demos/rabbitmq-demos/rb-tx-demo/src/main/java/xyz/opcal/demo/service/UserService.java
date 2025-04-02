package xyz.opcal.demo.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.opcal.demo.RbTxApplication;
import xyz.opcal.demo.entity.User;
import xyz.opcal.demo.repository.UserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class UserService {

	Logger producerLogger = LoggerFactory.getLogger("producer");
	Logger consumerLogger = LoggerFactory.getLogger("consumer");

	private @Autowired RabbitTemplate rabbitTemplate;
	private @Autowired UserRepository userRepository;
	private AtomicLong testCounter = new AtomicLong();

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	@Transactional
	public List<xyz.opcal.tools.response.result.User> generate(int batch) {
		var users = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults();
		for (xyz.opcal.tools.response.result.User user : users) {
			rabbitTemplate.convertAndSend(RbTxApplication.EXCHANGE_NAME, "commons.demo.randomuser", user);
			producerLogger.info("#### send user [{}]", user);
		}
		return users;
	}

	@Transactional
	// @RabbitListener(queues = "randomuser")
	// @formatter:off
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(
					name = RbTxApplication.QUEUE_NAME, 
					durable = "false", 
					autoDelete = "true"), 
			exchange = @Exchange(
					name = RbTxApplication.EXCHANGE_NAME, 
					type = ExchangeTypes.TOPIC, 
					durable = Exchange.FALSE, 
					autoDelete = Exchange.TRUE)
			)
	)
	// @formatter:on
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
