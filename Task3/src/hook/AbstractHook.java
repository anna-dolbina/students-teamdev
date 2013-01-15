package hook;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract hook class. Concrete hooks can be created by extending this class
 * and defining hook parameters.
 * 
 * @author anna.dolbina
 * 
 */
public abstract class AbstractHook implements Hook {
	protected static final int WINEVENT_OUTOFCONTEXT = 0x0000;
	private static Logger logger;
	private Set<HookListener> listeners = new HashSet<HookListener>();
	private HookEventLoop eventLoop = null;

	private final long minEventId, maxEventId, observedProcessId,
			observedThreadId, hookFlags;
	static{
		logger = LoggerFactory.getLogger(AbstractHook.class);
	}

	/**
	 * Initializes a hook with given parameters.
	 * 
	 * @param minEventId
	 *            The event constant for the lowest event value in the range of
	 *            events that are handled by the hook function. This parameter
	 *            can be set to EVENT_MIN to indicate the lowest possible event
	 *            value.
	 * @param maxEventId
	 *            The event constant for the highest event value in the range of
	 *            events that are handled by the hook function. This parameter
	 *            can be set to EVENT_MAX to indicate the highest possible event
	 *            value.
	 * @param observedProcessId
	 *            The id of the process that must be observed. Can be set to
	 *            OBSERVE_ALL_PROCESSES to receive events from all processes.
	 * @param observedThreadId
	 *            The id of the thread that must be observed. Can be set to
	 *            OBSERVE_ALL_THREADS to receive events from all threads.
	 * @param hookFlags
	 *            Flags for the hook. Flags can contain the
	 *            WINEVENT_SKIPOWNPROCESS value to skip events from own process.
	 */
	public AbstractHook(long minEventId, long maxEventId,
			long observedProcessId, long observedThreadId, long hookFlags) {
		this.minEventId = minEventId;
		this.maxEventId = maxEventId;
		this.observedProcessId = observedProcessId;
		this.observedThreadId = observedThreadId;
		this.hookFlags = hookFlags | WINEVENT_OUTOFCONTEXT;
	}

	@Override
	public void install() {
		if ((eventLoop != null) && (eventLoop.isAlive()))
			throw new RuntimeException("The hook is already installed");

		eventLoop = new HookEventLoop(minEventId, maxEventId, new HookCallback(
				this), observedProcessId, observedThreadId, hookFlags);

		eventLoop.start();
	}

	@Override
	public void uninstall() {
		if ((eventLoop == null) || (!eventLoop.isAlive()))
			throw new RuntimeException("The hook is not installed");
		eventLoop.interrupt();
		try {
			eventLoop.join();
		} catch (InterruptedException e) {
			logger.error("An exception occurred when trying to join hook loop:"+e.getMessage());
		}

	}

	@Override
	public synchronized void addListener(HookListener hookListener) {
		if (listeners.contains(hookListener))
			throw new IllegalArgumentException(
					"The hook listener is already added");
		listeners.add(hookListener);

	}

	@Override
	public boolean isInstalled() {
		return eventLoop.isAlive();
	}

	@Override
	public synchronized void removeListener(HookListener hookListener) {
		if (!listeners.contains(hookListener))
			throw new IllegalArgumentException(
					"Cannot remove the hook listener: it wasn't added to the hook");
		listeners.remove(hookListener);

	}

	/**
	 * Fires the specified event object to all known listeners.
	 * 
	 * @param e
	 *            An event object which will be passed to the listeners.
	 */
	protected void fireHookEvent(HookEventObject e) {
		if (listeners.isEmpty()) {
			return;
		}
		Set<HookListener> targets;
		synchronized (listeners) {
			targets = new HashSet<HookListener>(listeners);
		}
		Iterator<HookListener> i = targets.iterator();
		while (i.hasNext()) {
			HookListener listener = i.next();
			try {
				listener.onHookEvent(e);
			} catch (Exception ex) {
				logger.error("An exception occured in hook listener: "+ex.getMessage());
			}
		}

	}

}
