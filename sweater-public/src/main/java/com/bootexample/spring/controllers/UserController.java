package com.bootexample.spring.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.bootexample.spring.models.Role;
import com.bootexample.spring.models.User;
import com.bootexample.spring.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String userList(Model model, @PageableDefault(sort = { "id" }, direction = Direction.DESC) Pageable pageable) {

		Page<User> page = userService.findAll(pageable);

		model.addAttribute("page", page);
		model.addAttribute("url", "/user");
		model.addAttribute("type", "users");
		model.addAttribute("description", "user");
		return "admin";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("{user}")
	public String userEditForm(@PathVariable User user, Model model) {
		model.addAttribute("userToEdit", user);

		return "user_edit";
	}

	@PreAuthorize("hasAuthority('FOUNDER')")
	@GetMapping("{user}/roles")
	public String adminEditForm(@PathVariable User user, Model model) {

		model.addAttribute("user", user);
		model.addAttribute("roles", Role.getPermittedRoles());
		model.addAttribute("primaryRoles", Role.getPrimaryRoles());
		model.addAttribute("adminRoles", Role.getAdministrationRoles());

		return "user_edit_role";
	}

	@PreAuthorize("hasAuthority('FOUNDER')")
	@GetMapping("{user}/ban")
	public String banUser(@PathVariable User user) {
		userService.ban(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('FOUNDER') || hasAuthority('ADMIN')")
	@GetMapping("{user}/mute")
	public String muteUser(@PathVariable User user) {
		userService.mute(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('FOUNDER') || hasAuthority('ADMIN')")
	@GetMapping("{user}/unmute")
	public String unmuteUser(@PathVariable User user) {
		userService.unmute(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('FOUNDER')")
	@GetMapping("{user}/unban")
	public String unbanUser(@PathVariable User user) {
		userService.unban(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('FOUNDER') || hasAuthority('ADMIN')")
	@GetMapping("{user}/default")
	public String resetAvavatar(@PathVariable User user) {
		userService.dropProfileImageToDefault(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('FOUNDER') || hasAuthority('ADMIN')")
	@GetMapping("{user}/advertisement")
	public String sendAdvertisement(@PathVariable User user) {
		userService.sendAdvertisement(user);

		return "redirect:/user/" + user.getId();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public RedirectView userSave(@AuthenticationPrincipal User admin, @RequestParam("userId") User user,
			@RequestParam String username, @RequestParam MultipartFile file, Model model, RedirectAttributes attributes)
			throws IOException {

		if (!user.isAdmin() || !user.isImmutable() && admin.isFounder()) {
			userService.saveUser(user, username, file);
		}

		attributes.addFlashAttribute("qwerty", "testing redirect attributes...");

		return new RedirectView("/user");
	}

	@PreAuthorize("hasAuthority('FOUNDER')")
	@PostMapping("/roles")
	public String userSaveRoles(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {

		userService.assignRole(user, form);

		return "redirect:/user/" + user.getId();
	}

	@GetMapping("profile")
	public String getProfile(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("avatar", user.getAvatar());

		return "profile";
	}

	@PostMapping("profile")
	public String updateProfile(@AuthenticationPrincipal User user, @RequestParam String email,
			@RequestParam String password, Model model) {

		userService.updateProfile(user, email, password);

		model.addAttribute("header", user.getUsername() + " info successfully updated!");
		model.addAttribute("description", "Verify link sent on your email address.");
		return "redirect:/login?logout";
	}

	@PostMapping("profile/change-avatar")
	public String updateProfileImage(@AuthenticationPrincipal User user, MultipartFile file, Model model)
			throws IOException {

		if (!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
			model.addAttribute("header", "Wrong image extension!");
			model.addAttribute("description",
					"Extension [" + file.getContentType() + "] not supported. Use png or jpg(jpeg).");
			return "notification";
		}

		userService.updateProfileImage(user, file);
		model.addAttribute("header", "Successfully updated!");
		model.addAttribute("description", "Relogin to see updated image");
		model.addAttribute("login", true);

		return "notification";
	}

	@GetMapping("/subscribe/{user}")
	public String subscribe(@AuthenticationPrincipal User currentUser, @PathVariable User user) {
		userService.subscribe(currentUser, user);

		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("/unsubscribe/{user}")
	public String unsubscribe(@AuthenticationPrincipal User currentUser, @PathVariable User user) {
		userService.unsubscribe(currentUser, user);

		return "redirect:/user-messages/" + user.getId();
	}

	@GetMapping("{user}/{type}/list")
	public String userList(@PathVariable User user, @PathVariable String type, Model model,
			@PageableDefault(sort = { "id" }, direction = Direction.DESC) Pageable pageable) {
		model.addAttribute("userChannel", user);
		model.addAttribute("type", type);

		if ("subscribers".equals(type)) {
			model.addAttribute("page", userService.subscribersListForUser(pageable, user));
			model.addAttribute("url", "/user/" + user.getId() + "/subscribers/list");
			model.addAttribute("description", "subscriber");
		} else if ("subscriptions".equals(type)) {
			model.addAttribute("page", userService.subscriptionsListForUser(pageable, user));
			model.addAttribute("url", "/user/" + user.getId() + "/subscriptions/list");
			model.addAttribute("description", "content maker");
		}

		return "subscriptions";
	}

}
