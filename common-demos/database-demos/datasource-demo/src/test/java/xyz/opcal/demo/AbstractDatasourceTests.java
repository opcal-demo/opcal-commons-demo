/*
 * Copyright 2020-2025 Opcal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.opcal.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import xyz.opcal.demo.entity.UserEntity;
import xyz.opcal.demo.service.UserService;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
abstract class AbstractDatasourceTests {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	UserService userService;

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	@AfterAll
	void after() {
		var users = jdbcTemplate.queryForList("SELECT * FROM user");
		log.info("user info: \n {}", users);
	}

	public List<UserEntity> generate(int batch) {
		var response = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build());
		return response.getResults().stream().map(this::toUser).toList();
	}

	UserEntity toUser(xyz.opcal.tools.response.result.User result) {
		var user = new UserEntity();
		user.setFirstName(result.getName().getFirst());
		user.setLastName(result.getName().getLast());
		user.setGender(result.getGender());
		user.setAge(result.getDob().getAge());
		return user;
	}

	@Order(0)
	@Test
	void save() {
		var user = generate(1).getFirst();
		assertDoesNotThrow(() -> userService.save(user));
	}

	@Test
	@Order(1)
	void testSaveInLombda() {
		var users = generate(10);
		assertDoesNotThrow(() -> userService.saveInLambda(users));
	}

	@Test
	@Order(2)
	void testSaveErrorAfter() {
		final long beforeTotal = userService.countAll();
		var users = generate(10);
		assertThrows(RuntimeException.class, () -> userService.saveErrorAfter(users));
		assertEquals(beforeTotal, userService.countAll());
	}

	@Test
	@Order(3)
	void testSaveErrorInForeach() {
		final long beforeTotal = userService.countAll();
		var users = generate(10);
		assertThrows(RuntimeException.class, () -> userService.saveErrorInForeach(users));
		assertEquals(beforeTotal, userService.countAll());
	}

}
