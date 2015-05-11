package com.zycx.frame.exception;

/**
 * 用户异常，权限不足
 * @author Ouyang<iisquare@163.com>
 *
 */
public class PermitDeniedException extends Exception {
	private static final long serialVersionUID = 1L;

	public PermitDeniedException() {
		super();
	}

	public PermitDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermitDeniedException(String message) {
		super(message);
	}

	public PermitDeniedException(Throwable cause) {
		super(cause);
	}
}
