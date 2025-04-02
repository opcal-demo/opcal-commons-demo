package xyz.opcal.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.opcal.demo.entity.UserEntity;
import xyz.opcal.demo.repository.UserRepository;

@Transactional
@Service
public class UserService {

	private @Autowired UserRepository userRepository;

	public void save(UserEntity user) {
		userRepository.save(user);
	}

	public void saveInLombda(List<UserEntity> users) {
		users.forEach(userRepository::save);
	}

	public void saveErrorAfter(List<UserEntity> users) {
		users.forEach(userRepository::save);
		throw new RuntimeException("some error in code");
	}

	public void saveErrorInforeach(List<UserEntity> users) {
		Random random = new Random();
		users.forEach(user -> {
			userRepository.save(user);
			if (random.nextInt(users.size()) % 3 == 0) {
				throw new RuntimeException("some error in code");
			}
		});
	}

	public long countAll() {
		return userRepository.count();
	}
}
