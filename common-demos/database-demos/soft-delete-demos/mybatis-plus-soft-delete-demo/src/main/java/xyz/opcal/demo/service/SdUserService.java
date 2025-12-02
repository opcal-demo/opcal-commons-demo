package xyz.opcal.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import xyz.opcal.demo.domain.SdUser;
import xyz.opcal.demo.mapper.SdUserMapper;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
@Transactional
public class SdUserService {

	private final SdUserMapper userMapper;

	public SdUserService(SdUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	RandomuserClient randomuserClient = new RandomuserClient(System.getenv().getOrDefault("RANDOMUSER_API_URL", RandomuserClient.DEFAULT_API_URL));

	public List<SdUser> generate(int batch) {
		return randomuserClient.random(RandomuserRequest.builder().results(batch)
				.nationalities(new Nationalities[] { Nationalities.AU, Nationalities.GB, Nationalities.CA, Nationalities.US, Nationalities.NZ }).build())
				.getResults().stream().map(this::toUser).toList();
	}

	SdUser toUser(xyz.opcal.tools.response.result.User result) {
		SdUser user = new SdUser();
		user.setFirstName(result.getName().getFirst());
		user.setLastName(result.getName().getLast());
		user.setGender(result.getGender());
		user.setAge(result.getDob().getAge());
		return user;
	}

	public void insert(SdUser user) {
		userMapper.insert(user);
	}

	public void delete(SdUser user) {
		userMapper.deleteById(user);
	}

	public SdUser getLastOne() {
		return userMapper.selectOne(new LambdaQueryWrapper<>(SdUser.class).orderByDesc(SdUser::getId).last("limit 1"));
	}

	public List<SdUser> getLast10() {
		return userMapper.selectList(new LambdaQueryWrapper<>(SdUser.class).orderByDesc(SdUser::getId).last("limit 10"));
	}

	public void update(SdUser user) {
		userMapper.updateById(user);
	}
}
