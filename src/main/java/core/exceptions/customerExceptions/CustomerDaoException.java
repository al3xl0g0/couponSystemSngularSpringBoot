package core.exceptions.customerExceptions;

import core.exceptions.CouponSystemException;

public class CustomerDaoException extends CouponSystemException {
	private static final long serialVersionUID = 1L;

	public CustomerDaoException() {
	}

	public CustomerDaoException(String message) {
		super(message);
	}

	public CustomerDaoException(Throwable cause) {
		super(cause);
	}

	public CustomerDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
