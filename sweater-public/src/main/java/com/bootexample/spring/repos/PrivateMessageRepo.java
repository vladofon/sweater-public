package com.bootexample.spring.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootexample.spring.models.PrivateMessage;
import com.bootexample.spring.models.User;

public interface PrivateMessageRepo extends JpaRepository<PrivateMessage, Long> {

	@Query(value = "select pm from PrivateMessage pm join pm.sender ps join pm.reciever pr where (ps = :first_user and pr = :second_user) or (ps = :second_user and pr = :first_user)")
	Page<PrivateMessage> findCorrespondence(Pageable pageable, @Param("first_user") User sender,
			@Param("second_user") User reciever);
}
