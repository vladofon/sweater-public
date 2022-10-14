package com.bootexample.spring.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	USER, ADMIN, FOUNDER;

	@Override
	public String getAuthority() {
		return name();
	}

	public static Set<Role> getPermittedRoles() {
		Set<Role> roles = new HashSet<>();
		roles.add(USER);
		roles.add(ADMIN);

		return roles;
	}

	public static Set<Role> getAdministrationRoles() {
		Set<Role> roles = new HashSet<>();
		roles.add(ADMIN);
		roles.add(FOUNDER);

		return roles;
	}

	public static Set<Role> getPrimaryRoles() {
		Set<Role> roles = new HashSet<>();
		roles.add(USER);

		return roles;
	}

	public static Set<Role> getImmutableRoles() {
		Set<Role> roles = new HashSet<>();
		roles.add(FOUNDER);

		return roles;
	}
}
