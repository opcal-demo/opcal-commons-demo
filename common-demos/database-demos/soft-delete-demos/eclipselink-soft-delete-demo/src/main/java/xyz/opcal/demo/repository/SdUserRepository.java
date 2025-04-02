package xyz.opcal.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.opcal.demo.entity.SdUser;

public interface SdUserRepository extends JpaRepository<SdUser, Long> {

	SdUser findFirstByOrderByIdDesc();

	List<SdUser> findTop10ByOrderByIdDesc();
}
