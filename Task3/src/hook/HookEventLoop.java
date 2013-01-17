package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jniwrapper.Bool;
import com.jniwrapper.Function;
import com.jniwrapper.Int;
import com.jniwrapper.Library;
import com.jniwrapper.Parameter;
import com.jniwrapper.Pointer;
import com.jniwrapper.UInt;
import com.jniwrapper.UInt32;

/**
 * A thread which installs and removes the hook.
 * 
 * @author anna.dolbina
 * 
 */
public final class HookEventLoop extends Thread {
	private static final int ERROR_SUCCESS = 0;
	private static Logger logger;
	private static final int NO_CALLBACK_MODULE_NEEDED = ERROR_SUCCESS;
	private static final long EMPTY_MESSAGE_QUEUE = 0;
	private static final long MESSAGE_TYPE = 0x0012;
	private static final long PARAMETER_NOT_USED = 0;
	private static final long MESSAGE_QUEUE_ERROR = -1;
	protected static Library user32, kernel32;
	private Int hookHandle = new Int(ERROR_SUCCESS);
	private final Parameter[] hookParameters;
	private final HookCallback hookCallback;
	private final Function setWinEventHook, unhookWinEvent, getMessage,
			postThreadMessage, getCurrentThreadId;
	private volatile long currentThreadId;

	static {
		user32 = new Library("user32");
		kernel32 = new Library("kernel32");
		logger = LoggerFactory.getLogger(HookEventLoop.class);
	}

	/**
	 * Constructs an instance of a HookLoop.
	 * 
	 * @param minEventId
	 *            lowest event value in the range of events that are handled by
	 *            the hook function
	 * @param maxEventId
	 *            highest event value in the range of events that are handled by
	 *            the hook function
	 * @param hookCallback
	 *            the callback function which will be called if an event
	 *            received in the hook
	 * @param observedProcessId
	 *            the ID of the process from which the hook function receives
	 *            events. Specify zero (0) to receive events from all processes
	 *            on the current desktop
	 * @param observedThreadId
	 *            the ID of the thread from which the hook function receives
	 *            events. If this parameter is zero, the hook function is
	 *            associated with all existing threads on the current desktop.
	 * @param flags
	 *            Flag values that specify the events to be skipped.
	 * 
	 */
	public HookEventLoop(long minEventId, long maxEventId,
			HookCallback hookCallback, long observedProcessId,
			long observedThreadId, long flags) {
		hookParameters = new Parameter[] { new UInt(minEventId),
				new UInt(maxEventId), new Int(NO_CALLBACK_MODULE_NEEDED),
				hookCallback, new Int(observedProcessId),
				new Int(observedThreadId), new Int(flags) };
		this.hookCallback = hookCallback;
		synchronized (user32) {
			setWinEventHook = user32.getFunction("SetWinEventHook");
			unhookWinEvent = user32.getFunction("UnhookWinEvent");
			getMessage = user32.getFunction("GetMessageW");
			postThreadMessage = user32.getFunction("PostThreadMessageW");
			getCurrentThreadId = kernel32.getFunction("GetCurrentThreadId");
		}
	}

	/**
	 * Creates and processes a hook loop.
	 */
	public void run() {
		long errorCode = ERROR_SUCCESS;
		try {
			currentThreadId = getCurrentThreadId();
			installHook();
			logger.info("hook:" + hookHandle);
			synchronized(hookCallback){
				hookCallback.notifyAll();
			}
			Msg msg = new Msg();
			Int messageQueueStatus = new Int(0);
			Parameter[] params = { new Pointer(msg), new Pointer.Void(),
					new Int(0), new Int(0) };
			errorCode = getMessage.invoke(messageQueueStatus, params);
			if (errorCode != ERROR_SUCCESS) {
				removeHook();
				throw new LastErrorException(errorCode,
						"An error occurred when trying to get a message from thread message queue");
			}
			while (isThreadAlive() && isMessageQueueEmpty(messageQueueStatus)) {
				errorCode = getMessage.invoke(messageQueueStatus, params);
				if (errorCode != ERROR_SUCCESS) {
					removeHook();
					throw new LastErrorException(errorCode,
							"An error occurred when trying to get a message from thread message queue");
				}

			}
			removeHook();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Checks if the message queue of the current thread is empty
	 */
	private boolean isMessageQueueEmpty(Int messageQueueStatus) {
		return ((messageQueueStatus.getValue() == EMPTY_MESSAGE_QUEUE) || (messageQueueStatus
				.getValue() == MESSAGE_QUEUE_ERROR));
	}

	/*
	 * Checks if the thread is alive
	 */
	private boolean isThreadAlive() {
		return !isInterrupted();
	}

	/*
	 * Installs the hook with specified parameters
	 */
	private void installHook() {

		long errorCode = setWinEventHook.invoke(hookHandle, hookParameters);
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"An error occured when trying to install hook");
		}

	}

	/*
	 * Removes the installed hook correctly. Must be called in the thread where
	 * hook was installed
	 */
	private void removeHook() {
		logger.info("Removing hook " + hookHandle + "...");
		Bool status = new Bool();
		long errorCode = unhookWinEvent.invoke(status, hookHandle);
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"An error occured when trying to uninstall hook");
		}
		logger.info("Hook " + hookHandle + " uninstalled: " + status.getValue());
		hookCallback.dispose();
	}

	/*
	 * Performs the GetCurrentThreadId call
	 */
	private long getCurrentThreadId() {
		UInt32 currentThreadId = new UInt32(0);
		long errorCode = getCurrentThreadId.invoke(currentThreadId);
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"An error occured when trying to retrieve a current thread id");
		}
		logger.info("Current thread id: " + currentThreadId.getValue());
		return currentThreadId.getValue();
	}

	/*
	 * Performs sending message to a thread with specified id (PostThreadMessge
	 * call)
	 */
	private void postThreadMessage(long threadId) {
		long errorCode = postThreadMessage.invoke(null, new UInt32(threadId),
				new UInt(MESSAGE_TYPE), new Int(PARAMETER_NOT_USED), new Int(
						PARAMETER_NOT_USED));
		if (errorCode != ERROR_SUCCESS) {
			throw new LastErrorException(errorCode,
					"An error occured when trying to send a message to a hook thread");
		}
		logger.info("A message was sent to a thread " + threadId);
	}

	@Override
	public void interrupt() {
		super.interrupt();
		postThreadMessage(currentThreadId);
	}
}