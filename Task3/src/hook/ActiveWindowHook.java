package hook;

public class ActiveWindowHook extends AbstractHook {
    private static final int NO_FLAGS = 0;
	/**
     * The foreground window has changed. The system sends this event even if
     * the foreground window has changed to another window in the same thread.
     * Server applications never send this event.
     */
    public static final long EVENT_SYSTEM_FOREGROUND = 0x0003;
    private int i;

    public ActiveWindowHook() {
        super(ActiveWindowHook.EVENT_SYSTEM_FOREGROUND,
                ActiveWindowHook.EVENT_SYSTEM_FOREGROUND,
                Hook.OBSERVE_ALL_PROCESSES, Hook.OBSERVE_ALL_THREADS,
                NO_FLAGS);
    }

}
