package xyz.opcal.demo.service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import xyz.opcal.demo.model.User;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class MessageService {

	Logger producerLogger = LoggerFactory.getLogger("producer");

	private final StreamBridge streamBridge;

	public MessageService(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	private final RandomuserClient randomuserClient = new RandomuserClient(
			System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	private final AtomicLong idGenerator = new AtomicLong();

	public void create(int batch) {
		var users = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults();
		for (var user : users) {
			var data = new User(idGenerator.incrementAndGet(), LocalDateTime.now(), LocalDateTime.now(), user.getName().getFirst(), user.getName().getLast(),
					user.getGender(), user.getDob().getAge());
			streamBridge.send("new-user-out-0", data);
			producerLogger.info("#### send user [{}]", data);
		}
	}

}
