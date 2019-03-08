package core.exceptions.couponExceptions;

public class CouponExpiredException extends CouponDaoException {
	private static final long serialVersionUID = 1L;

	public CouponExpiredException() {
	}

	public CouponExpiredException(String message) {
		super(message);
	}

	public CouponExpiredException(Throwable cause) {
		super(cause);
	}

	public CouponExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
