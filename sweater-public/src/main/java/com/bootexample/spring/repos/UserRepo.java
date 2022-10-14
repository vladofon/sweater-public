package com.bootexample.spring.repos;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootexample.spring.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
	Page<User> findAll(Pageable pageable);

	User findByUsername(String username);

	User findByActivationCode(String code);

	@Query(value = "select c from User u join u.subscribers c where u = :user")
	Page<User> findSubscribersByUser(Pageable pageable, @Param("user") User user);

	@Query(value = "select c from User u join u.subscribers c where u = :user")
	Set<User> findSubscribersByUser(@Param("user") User user);

	@Query(value = "select c from User u join u.subscriptions c where u = :user")
	Page<User> findSubscriptionsByUser(Pageable pageable, @Param("user") User user);

	@Query(value = "select count(us2.subscriber_id) > 1 as is_friends from usr u inner join user_subscriptions us1 on u.id = channel_id inner join user_subscriptions us2 on us1.subscriber_id = us2.channel_id where u.id = :user and us2.channel_id = :sub", nativeQuery = true)
	boolean isUsersFriends(@Param("user") User user, @Param("sub") User sub);

	@Query(value = "select s from User u join u.subscribers s join s.subscribers ss where u = :user and ss = :user order by s")
	Page<User> findUserFriends(Pageable pageable, @Param("user") User user);
}
