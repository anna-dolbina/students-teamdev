package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;
import hook.MinimizeAndRestoreWindowHook;

import java.awt.Frame;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcreteHooksTest {
	private static final int TIME_INTERVAL = 500;
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
		final AtomicInteger counter = new AtomicInteger(0);

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
				counter.incrementAndGet();
				throw new Exception("Listener1: Exception!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				logger.info("Listener2: Window minimized or restored: "
						+ e.getSourceWindowHandle() + " Event time: "
						+ e.getEventTime());
				counter.incrementAndGet();
			}

		});
		try {
			hook.install();
			for (int i = 0; i < 10; i++) {
				frame.setState(Frame.ICONIFIED);
				Thread.sleep(TIME_INTERVAL);
				frame.setState(Frame.NORMAL);
				Thread.sleep(TIME_INTERVAL);
			}
			hook.uninstall();
			frame.dispose();
			assertEquals("Not all events were caught",40,counter.get());
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
		final AtomicInteger counter = new AtomicInteger(0);
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
				counter.incrementAndGet();
				throw new Exception("Listener1: Exception!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				logger.info("Listener2: Active window changed to "
						+ e.getSourceWindowHandle() + " Event time: "
						+ e.getEventTime());
				counter.incrementAndGet();
			}

		});
		try {
			hook.install();
			for (int i = 0; i < 10; i++) {
				frame.setState(Frame.ICONIFIED);
				Thread.sleep(TIME_INTERVAL);
				frame.setState(Frame.NORMAL);
				Thread.sleep(TIME_INTERVAL);
			}

		} catch (InterruptedException e) {
			logger.error("The main thread was suddenly interrupted");
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			hook.uninstall();
			frame.dispose();
			assertEquals("Not all events were caught",40,counter.get());
		}
	}

}
