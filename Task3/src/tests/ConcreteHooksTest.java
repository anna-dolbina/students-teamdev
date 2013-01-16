package tests;

import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;
import hook.MinimizeAndRestoreWindowHook;

import java.awt.Frame;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConcreteHooksTest {
	private static final int TOP_LEFT_CORNER_X = 10;
	private static final String WINDOW_TITLE = "TestWindow";
	private static final int TOP_LEFT_CORNER_Y = 10;
	private static final int WINDOW_WIDTH = 320;
	private static final int WINDOW_HEIGHT = 240;
	private final static Logger logger;
	static {
		logger = LoggerFactory.getLogger(ConcreteHooksTest.class);
	}

	/**
	 * Tests a hook catching minimizing and restoring events.
	 */
	@Test
	public void minimizeAndRestoreWindowHook() {
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setBounds(TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y, WINDOW_WIDTH,
				WINDOW_HEIGHT);
		frame.setVisible(true);
		
		Hook hook = new MinimizeAndRestoreWindowHook();
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				logger.info("Listener1: Window minimized or restored:"
						+ e.getSourceWindowHandle());
				throw new Exception("Listener1: Ouch!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				Calendar date = new GregorianCalendar();
				date.setTime(e.getEventDate());
				logger.info("Listener2: Window minimized or restored: "
						+ e.getSourceWindowHandle() + " Event time: "
						+ date.get(Calendar.HOUR_OF_DAY) + ":"
						+ date.get(Calendar.MINUTE) + ":"
						+ date.get(Calendar.MILLISECOND));
			}

		});
		try {
			hook.install();
			for (int i = 0; i < 10; i++) {
				frame.setState(Frame.ICONIFIED);
				Thread.sleep(3000);
				frame.setState(Frame.NORMAL);
				Thread.sleep(3000);
			}
			hook.uninstall();
		}

		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests a hook catching active window changing events.
	 */
	@Test
	public void activeWindowHook() {
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setBounds(TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y, WINDOW_WIDTH,
				WINDOW_HEIGHT);
		frame.setVisible(true);

		Hook hook = new ActiveWindowHook();
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				logger.info("Listener1:Active window changed to "
						+ e.getSourceWindowHandle());
				throw new Exception("Listener1: Ouch!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				Calendar date = new GregorianCalendar();
				date.setTimeInMillis(System.currentTimeMillis()-e.getEventTime());
				logger.info("Listener2: Active window changed to "
						+ e.getSourceWindowHandle() + " Event time: "
						+ date.get(Calendar.HOUR_OF_DAY) + ":"
						+ date.get(Calendar.MINUTE) + ":"
						+ date.get(Calendar.MILLISECOND)+
						"Time in ms:"+e.getEventTime());
			}

		});
		try {
			hook.install();
			for (int i = 0; i < 10; i++) {
				frame.setState(Frame.ICONIFIED);
				Thread.sleep(3000);
				frame.setState(Frame.NORMAL);
				Thread.sleep(3000);
			}

		} catch (InterruptedException e) {
			logger.error("The main thread was suddenly interrupted");
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			hook.uninstall();
		}
	}

}
