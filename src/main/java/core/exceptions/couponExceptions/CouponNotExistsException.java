package core.exceptions.couponExceptions;

public class CouponNotExistsException extends CouponDaoException {
	private static final long serialVersionUID = 1L;

	public CouponNotExistsException() {
	}

	public CouponNotExistsException(String message) {
		super(message);
	}

	public CouponNotExistsException(Throwable cause) {
		super(cause);
	}

	public CouponNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponNotExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
