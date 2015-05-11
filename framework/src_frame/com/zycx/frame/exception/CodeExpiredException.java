package com.zycx.frame.exception;

/**
 * 用户异常，交互码过期，用户在别处登录
 * @author Ouyang<iisquare@163.com>
 *
 */
public class CodeExpiredException extends Exception {
	private static final long serialVersionUID = 1L;

	public CodeExpiredException() {
		super();
	}

	public CodeExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeExpiredException(String message) {
		super(message);
	}

	public CodeExpiredException(Throwable cause) {
		super(cause);
	}
}
