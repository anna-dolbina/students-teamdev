package tests;

import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;
import hook.MinimizeAndRestoreWindowHook;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
//TODO: automatic tests
public class ConcreteHooksTest {
	/**
	 * Tests a hook catching minimizing and restoring events.
	 */
	@Test
	public void minimizeAndRestoreWindowHook() {
		Hook hook = new MinimizeAndRestoreWindowHook();
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				System.out.println("Listener1: Window minimized or restored:"
						+ e.getSourceWindowHandle());
				throw new Exception("Ouch!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				Calendar date = new GregorianCalendar();
				date.setTime(e.getEventDate());
				/*date.setTimeInMillis(System.currentTimeMillis()
						- e.getEventTime());*/
				System.out.println("Listener2: Window minimized or restored: "
						+ e.getSourceWindowHandle() + " Event time: "
						+ date.get(Calendar.HOUR_OF_DAY) + ":"
						+ date.get(Calendar.MINUTE) + ":"
						+ date.get(Calendar.MILLISECOND));
			}

		});
		try {
			hook.install();
			System.out.println("Press 'Enter' to terminate.");
			System.in.read();
			System.in.skip(4);
			hook.uninstall();
		}

		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	/**
	 * Tests a hook catching active window changing events.
	 */
	@Test
	public void activeWindowHook() {
		Hook hook = new ActiveWindowHook();
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) throws Exception {
				System.out.println("Listener1:Active window changed to "
						+ e.getSourceWindowHandle());
				throw new Exception("Ouch!");
			}

		});
		hook.addListener(new HookListener() {

			@Override
			public void onHookEvent(HookEventObject e) {
				Calendar date = new GregorianCalendar();
				date.setTime(e.getEventDate());
				
				/*date.setTimeInMillis(System.currentTimeMillis()
						- e.getEventTime());*/
				System.out.println("Listener2: Active window changed to "
						+ e.getSourceWindowHandle() + " Event time: "
						+ date.get(Calendar.HOUR_OF_DAY) + ":"
						+ date.get(Calendar.MINUTE) + ":"
						+ date.get(Calendar.MILLISECOND));
			}

		});
		try {
			hook.install();
			System.out.println("Press 'Enter' to terminate.");
			System.in.read();
			hook.uninstall();
		}

		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
