package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import windows.TopLevelWindowsInformation;
import windows.WindowInformation;
import windows.WindowsInformation;

public class TopLevelWindowsInformationTest {
	private static final int TOP_LEFT_CORNER_Y = 10;
	private static final int TOP_LEFT_CORNER_X = 10;
	private static final String WINDOW_TITLE = "TestWindow";

	/**
	 * Finds a previously created visible window on the screen using
	 * TopLevelWindowsInformation.
	 */
	@Test
	public void findingVisibleWindow() {
		boolean status = false;
		try {
			JFrame frame = new JFrame(WINDOW_TITLE);
			frame.setBounds(TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y, 320, 240);
			frame.setVisible(true);
			WindowsInformation informationList = new TopLevelWindowsInformation();
			WindowInformation window;
			List<WindowInformation> visibleWindows = new ArrayList<WindowInformation>();

			for (int i = 0; i < informationList.size(); i++) {
				window = informationList.get(i);
				if (window.isVisible()) {
					visibleWindows.add(window);
				}
			}
			Iterator<WindowInformation> it = visibleWindows.iterator();
			while (it.hasNext()) {
				window = it.next();
				if (checkWindowTitle(window) && checkTopLeftCorner(window)) {
					status = true;
				}
			}
		} catch (RuntimeException e) {
			fail("Exception was thrown: " + e.getMessage());
		}
		if (status == false)
			fail("A window created from test was not found on the screen");
	}

	/**
	 * @param window
	 * @return
	 */
	private boolean checkTopLeftCorner(WindowInformation window) {
		return (window.getTopLeftCorner().x == TOP_LEFT_CORNER_X)
				&& (window.getTopLeftCorner().y == TOP_LEFT_CORNER_Y);
	}

	/**
	 * @param window
	 * @return
	 */
	private boolean checkWindowTitle(WindowInformation window) {
		return window.getWindowTitle().matches(WINDOW_TITLE);
	}

}
