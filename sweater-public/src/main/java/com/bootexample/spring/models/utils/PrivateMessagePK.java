package com.bootexample.spring.models.utils;

import java.io.Serializable;

import com.bootexample.spring.models.User;

public class PrivateMessagePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8318747358293351369L;

	protected User sender;
	protected User reciever;

	public PrivateMessagePK() {
		// constructor for Spring
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reciever == null) ? 0 : reciever.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		PrivateMessagePK other = (PrivateMessagePK) obj;
		if (reciever == null) {
			if (other.reciever != null)
				return false;
		} else if (!reciever.equals(other.reciever))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}

}
