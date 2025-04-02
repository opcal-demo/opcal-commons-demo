package xyz.opcal.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import xyz.opcal.demo.domain.User;
import xyz.opcal.demo.mapper.UserMapper;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
@Transactional
public class UserService {

	private @Autowired UserMapper userMapper;

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

	public void insert(User user) {
		userMapper.insert(user);
	}

	public User getLastOne() {
		return userMapper.selectOne(new LambdaQueryWrapper<>(User.class).orderByDesc(User::getId).last("limit 1"));
	}

	public void update(User user) {
		userMapper.updateById(user);
	}
}
