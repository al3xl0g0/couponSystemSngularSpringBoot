package core.exceptions.companyExceptions;

public class CompanyDoesntOwnCoupon extends CompanyDaoException {
	private static final long serialVersionUID = 1L;

	public CompanyDoesntOwnCoupon() {
	}

	public CompanyDoesntOwnCoupon(String message) {
		super(message);
	}

	public CompanyDoesntOwnCoupon(Throwable cause) {
		super(cause);
	}

	public CompanyDoesntOwnCoupon(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyDoesntOwnCoupon(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
