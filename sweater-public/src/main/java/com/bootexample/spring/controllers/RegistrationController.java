package com.bootexample.spring.controllers;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.bootexample.spring.models.User;
import com.bootexample.spring.models.dto.CaptchaResponseDto;
import com.bootexample.spring.services.UserService;

@Controller
public class RegistrationController {
	private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
	@Autowired
	private UserService userService;

	@Value("${recaptcha.secret}")
	private String secret;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String createUser(@RequestParam("g-recaptcha-response") String captchaResponce,
			@RequestParam("password2") String passwordConfirm, @ModelAttribute @Valid User user, BindingResult validation,
			Model model) {

		String url = String.format(CAPTCHA_URL, secret, captchaResponce);
		CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

		if (!response.isSuccess()) {
			model.addAttribute("captchaError", "Fill re-CAPTCHA");
		}

		boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
		if (isConfirmEmpty) {
			model.addAttribute("password2Error", "Password confirmation field cannot be empty");
		}

		if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
			model.addAttribute("passwordError", "Passwords are not equals");
			return "registration";
		}

		if (isConfirmEmpty || validation.hasErrors() || !response.isSuccess()) {
			Map<String, String> errorsMap = ControllerUtils.getErrors(validation);

			model.mergeAttributes(errorsMap);
			return "registration";
		}

		if (!userService.addUser(user)) {
			model.addAttribute("usernameError", "This user already exist!");
			model.addAttribute("user", new User());
			return "registration";
		}

		model.addAttribute("header", "Account successfully created!");
		model.addAttribute("description", "Please, verify your email address to unlock profile");
		return "notification";
	}

	@GetMapping("/activate/{code}")
	public String activate(Model model, @PathVariable String code) {
		boolean isActivated = userService.activateUser(code);

		if (isActivated) {
			model.addAttribute("type", "success");
			model.addAttribute("message", "Successefully activated!");
		} else {
			model.addAttribute("type", "danger");
			model.addAttribute("message", "Activation code is not found or already used!");
		}

		return "login";
	}
}
