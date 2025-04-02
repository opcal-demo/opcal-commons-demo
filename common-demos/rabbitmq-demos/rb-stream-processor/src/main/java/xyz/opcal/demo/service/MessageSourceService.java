package xyz.opcal.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class MessageSourceService {

	Logger producerLogger = LoggerFactory.getLogger("producer");

	private @Autowired StreamBridge streamBridge;

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	public void create(int batch) {
		var users = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults();
		for (xyz.opcal.tools.response.result.User user : users) {
			streamBridge.send("new-user-out-0", user);
			producerLogger.info("#### send user [{}]", user);
		}
	}
}
