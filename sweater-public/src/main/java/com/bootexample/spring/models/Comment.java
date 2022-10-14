package com.bootexample.spring.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -884870396410901256L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Fill message field")
	@Length(max = 2048, message = "Message too long (more than 2048 characters)")
	private String text;

	@Basic
	@Temporal(TemporalType.DATE)
	private Date postedAt;

	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;

	public Comment() {
		// empty constructor for Spring
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

}
