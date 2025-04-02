package xyz.opcal.demo.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class RedisLockConfiguration {

	@Bean
	public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
		return new RedisLockRegistry(redisConnectionFactory, "Lettuce-redis-lock", TimeUnit.SECONDS.toMillis(30));
	}

	// @formatter:off
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	// @formatter:on

}
