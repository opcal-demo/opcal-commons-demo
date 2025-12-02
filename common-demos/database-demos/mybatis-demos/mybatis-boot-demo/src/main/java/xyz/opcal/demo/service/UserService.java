package xyz.opcal.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.MyBatis3RenderingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.opcal.demo.domain.User;
import xyz.opcal.demo.domain.dsql.UserDynamicSqlSupport;
import xyz.opcal.demo.mapper.UserMapper;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
@Transactional
public class UserService {

	private final UserMapper userMapper;

	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

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
		user.setCreatedDate(LocalDateTime.now());
		user.setModifiedDate(LocalDateTime.now());
		// @formatter:off
		var insertStatement = SqlBuilder.insert(user)
				.into(UserDynamicSqlSupport.user)
				.map(UserDynamicSqlSupport.id).toProperty(User.ID)
				.map(UserDynamicSqlSupport.createdDate).toProperty(User.CREATED_DATE)
				.map(UserDynamicSqlSupport.modifiedDate).toProperty(User.MODIFIED_DATE)
				.map(UserDynamicSqlSupport.firstName).toProperty(User.FIRST_NAME)
				.map(UserDynamicSqlSupport.lastName).toProperty(User.LAST_NAME)
				.map(UserDynamicSqlSupport.gender).toProperty(User.GENDER)
				.map(UserDynamicSqlSupport.age).toProperty(User.AGE)
				.build()
				.render(new MyBatis3RenderingStrategy());
		// @formatter:on
		userMapper.insert(insertStatement);
	}

	public User getLastOne() {
		// @formatter:off
		var selectStatement = SqlBuilder.select(UserDynamicSqlSupport.id, 
				UserDynamicSqlSupport.createdDate,UserDynamicSqlSupport.modifiedDate, UserDynamicSqlSupport.firstName,
						UserDynamicSqlSupport.lastName, UserDynamicSqlSupport.gender, UserDynamicSqlSupport.age)
				.from(UserDynamicSqlSupport.user)
				.orderBy(UserDynamicSqlSupport.id.descending())
				.limit(1)
				.build()
				.render(new MyBatis3RenderingStrategy());
		// @formatter:on

		return userMapper.selectLastOne(selectStatement);
	}

	public void update(User user) {
		// @formatter:off
		var updateStatement = SqlBuilder.update(UserDynamicSqlSupport.user)
				.set(UserDynamicSqlSupport.modifiedDate).equalTo(LocalDateTime.now())
				.set(UserDynamicSqlSupport.firstName).equalTo(user.getFirstName())
				.set(UserDynamicSqlSupport.lastName).equalTo(user.getLastName())
				.set(UserDynamicSqlSupport.gender).equalTo(user.getGender())
				.set(UserDynamicSqlSupport.age).equalTo(user.getAge())
				.where(UserDynamicSqlSupport.id, SqlBuilder.isEqualTo(user.getId()))
				.build()
				.render(new MyBatis3RenderingStrategy());
		// @formatter:on
		userMapper.update(updateStatement);
	}

}
