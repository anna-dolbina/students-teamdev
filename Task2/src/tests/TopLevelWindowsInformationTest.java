package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import windows.TopLevelWindowsInformation;
import windows.WindowInformation;
import windows.WindowsInformation;

public class TopLevelWindowsInformationTest {
	private static final int WINDOW_HEIGHT = 240;
	private static final int WINDOW_WIDTH = 320;
	private static final int TOP_LEFT_CORNER_Y = 10;
	private static final int TOP_LEFT_CORNER_X = 10;
	private static final String WINDOW_TITLE = "TestWindow";

	/**
	 * Finds a previously created visible window on the screen using
	 * TopLevelWindowsInformation.
	 */
	@Test
	public void findingVisibleWindow() {
		WindowInformation window = null;
		try {
			createVisibleFrame();
			List<WindowInformation> visibleWindows = createVisibleWindowsList();
			window = findWindowInList(WINDOW_TITLE, new Point(
					TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y), visibleWindows);

		} catch (RuntimeException e) {
			e.printStackTrace();
			fail("Exception was thrown: " + e.getMessage());
		}
		assertNotNull("A window created from test was not found on the screen",window);
	}

	/*
	 * Tries to find information about a window with specified title and top
	 * left corner coordinates
	 * 
	 * @param windowTitle
	 *            a title of the window
	 * @param topLeftCorner
	 *            coordinates of the top left corner of the window
	 * @param windows
	 *            a list to look through
	 * 
	 * @return the information about the window if such window is present in the
	 *         list; null if it wasn't found
	 */
	private WindowInformation findWindowInList(String windowTitle,
			Point topLeftCorner, List<WindowInformation> windows) {
		WindowInformation window;
		Iterator<WindowInformation> it = windows.iterator();
		while (it.hasNext()) {
			window = it.next();
			if (checkWindowTitle(window, windowTitle)
					&& checkTopLeftCorner(window, topLeftCorner)) {
				return window;
			}
		}
		return null;
	}

	/*
	 * Creates a list with information about visible top-level windows on the
	 * screen
	 * 
	 * @return a filled list with information about visible top-level windows on
	 * the screen
	 */
	private List<WindowInformation> createVisibleWindowsList() {
		List<WindowInformation> visibleWindows = new ArrayList<WindowInformation>();

		WindowsInformation informationList = new TopLevelWindowsInformation();
		WindowInformation window;

		for (int i = 0; i < informationList.size(); i++) {
			window = informationList.get(i);
			if (window.isVisible()) {
				visibleWindows.add(window);
			}
		}
		return visibleWindows;
	}

	/*
	 * Creates a visible JFrame on the screen
	 */
	private void createVisibleFrame() {
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setBounds(TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y, WINDOW_WIDTH,
				WINDOW_HEIGHT);
		frame.setVisible(true);
	}

	/*
	 * Checks if the top left corner coordinates of a window are equal to the
	 * passed coordinates
	 * 
	 * @param window the window to check
	 * 
	 * @param coordinates the coordinates for comparing
	 * 
	 * @return true if coordinates match; false otherwise
	 */
	private boolean checkTopLeftCorner(WindowInformation window,
			Point coordinates) {
		return (window.getTopLeftCorner().x == coordinates.x)
				&& (window.getTopLeftCorner().y == coordinates.y);
	}

	/*
	 * Checks if a title of a window matches the specified string
	 * 
	 * @param window the window to check
	 * 
	 * @param windowTitle the string for comparing
	 * 
	 * @return true if title matches the string; false otherwise
	 */
	private boolean checkWindowTitle(WindowInformation window,
			String windowTitle) {
		return window.getWindowTitle().matches(windowTitle);
	}

}
