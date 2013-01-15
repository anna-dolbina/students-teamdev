package tests;

import static org.junit.Assert.fail;
import hook.ActiveWindowHook;
import hook.Hook;

import org.junit.Test;

public class HookInstallAndRemoveTest {
	/**
	 * Tests installing a hook that was already installed.
	 */
	@Test
	public void doubleHookInstalling() {
		Hook hook = new ActiveWindowHook();

		hook.install();
		try {
			hook.install();
			fail("Exception wasn't thrown when trying to install hook twice from the same thread");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());

		} finally {
			hook.uninstall();
		}
	}
	
	/**
	 * Tests uninstalling a hook that was already uninstalled.
	 */
	@Test
	public void doubleHookUninstalling() {
		Hook hook = new ActiveWindowHook();

		hook.install();
		hook.uninstall();
		try {
			hook.uninstall();
			fail("Exception wasn't thrown when trying to uninstall hook twice from the same thread");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());

		} 
	}

}
