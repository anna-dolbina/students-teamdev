package windows;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jniwrapper.Bool;
import com.jniwrapper.Callback;
import com.jniwrapper.DefaultLibraryLoader;
import com.jniwrapper.Function;
import com.jniwrapper.Int;
import com.jniwrapper.Library;
import com.jniwrapper.Parameter;
import com.jniwrapper.Pointer;
import com.jniwrapper.WideString;

/**
 * This class represents information about top-level windows on the screen.
 * 
 * @author anna.dolbina
 * 
 */
public class TopLevelWindowsInformation implements WindowsInformation {
	private static final int ERROR_FAIL = 1;
	private static final int ERROR_SUCCESS = 0;
	private static final int MAX_CLASSNAME_LENGTH = 1024;
	private static Logger logger;

	static {
		logger = LoggerFactory.getLogger(TopLevelWindowsInformation.class);
	}

	/*
	 * Callback for creating the list of window handles.
	 */
	private static class EnumWindowsCallback extends Callback {

		private static final int RESULT_FAIL = 0;
		private static final int RESULT_OK = 1;
		private final Int windowHandle = new Int();
		private final Pointer.Void lParameter = new Pointer.Void();
		private final Int returnValue = new Int();

		private List<Integer> windowHandles = new ArrayList<Integer>();

		public EnumWindowsCallback() {
			init(new Parameter[] { windowHandle, lParameter }, returnValue);
		}

		public void callback() {
			try {
				windowHandles.add(new Integer((int) windowHandle.getValue()));
				returnValue.setValue(RESULT_OK);
			} catch (Exception e) {
				logger.error("Exception caught in callback: " + e.getMessage());
				returnValue.setValue(RESULT_FAIL);
			}

		}

		public List<Integer> getWindowHandles() {
			return windowHandles;
		}
	}

	private final List<WindowInformation> windowsInformation = new ArrayList<WindowInformation>();
	private final Library user32;
	private final Function isWindow;
	private final Function enumWindows;
	private final Function isWindowVisible;
	private final Function getWindowTextLength;
	private final Function getWindowText;
	private final Function getClassName;
	private final Function getWindowRect;
	static {
		DefaultLibraryLoader.getInstance().addPath("lib");
	}

