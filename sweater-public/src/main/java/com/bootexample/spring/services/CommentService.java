package com.bootexample.spring.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bootexample.spring.models.Comment;
import com.bootexample.spring.models.Message;
import com.bootexample.spring.models.User;
import com.bootexample.spring.repos.CommentRepo;

@Service
public class CommentService {
	@Autowired
	CommentRepo commentRepo;

	public Page<Comment> commentList(Pageable pageable, Message message) {
		return commentRepo.findByMessage(pageable, message);
	}

	public Comment save(Comment comment, User author, Message message) {
		comment.setAuthor(author);
		comment.setMessage(message);
		comment.setPostedAt(new Date());

		return commentRepo.save(comment);
	}

}
