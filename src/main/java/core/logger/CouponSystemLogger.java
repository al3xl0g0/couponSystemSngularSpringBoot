package core.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CouponSystemLogger {

	private String className;
	private Logger logger;

	private static CouponSystemLogger instance;

	private CouponSystemLogger(String className) {
		this.className = className;
	}

	public static CouponSystemLogger getInstance(String className) {
		if (instance == null) {
			instance = new CouponSystemLogger(className);
		}
		return instance;
	}

	public void log(Level level, String msg, Throwable thrown) {
		logger = Logger.getLogger(className);
		FileHandler fileHandler = null;
		try {
			fileHandler = new FileHandler("logs.txt", true);
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(true);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			logger.log(level, className + "\n" + msg, thrown);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileHandler.close();
		}
	}
}
