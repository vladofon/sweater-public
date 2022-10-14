package com.bootexample.spring.models.utils;

import com.bootexample.spring.models.User;

public abstract class MessageHelper {
	public static String getAuthorName(User author) {
		return author != null ? author.getUsername() : "<none>";
	}
}
