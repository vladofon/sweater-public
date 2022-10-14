package com.bootexample.spring.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootexample.spring.models.Comment;
import com.bootexample.spring.models.Message;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	@Query("select mc from Message m join m.comments mc where m = :message")
	Page<Comment> findByMessage(Pageable pageable, @Param("message") Message message);

}
