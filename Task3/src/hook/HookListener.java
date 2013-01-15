package hook;

import java.util.EventListener;

/**
 * Interface describes functionality for a hook listener.
 * 
 * @author anna.dolbina
 * 
 */
public interface HookListener extends EventListener {
	/**
	 * This method performs handling of the received event object.
	 * 
	 * @param event
	 *            The event object to handle
	 * @throws Exception
	 *             any exception which was thrown when handling of the received
	 *             event object.
	 */
	public void onHookEvent(HookEventObject event) throws Exception;
}
