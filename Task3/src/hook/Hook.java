package hook;

/**
 * Interface of a hook.
 * 
 * @author anna.dolbina
 * 
 */
public interface Hook {
	public static final int OBSERVE_ALL_THREADS = 0x0000;
	public static final int OBSERVE_ALL_PROCESSES = 0x0000;
	public static final long WINEVENT_SKIPOWNPROCESS = 0x0002;
	public static final int EVENT_MIN = 0x00000001;
	public static final int EVENT_MAX = 0x7fffffff;

	/**
	 * Installs the hook.
	 */
	void install();

	/**
	 * Uninstalls the hook.
	 */
	void uninstall();

	/**
	 * Adds the specified listener to the hook.
	 * 
	 * @param hookListener
	 *            the listener to add.
	 */
	void addListener(HookListener hookListener);

	/**
	 * Removes the specified listener from the hook.
	 * 
	 * @param hookListener
	 *            the listener to remove.
	 */
	void removeListener(HookListener hookListener);

	/**
	 * Checks if the hook is installed.
	 * 
	 * @return true if the hook is installed; false otherwise
	 */
	boolean isInstalled();
}
