package tests;

import hook.Hook;
import hook.HookEventObject;
import hook.HookListener;
/**
 * A thread which adds listeners to the specified hook in random moments of time
 * 
 * @author anna.dolbina
 *
 */
public class HookListenerAddThread extends Thread {
	private final Hook hook;
	/**
	 * Constructs and initializes the instance of this class
	 * 
	 * @param h the hook to add listeners to
	 */
	public HookListenerAddThread(Hook h){
		hook=h;
	}

	public void run() {
		while (!isInterrupted()) {
			hook.addListener(new HookListener() {

				@Override
				public void onHookEvent(HookEventObject event)
						throws Exception {
					System.out
							.println("Thread listener:Active window changed to "
									+ event.getSourceWindowHandle());

				}

			});
			try {
				Thread.sleep((long) (Math.random() * 2000));
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
