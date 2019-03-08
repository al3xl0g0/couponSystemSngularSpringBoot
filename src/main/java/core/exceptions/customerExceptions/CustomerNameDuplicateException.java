package core.exceptions.customerExceptions;

public class CustomerNameDuplicateException extends CustomerDaoException {
	private static final long serialVersionUID = 1L;

	public CustomerNameDuplicateException() {
	}

	public CustomerNameDuplicateException(String message) {
		super(message);
	}

	public CustomerNameDuplicateException(Throwable cause) {
		super(cause);
	}

	public CustomerNameDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNameDuplicateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