	/**
	 * Constructs and initializes the instance of this class.
	 */
	public TopLevelWindowsInformation() {
		user32 = new Library("user32");
		isWindow = user32.getFunction("IsWindow");
		enumWindows = user32.getFunction("EnumWindows");
		isWindowVisible = user32.getFunction("IsWindowVisible");
		getWindowTextLength = user32.getFunction("GetWindowTextLengthW");
		getWindowText = user32.getFunction("GetWindowTextW");
		getClassName = user32.getFunction("GetClassNameW");
		getWindowRect = user32.getFunction("GetWindowRect");

		long errorCode = init();
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when forming a list");
		}

	}

	@Override
	public void refresh() {
		windowsInformation.clear();
		long errorCode = init();
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when refreshing a list");
		}
	}

	@Override
	public int size() {
		return windowsInformation.size();
	}

	@Override
	public WindowInformation get(int index) {
		return windowsInformation.get(index);
	}

	/*
	 * Fills the list with information about windows.
	 * 
	 * @return 0 if all topmost windows were processed; error code if something
	 * went wrong.
	 */
	private long init() {
		EnumWindowsCallback callback = new EnumWindowsCallback();
		long errorCode = enumWindows.invoke(null, callback, new Int(0));
		if (errorCode == ERROR_SUCCESS) {
			List<Integer> handles = callback.getWindowHandles();
			try {
				fillInformation(handles);
			} catch (RuntimeException e) {
				logger.error("Init exception: " + e.getMessage());
				errorCode = ERROR_FAIL;
			}
		}
		callback.dispose();
		return errorCode;

	}

	/*
	 * Fills information structures for all handles in list of retrieved
	 * handles.
	 * 
	 * @param handles Retrieved handles for windows.
	 */
	private void fillInformation(List<Integer> handles) {
		WindowInformation windowInformation = null;
		Iterator<Integer> it = handles.iterator();
		while (it.hasNext()) {
			int windowHandle = it.next().intValue();
			windowInformation = null;
			try {
				windowInformation = new WindowInformation(windowHandle,
						getWindowTitle(windowHandle),
						getWindowClassName(windowHandle),
						isWindowVisible(windowHandle),
						getTopLeftCorner(windowHandle));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(
						"Cannot retrieve window information: " + e.getMessage()
								+ " Window handle: " + windowHandle);
			}
			windowsInformation.add(windowInformation);
		}
	}

	/*
	 * Checks if handle is a valid window handle.
	 * 
	 * @param windowHandle the window handle to check
	 * 
	 * @return true if windowHandle is a valid window handle; false otherwise
	 */
	private boolean isWindow(int windowHandle) {
		if (windowHandle <= 0)
			return false;
		Bool res = new Bool();
		long errorCode = isWindow.invoke(res, new Int(windowHandle));
		
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when validating a handle");
		}
		return res.getValue();
	}

	/*
	 * Checks if window described by handle is visible.
	 * 
	 * @param windowHandle the window handle to check.
	 * 
	 * @return true if this is a visible window; false otherwise.
	 */
	private boolean isWindowVisible(int windowHandle) {
		checkWindowHandle(windowHandle);
		Bool res = new Bool();
		long errorCode = isWindowVisible.invoke(res, new Int(windowHandle));
		
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when checking visibility of "
							+ windowHandle);
		}
		return res.toBoolean().booleanValue();
	}

	/*
	 * Returns the title of the window described by handle.
	 * 
	 * @param windowHandle the window handle.
	 * 
	 * @return The title string if the window has a title; empty string
	 * otherwise.
	 */
	private String getWindowTitle(int windowHandle) {
		checkWindowHandle(windowHandle);
		Int windowTitleLength = new Int(getWindowTitleLength(windowHandle) + 1);
		long errorCode;
		WideString result = new WideString(
				(int) windowTitleLength.getValue() + 1);
		
		errorCode = getWindowText.invoke(null, new Int(windowHandle), result,
				windowTitleLength);
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when getting a window title of "
							+ windowHandle);
		}
		return result.getValue();

	}

	/*
	 * Retrieves the length of window title string
	 * 
	 * @param windowHandle the handle to the window
	 * 
	 * @return retrieved length of window title string
	 */
	private long getWindowTitleLength(int windowHandle) {
		Int windowTitleLength = new Int(0);
		long errorCode = getWindowTextLength.invoke(windowTitleLength, new Int(
				windowHandle));
		
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when getting a window title length of "
							+ windowHandle);
		}
		return windowTitleLength.getValue();
	}

	/*
	 * Returns the class name of the window described by handle.
	 * 
	 * @param windowHandle the window handle.
	 * 
	 * @return The class name string if the window handle is valid; empty string
	 * otherwise.
	 */
	private String getWindowClassName(int windowHandle) {
		checkWindowHandle(windowHandle);
		Int classNameLength = new Int(MAX_CLASSNAME_LENGTH);
		WideString result = new WideString(MAX_CLASSNAME_LENGTH);
		Int returnValue = new Int();
		
		long errorCode = getClassName.invoke(returnValue,
				new Int(windowHandle), result, classNameLength);
		if ((errorCode != ERROR_SUCCESS) || (returnValue.getValue() == 0)) {
			throw new LastErrorException(errorCode,
					"Failed to get class name of " + windowHandle);
		}
		return result.getValue();
	}

	/*
	 * Returns the point describing the top left corner of the window.
	 * 
	 * @param windowHandle.
	 * 
	 * @return a Point describing the top left corner; returns null if the
	 * window handle is not valid.
	 */
	private Point getTopLeftCorner(int windowHandle) {
		checkWindowHandle(windowHandle);
		Rect rect = new Rect();
		long errorCode = getWindowRect.invoke(null, new Int(windowHandle),
				new Pointer(rect));
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Failed to get top left corner coordinates of "
							+ windowHandle);
		}
		return new Point((int) rect.getLeft(), (int) rect.getTop());

	}

	/*
	 * Performs window handle check
	 * 
	 * @param windowHandle
	 *            the window handle to check
	 */
	private void checkWindowHandle(int windowHandle) {
		if (!isWindow(windowHandle))
			throw new IllegalArgumentException(
					"Passed argument is not a valid window handle.");
	}

}
