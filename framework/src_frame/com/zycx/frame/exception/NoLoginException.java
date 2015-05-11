package com.zycx.frame.exception;

/**
 * 用户异常，未登录
 * @author Ouyang<iisquare@163.com>
 *
 */
public class NoLoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoLoginException() {
		super();
	}

	public NoLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoLoginException(String message) {
		super(message);
	}

	public NoLoginException(Throwable cause) {
		super(cause);
	}
}
