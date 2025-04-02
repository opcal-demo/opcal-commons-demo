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

import java.time.LocalDateTime;
import java.util.List;

import xyz.opcal.demo.entity.User;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

public class RandomuserUtils {

	public static final RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	public static List<User> generate(int batch) {
		var response = randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build());
		return response.getResults().stream().map(RandomuserUtils::toUser).toList();
	}

	public static User toUser(xyz.opcal.tools.response.result.User result) {
		return new User(null, LocalDateTime.now(), LocalDateTime.now(), result.getName().getFirst(), result.getName().getLast(), result.getGender(),
				result.getDob().getAge());
	}

}
