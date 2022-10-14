package com.bootexample.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bootexample.spring.repos.UserRepo;

@SpringBootTest
class ApplicationTests {
	@Autowired
	UserRepo userRepo;

	@Test
	void contextLoads() {
//		User user = new User();
//		user.setUsername("username1234");
//		user.setPassword("password1234");
//		user.setActive(true);
//		user.setRoles(Collections.singleton(Role.USER));
//
//		userRepo.save(user);
	}

}
