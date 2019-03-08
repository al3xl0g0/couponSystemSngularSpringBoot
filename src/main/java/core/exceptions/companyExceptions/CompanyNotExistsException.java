package core.exceptions.companyExceptions;

public class CompanyNotExistsException extends CompanyDaoException {
	private static final long serialVersionUID = 1L;

	public CompanyNotExistsException() {
	}

	public CompanyNotExistsException(String message) {
		super(message);
	}

	public CompanyNotExistsException(Throwable cause) {
		super(cause);
	}

	public CompanyNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompanyNotExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
