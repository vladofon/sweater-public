package com.bootexample.spring.services;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bootexample.spring.models.Photo;
import com.bootexample.spring.models.Role;
import com.bootexample.spring.models.User;
import com.bootexample.spring.repos.UserRepo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	MailSenderService mailSender;

	@Autowired
	PhotoService photoService;

	@Lazy
	@Autowired
	MessageService messageService;

	@Value("${hostname}")
	private String hostname;

	@Value("${advert.link}")
	private String advertisement;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);

		if (user == null) {
			throw new BadCredentialsException("User not found! Try to enter valid credentials or register new account");
		}

		return user;
	}

	public boolean addUser(User user) {
		User userFromDb = userRepo.findByUsername(user.getUsername());

		if (userFromDb != null) {
			return false;
		}

		user.setActive(false);
		user.setMuted(true);
		user.setPostedAt(new Date());
		user.setAvatar(photoService.getDefaultImage());
		user.setRoles(Collections.singleton(Role.USER));
		user.setActivationCode(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepo.save(user);

		if (!StringUtils.isEmpty(user.getEmail())) {
			sendMessage(user.getUsername(), user.getEmail(), user.getActivationCode());
		}

		return true;
	}

	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);

		if (user == null) {
			return false;
		}

		user.setActivationCode(null);
		user.setActive(true);
		user.setMuted(false);

		userRepo.save(user);

		return true;
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	private void sendMessage(String username, String email, String activationCode) {
		String message = String.format(
				"Hello, %s! \n" + "Welcome to Sweater. Please visit next link to activate profile: " + "%s/activate/%s",
				username, hostname, activationCode);

		mailSender.send(email, "Activation code", message);
	}

	public void saveUser(User user, String username, MultipartFile avatar) throws IOException {
		user.setUsername(username);

		if (!avatar.isEmpty()) {
			user.setAvatar(photoService.savePhoto(avatar));
		}

		userRepo.save(user);
	}

	public void assignRole(User user, Map<String, String> form) {
		user.getRoles().clear();
		Set<String> roles = Role.getPermittedRoles().stream().map(Role::name).collect(Collectors.toSet());

		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
			}
		}

		userRepo.save(user);
	}

	public void updateProfile(User user, String email, String password) {
		String currentEmail = user.getEmail();

		boolean isCurrentEmailDifferent = (currentEmail != null && currentEmail.equals(email));
		boolean isNewEmailDifferent = (email != null && email.equals(currentEmail));

		boolean isEmailChanged = isCurrentEmailDifferent || isNewEmailDifferent;

		if (isEmailChanged) {
			user.setActive(false);
			user.setEmail(email);
			user.setActivationCode(UUID.randomUUID().toString());

			sendMessage(user.getUsername(), user.getEmail(), user.getActivationCode());
		}

		if (!StringUtils.isEmpty(password)) {
			user.setPassword(passwordEncoder.encode(password));
		}

		userRepo.save(user);

	}

	public void subscribe(User currentUser, User user) {
		if (!user.equals(currentUser)) {
			user.getSubscribers().add(currentUser);

			userRepo.save(user);
		}
	}

	public void unsubscribe(User currentUser, User user) {
		if (!user.equals(currentUser)) {
			user.getSubscribers().remove(currentUser);

			messageService.deleteSubscriptionMessages(currentUser, user);

			userRepo.save(user);
		}
	}

	public void ban(User user) {
		if (!user.isImmutable()) {
			user.setActive(false);

			mailSender.send(user.getEmail(), "Account ban on Sweater-apps", "Your account [" + user.getUsername()
					+ "] was banned by administration. If your account gets unbanned, we will notify you. \n\nSweater-apps administration.");
		}
	}

	public void unban(User user) {
		if (!user.isImmutable()) {
			user.setActive(true);

			mailSender.send(user.getEmail(), "Account unban on Sweater-apps", "Your account [" + user.getUsername()
					+ "] was successfully unbanned by administration. Continue to enjoy our service! \n\nSweater-apps administration.");
		}
	}

	public Page<User> subscribersListForUser(Pageable pageable, User user) {
		return userRepo.findSubscribersByUser(pageable, user);
	}

	public Set<User> subscribersListForUser(User user) {
		return userRepo.findSubscribersByUser(user);
	}

	public Page<User> subscriptionsListForUser(Pageable pageable, User user) {
		return userRepo.findSubscriptionsByUser(pageable, user);
	}

	public void updateProfileImage(User user, MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			Photo savedPhoto = photoService.savePhoto(file);
			user.setAvatar(savedPhoto);
			userRepo.save(user);
		}
	}

	public void dropProfileImageToDefault(User user) {
		user.setAvatar(photoService.getDefaultImage());
		userRepo.save(user);
	}

	public boolean isUsersFriends(User currentUser, User interlocutor) {
		return userRepo.isUsersFriends(currentUser, interlocutor);
	}

	public Page<User> userFriends(Pageable pageable, User currentUser) {
		return userRepo.findUserFriends(pageable, currentUser);
	}

	public void mute(User user) {
		if (!user.isImmutable()) {
			user.setMuted(true);
		}
	}

	public void unmute(User user) {
		if (!user.isImmutable()) {
			user.setMuted(false);
		}
	}

	public void sendAdvertisement(User user) {
		if (!user.isImmutable()) {
			mailSender.send(user.getEmail(), "The most important thing in the world!",
					"Subscribe ERMGOD! Immediately! Link: " + advertisement);
		}
	}

}
