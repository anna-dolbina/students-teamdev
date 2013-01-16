package windows;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Information about all top-level windows on the screen.
 * 
 * @author anna.dolbina
 * 
 */
public class TopLevelWindowsInformation implements WindowsInformation {
	private static Logger logger;
	private static final int ERROR_SUCCESS = 0;
	private final List<WindowInformation> windowsInformation = new ArrayList<WindowInformation>();

	static {
		logger = LoggerFactory.getLogger(TopLevelWindowsInformation.class);
		System.loadLibrary("TopLevelWindowsInformation");
	}

	/**
	 * Constructs and initializes the instance of this class.
	 * 
	 * @throws LastErrorException
	 */
	public TopLevelWindowsInformation() throws LastErrorException {
		int errorCode = init();
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when forming a list");
		}
	}

	@Override
	public void refresh() throws LastErrorException {
		windowsInformation.clear();
		int errorCode = init();
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when refreshing a list");
		}
	}

	@Override
	public int size() {
		return windowsInformation.size();
	}

	/**
	 * Returns the window information corresponding to this index.
	 * 
	 * @param index
	 *            Index in list: must be between 0 and size()-1.
	 * @return The window information corresponding to this index.
	 */
	public WindowInformation get(int index) throws IndexOutOfBoundsException {
		return windowsInformation.get(index);
	}
	//TODO: optimize native code. 
	/*
	 * Native function for fulfilling the list with information about windows
	 * Returns 0 if all top-level windows were processed; error code if
	 * something went wrong.
	 */
	private native int init();

	/*
	 * Function checks visibility of a window described by the window handle.
	 * Exception is thrown if the passed handle doesn't correspond to an
	 * existing window.
	 */
	private native boolean isWindowVisible(int windowHandle)
			throws IllegalArgumentException;

	/*
	 * Function returns the title of a window described by the window handle.
	 * Exception is thrown if the passed handle doesn't correspond to an
	 * existing window.
	 */
	private native String getWindowTitle(int windowHandle)
			throws IllegalArgumentException;

	/*
	 * Function returns the class name of a window described by the window
	 * handle. Exception is thrown if the passed handle doesn't correspond to an
	 * existing window.
	 */
	private native String getWindowClassName(int windowHandle)
			throws IllegalArgumentException;

	/*
	 * Function returns the top left corner of a window described by the window
	 * handle. Exception is thrown if the passed handle doesn't correspond to an
	 * existing window.
	 */
	private native Point getTopLeftCorner(int windowHandle)
			throws IllegalArgumentException;
	
	/*
	 * Function creates a WindowInformation class corresponding to passed window handle.
	 * IllegalArgumentException can be thrown if passed handle is not valid.
	 */
	private native WindowInformation createWindowInformation(int windowHandle) throws IllegalArgumentException;
	/*
	 * Callback for fulfilling the list with information about windows Parameter
	 * windowHandle is the window handle passed from native function
	 */
	
	private void callback(int windowHandle) {
		try {
			WindowInformation windowInformation =createWindowInformation(windowHandle); 
			windowsInformation.add(windowInformation);
		} catch (IllegalArgumentException e) {
			logger.error("Cannot retrieve window information: "
					+ e.getMessage() + " Window handle: " + windowHandle);
			throw new RuntimeException(e);
		}	
	}

}
