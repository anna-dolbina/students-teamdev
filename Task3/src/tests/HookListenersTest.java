package tests;

import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;

import java.awt.Frame;

import javax.swing.JFrame;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HookListenersTest {
	private final static Logger logger;
	private static final int WINDOW_HEIGHT = 240;
	private static final int WINDOW_WIDTH = 320;
	private static final String WINDOW_TITLE = "TestWindow";
	private static final int TOP_LEFT_CORNER_X = 10;
	private static final int TOP_LEFT_CORNER_Y = 10;
	static{
		logger = LoggerFactory.getLogger(HookListenersTest.class);
	}

	/**
	 * Tests adding a listener that was already added to the hook.
	 */
	@Test
	public void sameListenerAdding() {
		Hook hook = new ActiveWindowHook();
		HookListener hookListener = new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				System.out.println("Listener1:Active window changed to "
						+ e.getSourceWindowHandle());
				throw new Exception("Ouch!");
			}

		};
		hook.addListener(hookListener);
		try {
			hook.addListener(hookListener);
			fail("The same listener was added to the hook twice");
		} catch (Exception e) {
			logger.info("An exception occurred when adding the same listener twice: "+e.getMessage());
		}
	}

	/**
	 * Tests removing a listener that wasn't added to the hook
	 */
	@Test
	public void removeListenerWithoutAdding() {
		Hook hook = new ActiveWindowHook();
		HookListener hookListener = new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				System.out.println("Listener1:Active window changed to "
						+ e.getSourceWindowHandle());
				throw new Exception("Ouch!");
			}

		};

		try {
			hook.removeListener(hookListener);
			fail("The listener was removed from hook without being added to it");
		} catch (Exception e) {
			logger.info("An exception occurred when trying to remove a listener without adding it to hook: "+e.getMessage());
		}
	}

	/**
	 * Tests adding listeners to the same hook form two different threads in
	 * random moments of time
	 */
	@Test
	public void addingListenersFromDifferentThreads() {
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setBounds(TOP_LEFT_CORNER_X, TOP_LEFT_CORNER_Y, WINDOW_WIDTH,
				WINDOW_HEIGHT);
		frame.setVisible(true);

		final Hook hook = new ActiveWindowHook();

		Thread t1 = new HookListenerAddThread(hook);
		Thread t2 = new HookListenerAddThread(hook);
		hook.install();

		t1.start();
		t2.start();
		try {
			for (int i = 0; i < 10; i++) {
				frame.setState(Frame.ICONIFIED);
				Thread.sleep(3000);
				frame.setState(Frame.NORMAL);
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			t1.interrupt();
			t2.interrupt();
			hook.uninstall();
		}

	}
}
