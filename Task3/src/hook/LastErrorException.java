package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jniwrapper.DefaultLibraryLoader;
import com.jniwrapper.Function;
import com.jniwrapper.Library;
import com.jniwrapper.Parameter;
import com.jniwrapper.Pointer;
import com.jniwrapper.Str;
import com.jniwrapper.UInt32;

/**
 * Describes the last system error which has occurred.
 * 
 * @author anna.dolbina
 * 
 */
public class LastErrorException extends RuntimeException {
	private static Logger logger;
	private final long errorCode;
	private final String errorDescription;
	private final String sourceMessage;
	private static final long serialVersionUID = 1L;
	private static final int FORMAT_MESSAGE_FROM_SYSTEM = 0x1000;
    private static final long LANG_NEUTRAL = 0x00;
	private static final long SUBLANG_DEFAULT = 0x01;

	static {
		DefaultLibraryLoader.getInstance().addPath("lib");
		new Library("kernel32");
		logger = LoggerFactory.getLogger(LastErrorException.class);
	}

	/**
	 * Creates and initializes the instance of LastErrorException, containing
	 * information about the system error that has occurred.
	 * 
	 * @param errorCode
	 *            the code of system error
	 * @param sourceMessage
	 *            The message passed from the error source.
	 * @return the LastErrorException which corresponds to last system error
	 *         code.
	 */
	public LastErrorException(long errorCode, String sourceMessage) {
		this(errorCode, retrieveErrorDescription(errorCode), sourceMessage);
	}

	/**
	 * Creates and initializes the instance of LastErrorException, containing
	 * information about the system error that has occurred.
	 * 
	 * @param errorCode
	 *            the code of the system error
	 */
	public LastErrorException(int errorCode) {
		this(errorCode, retrieveErrorDescription(errorCode), "");
	}

	/*
	 * Retrieves a description string for the system error code
	 * 
	 * @param errorCode the code of system error
	 * 
	 * @return the string description for this error code
	 */
	private static String retrieveErrorDescription(long errorCode) {
		UInt32 descriptionLength = new UInt32(256);
		UInt32 errorCodeParameter = new UInt32(errorCode);
		Str description = new Str();
		

		long error = formatMessage(descriptionLength, errorCodeParameter,
				description);
		if ((error != 0) || (descriptionLength.getValue() == 0)) {
			logger.error("FormatMessage error: error code "+error);
			return "Cannot retrieve description for error code " + errorCode;
		}

		String result = description.getValue().trim();
		return result;
	}

	/*
	 * Performs FormatMessageW call
	 * 
	 * @param bufferSize The description string length to initialize. Will be
	 * initialized after call
	 * 
	 * @param errorCode The error code
	 * 
	 * @param description a pointer to the description string. Will be
	 * initialized after call
	 * 
	 * @return 0 if succeeded; error code if failed
	 */
	private static long formatMessage(UInt32 descriptionLength,
			UInt32 errorCode, Str description) {
		Pointer p=new Pointer(description);
		Parameter[] formatParameters = new Parameter[] {
				new UInt32(FORMAT_MESSAGE_FROM_SYSTEM), 
				new Pointer.Void(),
				errorCode,
				new UInt32(MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT)),
				p, 
				descriptionLength, 
				new Pointer.Void() };

		long error = Function.call("kernel32", "FormatMessageW",
				descriptionLength, formatParameters);
		return error;
	}

	/*
	 * An implementation of MAKELANGID macro
	 */
	private static long MAKELANGID(long lang, long sublang) {
		return ((((sublang)) << 10) | (lang));
	}

	/*
	 * Constructs and initializes the instance of LastErrorException.
	 * 
	 * @param errorCode the code of the system error
	 * 
	 * @param errorDescription the description of this error
	 * 
	 * @param sourceMessage the message passed from the error source
	 */
	private LastErrorException(long errorCode, String errorDescription,
			String sourceMessage) {
		super(sourceMessage + " System error code: " + errorCode
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
