package windows;

import java.awt.Point;

/**
 * Information about a single window.
 * 
 * @author anna.dolbina
 * 
 */
public class WindowInformation {
	private final int handle;
	private final String windowTitle;
	private final String windowClass;
	private final boolean visible;
	private final Point topLeftCorner;

	/**
	 * Constructs the instance of this class.
	 * 
	 * @param handle
	 *            Handle for this window.
	 * @param windowTitle
	 *            The window title.
	 * @param windowClass
	 *            The name of a window class of this window.
	 * @param visibility
	 *            Describes if the window is visible or not.
	 * @param topLeftCorner
	 *            Coordinates of top left corner of the window.
	 */
	public WindowInformation(int handle, String windowTitle,
			String windowClass, boolean visible, Point topLeftCorner) {

		this.handle = handle;
		this.windowTitle = windowTitle.trim();
		this.windowClass = windowClass.trim();
		this.visible = visible;
		this.topLeftCorner = topLeftCorner;
	}

	/**
	 * Returns the handle of the described window.
	 * 
	 * @return the handle of this window.
	 */
	public int getHandle() {
		return handle;
	}

	/**
	 * Returns the title of the described window.
	 * 
	 * @return the window title.
	 */
	public String getWindowTitle() {
		return windowTitle;
	}

	/**
	 * Returns the class name of the described window.
	 * 
	 * @return the window class
	 */
	public String getWindowClass() {
		return windowClass;
	}

	/**
	 * Returns the visibility of the described window.
	 * 
	 * @return true if this window is visible; false otherwise.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Returns the top left corner point of the window.
	 * 
	 * @return a Point class describing this point on the screen.
	 */
	public Point getTopLeftCorner() {
		return new Point(topLeftCorner);
	}

}
