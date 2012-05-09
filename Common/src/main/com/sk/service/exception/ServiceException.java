package com.sk.service.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -4121998000559018949L;

	public ServiceException(Throwable e) {
		super(e);
	}
	
	public ServiceException(String message) {
		super(message);
	}

}
