package com.bootexample.spring.models;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7132588954195027378L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Username field cannot be empty")
	private String username;

	@NotBlank(message = "Password field cannot be empty")
	private String password;

	private boolean active;

	private boolean muted;

	@Basic
	@Temporal(TemporalType.DATE)
	private Date postedAt;

	@Email(message = "Email is not valid")
	@NotBlank(message = "Email field cannot be empty")
	private String email;
	private String activationCode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "photo_id")
	private Photo avatar;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Message> messages;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comment> comments;

	@ManyToMany
	@JoinTable(name = "subscription_messages", joinColumns = {
			@JoinColumn(name = "subscriber_id") }, inverseJoinColumns = { @JoinColumn(name = "message_id") })
	private Set<Message> subscriptionMessages;

	@ManyToMany
	@JoinTable(name = "user_subscriptions", joinColumns = { @JoinColumn(name = "channel_id") }, inverseJoinColumns = {
			@JoinColumn(name = "subscriber_id") })
	private Set<User> subscribers = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "user_subscriptions", joinColumns = { @JoinColumn(name = "subscriber_id") }, inverseJoinColumns = {
			@JoinColumn(name = "channel_id") })
	private Set<User> subscriptions = new HashSet<>();

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Date postedAt) {
		this.postedAt = postedAt;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}

	public boolean isAdmin() {
		return getRoles().stream().anyMatch(role -> Role.getAdministrationRoles().contains(role));
	}

	public boolean isFounder() {
		return roles.contains(Role.FOUNDER);
	}

	public boolean isImmutable() {
		return getRoles().stream().anyMatch(role -> Role.getImmutableRoles().contains(role));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<User> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<User> subscribers) {
		this.subscribers = subscribers;
	}

	public Set<User> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<User> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Message> getSubscriptionMessages() {
		return subscriptionMessages;
	}

	public void setSubscriptionMessages(Set<Message> subscriptionMessages) {
		this.subscriptionMessages = subscriptionMessages;
	}

	public Photo getAvatar() {
		return avatar;
	}

	public void setAvatar(Photo avatar) {
		this.avatar = avatar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}