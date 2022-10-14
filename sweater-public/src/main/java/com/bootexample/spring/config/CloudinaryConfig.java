package com.bootexample.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	@Value("${cloud.name}")
	private String name;

	@Value("${cloud.api-key}")
	private String apiKey;

	@Value("${cloud.api-secret}")
	private String secret;

	@Bean
	public Cloudinary getCloudinary() {

		Cloudinary cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", name, "api_key", apiKey, "api_secret", secret, "secure", true));

		SingletonManager manager = new SingletonManager();
		manager.setCloudinary(cloudinary);
		manager.init();

		return cloudinary;
	}
}
