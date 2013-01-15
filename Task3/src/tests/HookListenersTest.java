package tests;

import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;

import org.junit.Test;

public class HookListenersTest {

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
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Tests adding listeners to the same hook form two different threads in random moments of time
	 */
	@Test
	public void addingListenersFromDifferentThreads() {
		final Hook hook = new ActiveWindowHook();
		
		Thread t1 = new HookListenerAddThread(hook);
		Thread t2 = new HookListenerAddThread(hook);
		hook.install();
		
		t1.start();
		t2.start();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			t1.interrupt();
			t2.interrupt();
			hook.uninstall();
			return;
		}
		t1.interrupt();
		t2.interrupt();
		hook.uninstall();
	}
}
