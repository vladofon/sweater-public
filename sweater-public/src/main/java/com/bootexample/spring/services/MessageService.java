package com.bootexample.spring.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bootexample.spring.models.Message;
import com.bootexample.spring.models.PrivateMessage;
import com.bootexample.spring.models.User;
import com.bootexample.spring.models.dto.MessageDto;
import com.bootexample.spring.repos.MessageRepo;
import com.bootexample.spring.repos.PrivateMessageRepo;
import com.bootexample.spring.repos.UserRepo;

@Service
public class MessageService {
	@Autowired
	MessageRepo messageRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserService userService;

	@Autowired
	PrivateMessageRepo privateMessageRepo;

	public void save(Message message, Set<User> subscribers) {
		if (!subscribers.isEmpty()) {
			message.getSubscribers().addAll(subscribers);
		}
		messageRepo.save(message);
	}

	public Page<MessageDto> messageList(String filter, Pageable pageable, User user) {
		if (filter != null && !filter.isEmpty()) {
			return messageRepo.findByTag(filter, pageable, user);
		} else {
			return messageRepo.findAll(pageable, user);
		}
	}

	public Page<MessageDto> messageListForUser(Pageable pageable, User author, User user) {
		return messageRepo.findByUser(pageable, author, user);
	}

	public Page<MessageDto> messageListByUserSubscriptions(Pageable pageable, User user) {
		return messageRepo.findAllSubscriptionsByUser(pageable, user);
	}

	public Page<PrivateMessage> getCorrespondenceList(Pageable pageable, User currentUser, User interlocutor) {
		return privateMessageRepo.findCorrespondence(pageable, currentUser, interlocutor);
	}

	public PrivateMessage saveCorrespondence(PrivateMessage message) {
		return privateMessageRepo.save(message);
	}

	public boolean hasRights(User currentUser, User interlocutor) {
		boolean isUsersFriends = userService.isUsersFriends(currentUser, interlocutor);

		if (isUsersFriends) {
			return true;
		}

		return false;
	}

	public void deleteSubscriptionMessages(User currentUser, User user) {
		Set<Message> subMessages = messageRepo.findAllSubscriptionsByUser(currentUser);
		subMessages.removeIf(message -> message.getAuthor() == user);

		currentUser.setSubscriptionMessages(subMessages);
		userRepo.save(currentUser);
	}

}
