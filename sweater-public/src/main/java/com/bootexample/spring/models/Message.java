package com.bootexample.spring.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.bootexample.spring.models.utils.MessageHelper;

@Entity
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3552160194664422217L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Please, fill the message field")
	@Length(max = 2048, message = "Too long, max size 2048 characters")
	private String text;

	@Length(max = 128, message = "Tag is too long! Max size 128 characters")
	private String tag;

	@Basic
	@Temporal(TemporalType.DATE)
	private Date postedAt;

	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;

	@ManyToMany
	@JoinTable(name = "subscription_messages", joinColumns = { @JoinColumn(name = "message_id") }, inverseJoinColumns = {
			@JoinColumn(name = "subscriber_id") })
	private Set<User> subscribers = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "message_likes", joinColumns = { @JoinColumn(name = "message_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private Set<User> likes = new HashSet<>();

	public Message() {
		// empty constructor for Spring
	}

	public Message(String text, String tag, User user) {
		super();
		this.author = user;
		this.text = text;
		this.tag = tag;
	}

	public String getAuthorName() {
		return MessageHelper.getAuthorName(author);
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public Set<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}

}
