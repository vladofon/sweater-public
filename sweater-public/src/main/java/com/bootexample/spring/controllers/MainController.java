package com.bootexample.spring.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.bootexample.spring.models.Comment;
import com.bootexample.spring.models.Message;
import com.bootexample.spring.models.PrivateMessage;
import com.bootexample.spring.models.User;
import com.bootexample.spring.models.dto.MessageDto;
import com.bootexample.spring.repos.CommentRepo;
import com.bootexample.spring.repos.MessageRepo;
import com.bootexample.spring.services.CommentService;
import com.bootexample.spring.services.MessageService;
import com.bootexample.spring.services.UserService;

@Controller
public class MainController {
	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private MessageService messageService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Value("${upload.path}")
	private String uploadPath;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/messages/{message}/delete")
	public String deleteMessage(@PathVariable Message message) {

		messageRepo.delete(message);
		return "redirect:/main";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/messages/{message}/{comment}/delete")
	public String deleteComment(@PathVariable Long message, @PathVariable Comment comment) {

		commentRepo.delete(comment);
		return "redirect:/messages/" + message;
	}

	@GetMapping
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/main")
	public String main(@AuthenticationPrincipal User user, @RequestParam(required = false) String filter, Model model,
			@PageableDefault(sort = { "postedAt", "id" }, direction = Direction.DESC) Pageable pageable) {

		Page<MessageDto> page = messageService.messageList(filter, pageable, user);

		model.addAttribute("page", page);
		model.addAttribute("url", "/main");
		model.addAttribute("filter", filter);

		return "main";
	}

	@PostMapping("/create")
	public RedirectView create(@AuthenticationPrincipal User user, @Valid Message message, BindingResult validation,
			Model model, @PageableDefault(sort = { "postedAt", "id" }, direction = Direction.DESC) Pageable pageable,
			RedirectAttributes attributes) throws IllegalStateException, IOException {

		message.setAuthor(user);
		message.setPostedAt(new Date());

		if (validation.hasErrors() || user.isMuted()) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(validation);
			Page<MessageDto> page = messageService.messageList("", pageable, user);

			if (user.isMuted())
				errorsMap.put("mutedError", "You were muted!");

			attributes.addFlashAttribute("page", page);
			attributes.addFlashAttribute("error", errorsMap);
			attributes.addFlashAttribute("message", message);
			attributes.addFlashAttribute("url", "/main");

			return new RedirectView("/main");

		} else {

			messageService.save(message, userService.subscribersListForUser(user));

			model.addAttribute("message", null);
		}

		return new RedirectView("/main");
	}

	@GetMapping("/user-messages/{user}")
	public String userMessages(@AuthenticationPrincipal User currentUser, @PathVariable User user,
			@RequestParam(required = false) Message message, Model model,
			@PageableDefault(sort = { "postedAt", "id" }, direction = Direction.DESC) Pageable pageable) {

		Page<MessageDto> page = messageService.messageListForUser(pageable, user, currentUser);

		model.addAttribute("page", page);
		model.addAttribute("url", "/user-messages/" + user.getId());
		model.addAttribute("userChannel", user);
		model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
		model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
		model.addAttribute("subscribersCount", user.getSubscribers().size());
		model.addAttribute("message", message);
		model.addAttribute("isCurrentUser", currentUser.equals(user));

		return "user_messages";
	}

	@PostMapping("/user-messages/{user}")
	public RedirectView updateMessage(@AuthenticationPrincipal User currentUser, @PathVariable Long user,
			@RequestParam("id") Message message, @RequestParam("text") String text, @RequestParam("tag") String tag,
			RedirectAttributes attributes) throws IOException {

		if (message.getAuthor().equals(currentUser)) {
			if (!StringUtils.isEmpty(text)) {
				if (text.length() > 2048) {
					Map<String, String> errorsMap = new HashMap<>();
					errorsMap.put("textError", "Message is too long (more than 2048 characters)");
					attributes.addFlashAttribute("error", errorsMap);
					attributes.addFlashAttribute("message", message);

					return new RedirectView("/user-messages/" + user + "?message=" + message.getId());
				}
				message.setText(text);
			} else {
				Map<String, String> errorsMap = new HashMap<>();
				errorsMap.put("textError", "Message is empty");
				attributes.addFlashAttribute("error", errorsMap);
				attributes.addFlashAttribute("message", message);

				return new RedirectView("/user-messages/" + user + "?message=" + message.getId());
			}

			if (!StringUtils.isEmpty(tag)) {
				message.setTag(tag);
			}

			if (!currentUser.isMuted())
				messageRepo.save(message);
		}

		return new RedirectView("/user-messages/" + user);
	}

