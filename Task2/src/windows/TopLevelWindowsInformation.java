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
	static {
		DefaultLibraryLoader.getInstance().addPath("lib");
	}

	/**
	 * Constructs and initializes the instance of this class.
	 */
	public TopLevelWindowsInformation() {
		user32 = new Library("user32");
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
	 * @return true if all topmost windows were processed; false if something
	 * went wrong.
	 */
	private long init() {
		Function enumWindows = user32.getFunction("EnumWindows");
		EnumWindowsCallback callback = new EnumWindowsCallback();
		long errorCode = enumWindows.invoke(null, callback, new Int(0));
		if (errorCode == ERROR_SUCCESS) {
			List<Integer> handles = callback.getWindowHandles();
			try {
				fillInformation(handles);
			} catch (RuntimeException e) {
				logger.error("Init exception: " + e.getMessage());
				errorCode=ERROR_FAIL;
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
	 * @return true if windowHandle is a valid window handle ; false otherwise
	 */
	private boolean isWindow(int windowHandle) {
		Function isWindow = user32.getFunction("IsWindow");
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
		if (!isWindow(windowHandle))
			throw new IllegalArgumentException(
					"Passed argument is not a valid window handle.");
		Function isVisible = user32.getFunction("IsWindowVisible");
		Bool res = new Bool();
		long errorCode = isVisible.invoke(res, new Int(windowHandle));
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
		if (!isWindow(windowHandle))
			throw new IllegalArgumentException(
					"Passed argument is not a valid window handle.");
		Int windowTitleLength = new Int(0);
		Function textLength = user32.getFunction("GetWindowTextLengthW");
		long errorCode = textLength.invoke(windowTitleLength, new Int(
				windowHandle));
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when getting a window title length of "
							+ windowHandle);
		}
		windowTitleLength = new Int((int) windowTitleLength.getValue() + 1);
		WideString result = new WideString(
				(int) windowTitleLength.getValue() + 1);
		Function getText = user32.getFunction("GetWindowTextW");
		errorCode = getText.invoke(null, new Int(windowHandle), result,
				windowTitleLength);
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Error has occurred when getting a window title of "
							+ windowHandle);
		}
		return result.getValue();

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
		if (!isWindow(windowHandle))
			throw new IllegalArgumentException(
					"Passed argument is not a valid window handle.");

		Int classNameLength = new Int(MAX_CLASSNAME_LENGTH);
		WideString result = new WideString(MAX_CLASSNAME_LENGTH + 1);
		Function getText = user32.getFunction("GetClassNameW");
		Int returnValue = new Int();
		long errorCode = getText.invoke(returnValue, new Int(windowHandle),
				result, classNameLength);
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
		if (!isWindow(windowHandle))
			throw new IllegalArgumentException(
					"Passed argument is not a valid window handle.");
		Rect rect = new Rect();
		Function getRect = user32.getFunction("GetWindowRect");
		long errorCode = getRect.invoke(null, new Int(windowHandle),
				new Pointer(rect));
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"Failed to get top left corner coordinates of "
							+ windowHandle);
		}
		return new Point((int) rect.getLeft(), (int) rect.getTop());

	}

}
