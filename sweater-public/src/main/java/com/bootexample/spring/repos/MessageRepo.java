package com.bootexample.spring.repos;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bootexample.spring.models.Message;
import com.bootexample.spring.models.User;
import com.bootexample.spring.models.dto.MessageDto;

public interface MessageRepo extends CrudRepository<Message, Long> {
	@Query("select new com.bootexample.spring.models.dto.MessageDto(m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) from Message m left join m.likes ml group by m")
	Page<MessageDto> findAll(Pageable pageable, @Param("user") User user);

	@Query("select new com.bootexample.spring.models.dto.MessageDto(m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) from Message m left join m.likes ml where m.tag = :tag group by m")
	Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);

	@Query("select new com.bootexample.spring.models.dto.MessageDto(m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) from Message m left join m.likes ml where m.author = :author group by m")
	Page<MessageDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);

//	@Query("select new com.bootexample.spring.models.dto.MessageDto(m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) from User u join u.subscriptions us join us.messages m left join m.likes ml where u = :user group by m, u")
//	Page<MessageDto> findAllSubscriptionsByUser(Pageable pageable, @Param("user") User user);

	@Query("select new com.bootexample.spring.models.dto.MessageDto(sm, count(ml), sum(case when ml = :user then 1 else 0 end) > 0) from User u join u.subscriptionMessages sm left join sm.likes ml where u = :user group by sm, u")
	Page<MessageDto> findAllSubscriptionsByUser(Pageable pageable, @Param("user") User user);

	@Query("select sm from User u join u.subscriptionMessages sm where u = :user")
	Set<Message> findAllSubscriptionsByUser(@Param("user") User user);
}