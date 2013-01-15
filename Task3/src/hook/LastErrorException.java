package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jniwrapper.DefaultLibraryLoader;
import com.jniwrapper.Function;
import com.jniwrapper.Parameter;
import com.jniwrapper.Pointer;
import com.jniwrapper.UInt32;
import com.jniwrapper.WideString;

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
	private static final long FORMAT_MESSAGE_FROM_SYSTEM = 0x00001000;
	private static final long FORMAT_MESSAGE_ALLOCATE_BUFFER = 0x00000100;
	private static final long LANG_NEUTRAL = 0x00;
	private static final long SUBLANG_DEFAULT = 0x01;

	static {
		DefaultLibraryLoader.getInstance().addPath("lib");
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
	/*
	 * Retrieves a description string for the system error code
	 * 
	 * @param errorCode the code of system error
	 * 
	 * @return the string description for this error code
	 */
	private static String retrieveErrorDescription(long errorCode) {
		UInt32 bufferSize=new UInt32(0);
		UInt32 errorCodeParameter=new UInt32(errorCode);
		Pointer description=new Pointer(WideString.class);
		Parameter[] formatParameters=new Parameter[]{
				new UInt32(FORMAT_MESSAGE_ALLOCATE_BUFFER | 
		        FORMAT_MESSAGE_FROM_SYSTEM),
		        new Pointer.Void(0),
		        errorCodeParameter,
		        new UInt32(MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT)),
		        new Pointer(description),
		        new UInt32(0),new Pointer.Void(0)
		};
		long error=Function.call("kernel32","FormatMessageW", bufferSize, formatParameters);
		if((error!=0)||(bufferSize.getValue()==0)){
			return "Cannot retrieve description for error code "+errorCode;
		}
		String result=description.getReferencedObject().toString();
		error=Function.call("kernel32","LocalFree", description, description);
		if((error!=0)||(!description.isNull())){
			logger.error("An error with code "+error+" occured when freeing allocated buffer");
		}
		return result;
	}

	private static long MAKELANGID(long lang,
			long sublang) {
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
