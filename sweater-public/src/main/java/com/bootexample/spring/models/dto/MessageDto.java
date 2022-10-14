package com.bootexample.spring.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bootexample.spring.models.Comment;
import com.bootexample.spring.models.Message;
import com.bootexample.spring.models.User;
import com.bootexample.spring.models.utils.MessageHelper;

public class MessageDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8755809493516980483L;

	private Long id;

	private String text;

	private String tag;

	private Date postedAt;

	private User author;

	private Set<Comment> comments;

	private Long likes;

	private boolean meLiked;

	public MessageDto(Message message, Long likes, boolean meLiked) {
		this.id = message.getId();
		this.text = message.getText();
		this.tag = message.getTag();
		this.postedAt = message.getPostedAt();
		this.author = message.getAuthor();
		this.comments = message.getComments();
		this.likes = likes;
		this.meLiked = meLiked;
	}

	public String getAuthorName() {
		return MessageHelper.getAuthorName(author);
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getTag() {
		return tag;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public User getAuthor() {
		return author;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Long getLikes() {
		return likes;
	}

	public boolean isMeLiked() {
		return meLiked;
	}
}
