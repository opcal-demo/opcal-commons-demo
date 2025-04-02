package xyz.opcal.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.opcal.demo.entity.SdUser;
import xyz.opcal.demo.repository.SdUserRepository;
import xyz.opcal.tools.client.RandomuserClient;
import xyz.opcal.tools.request.RandomuserRequest;
import xyz.opcal.tools.request.param.Nationalities;

@Service
public class SdUserService {

	private @Autowired SdUserRepository sdUserRepository;
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

	public long countAll() {
		return sdUserRepository.count();
	}

	public SdUser save(SdUser user) {
		return sdUserRepository.save(user);
	}

	public void delete(SdUser user) {
		sdUserRepository.delete(user);
	}

	public SdUser getLastOne() {
		return sdUserRepository.findFirstByOrderByIdDesc();
	}

	public List<SdUser> getLast10() {
		return sdUserRepository.findTop10ByOrderByIdDesc();
	}

}
