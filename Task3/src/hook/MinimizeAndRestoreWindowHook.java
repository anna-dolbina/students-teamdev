package hook;

public class MinimizeAndRestoreWindowHook extends AbstractHook {

	private static final int NO_FLAGS = 0;

	/**
	 * A window object is about to be restored. This event is sent by the
	 * system, never by servers.
	 */
	public static final long EVENT_SYSTEM_MINIMIZEEND = 0x0017;

	/**
	 * A window object is about to be minimized. This event is sent by the
	 * system, never by servers.
	 */
	public static final long EVENT_SYSTEM_MINIMIZESTART = 0x0016;
	/**
	 * Constructs and initializes the instance of MinimizeAndRestoreWindowHook
	 */
	public MinimizeAndRestoreWindowHook() {
		super(EVENT_SYSTEM_MINIMIZESTART, EVENT_SYSTEM_MINIMIZEEND,
				Hook.OBSERVE_ALL_PROCESSES, Hook.OBSERVE_ALL_THREADS,
				NO_FLAGS);
	}
}
