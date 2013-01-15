package windows;

/**
 * Describes the last system error which has occurred.
 * 
 * @author anna.dolbina
 * 
 */
public class LastErrorException extends RuntimeException {
	private final long errorCode;
	private final String errorDescription;
	private final String sourceMessage;

	private static final long serialVersionUID = 1L;

	static {
		System.loadLibrary("LastErrorException");
	}

	/**
	 * Creates and initializes the instance of LastErrorException, containing
	 * information about the system error that has occurred.
	 * 
	 * @param errorCode
	 *            the code of the system error
	 * @param sourceMessage
	 *            The message passed from the error source.
	 */
	public LastErrorException(int errorCode,
			String sourceMessage) {
		this(errorCode,retrieveErrorDescription(errorCode),sourceMessage);
	}

	private static native String retrieveErrorDescription(int errorCode);

	/*
	 * Constructs and initializes the instance of LastErrorException.
	 * 
	 * @param errorCode the code of the system error
	 * 
	 * @param errorDescription the description of this error
	 * 
	 * @param sourceMessage the message passed from the error source
	 */
	private LastErrorException(int errorCode, String errorDescription,
			String sourceMessage) {
		super(sourceMessage + ". System error code: " + errorCode
				+ " Description: " + errorDescription);

		this.errorCode = errorCode;
		this.sourceMessage = sourceMessage;
		this.errorDescription = errorDescription;
	}

	/**
	 * Returns the code of the system error.
	 * 
	 * @return the code of the error that has occurred.
	 */
	public long getErrorCode() {
		return errorCode;
	}

	/**
	 * Returns the description of the system error.
	 * 
	 * @return a string representing the description of the system error.
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Returns the message passed from the error source.
	 * 
	 * @return the sourceMessage
	 */
	public String getSourceMessage() {
		return sourceMessage;
	}

}
