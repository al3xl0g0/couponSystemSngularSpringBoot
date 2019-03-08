package core.exceptions.companyExceptions;

import core.exceptions.CouponSystemException;

public class CompanyDaoException extends CouponSystemException {
	private static final long serialVersionUID = 1L;

	public CompanyDaoException() {
	}

	public CompanyDaoException(String message) {
		super(message);
	}

	public CompanyDaoException(Throwable cause) {
		super(cause);
	}

	public CompanyDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