	@GetMapping("/messages/{message}/like")
	public String like(@AuthenticationPrincipal User user, Message message, RedirectAttributes redirectAttributes,
			@RequestHeader(required = false) String referer) {
		Set<User> likes = message.getLikes();

		if (likes.contains(user)) {
			likes.remove(user);
		} else {
			likes.add(user);
		}

		UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

		components.getQueryParams().entrySet()
				.forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

		return "redirect:" + components.getPath();
	}

	@GetMapping("/messages/{message}")
	public String messagePage(@PathVariable(required = false) Message message, @PathVariable(required = false) MessageDto messageDto, Model model,
			@PageableDefault(sort = { "postedAt", "id" }, direction = Direction.ASC) Pageable pageable) {

		if (message == null) {
			model.addAttribute("header", "404");
			model.addAttribute("description", "This message does not exist");
			return "notification";
		}

		Page<Comment> page = commentService.commentList(pageable, message);
		model.addAttribute("message", messageDto);
		model.addAttribute("page", page);
		model.addAttribute("url", "/messages/" + messageDto.getId());

		return "message";
	}

	@PostMapping("/messages/{message}")
	public RedirectView createComment(@AuthenticationPrincipal User user, @Valid Comment comment,
			BindingResult validation, Model model, @PathVariable("message") Message message, RedirectAttributes attributes)
			throws IOException {

		if (validation.hasErrors() || user.isMuted()) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(validation);

			if (user.isMuted())
				errorsMap.put("mutedError", "You were muted!");

			attributes.addFlashAttribute("error", errorsMap);
			attributes.addFlashAttribute("comment", comment);
			attributes.addFlashAttribute("url", "/main");

			return new RedirectView("/messages/" + message.getId());

		} else {
			commentService.save(comment, user, message);
		}

		return new RedirectView("/messages/" + message.getId());

		// return "redirect:/messages/" + message.getId();
	}

	@GetMapping("/channels")
	public String getChannelsMessages(@AuthenticationPrincipal User user, Model model,
			@PageableDefault(sort = { "postedAt" }, direction = Direction.ASC) Pageable pageable) {

		Page<MessageDto> page = messageService.messageListByUserSubscriptions(pageable, user);

		model.addAttribute("subscriptions", userService.subscriptionsListForUser(pageable, user));
		model.addAttribute("userAvatar", user.getAvatar().getImage());
		model.addAttribute("page", page);
		model.addAttribute("url", "/channels");

		return "subscription_messages";
	}

	@GetMapping("/private-messages")
	public String privateMessages(@AuthenticationPrincipal User user, Model model,
			@PageableDefault(sort = { "id" }, direction = Direction.DESC) Pageable pageable) {
		Page<User> page = userService.userFriends(pageable, user);

		model.addAttribute("page", page);
		model.addAttribute("url", "/private-messages");
		model.addAttribute("type", "correspondences");
		model.addAttribute("description", "user");

		return "private_messages";
	}

	@GetMapping("/private-messages/{user}")
	public String correspondence(@AuthenticationPrincipal User currentUser, @PathVariable User user, Model model,
			@PageableDefault(sort = { "id" }, direction = Direction.ASC) Pageable pageable) {

		if (!userService.isUsersFriends(currentUser, user)) {
			model.addAttribute("header", "403-Forbiden");
			model.addAttribute("description", "This user is not your friend!");
			return "notification";
		}

		Page<PrivateMessage> page = messageService.getCorrespondenceList(pageable, currentUser, user);

		model.addAttribute("page", page);
		model.addAttribute("url", "/private-messages/" + user.getId());
		model.addAttribute("interlocutor", user.getUsername());
		model.addAttribute("interlocutorId", user.getId());

		return "correspondence";
	}

	@PostMapping("/private-messages/{user}")
	public RedirectView correspondenceMessage(@AuthenticationPrincipal User currentUser, @PathVariable User user,
			@Valid PrivateMessage message, BindingResult validation, Model model, RedirectAttributes attributes) {

		if (validation.hasErrors() || !userService.isUsersFriends(currentUser, user)) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(validation);

			if (!userService.isUsersFriends(currentUser, user))
				return new RedirectView("/private-messages");

			attributes.addFlashAttribute("error", errorsMap);
			attributes.addFlashAttribute("message", message);
			attributes.addFlashAttribute("url", "/main");

			return new RedirectView("/private-messages/" + user.getId());

		} else {
			message.setSender(currentUser);
			message.setReciever(user);
			message.setPostedAt(new Date());

			messageService.saveCorrespondence(message);
		}

		return new RedirectView("/private-messages/" + user.getId());

		// return "redirect:/private-messages/" + user.getId();
	}
}
