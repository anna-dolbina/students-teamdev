package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jniwrapper.Callback;
import com.jniwrapper.Int;
import com.jniwrapper.LongInt;
import com.jniwrapper.Parameter;
import com.jniwrapper.UInt32;

/**
 * Represents a WINEVENTPROC, an event hook function.
 * 
 * @author anna.dolbina
 * 
 */

public final class HookCallback extends Callback {
	private Int hookHandle = new Int();
	private UInt32 eventCode = new UInt32();

	private Int sourceWindowHandle = new Int();
	private LongInt objectId = new LongInt();
	
	private LongInt childId = new LongInt();
	private UInt32 eventThreadId = new UInt32();
	private UInt32 eventTime = new UInt32();
	private final static Logger logger;
	private final AbstractHook hook;
	
	static{
		logger=LoggerFactory.getLogger(HookCallback.class);
	}
	/**
	 * Constructs and initializes a callback instance.
	 * 
	 * @param hook
	 *            The hook to fire caught events to.
	 */
	public HookCallback(AbstractHook hook) {
		this.hook = hook;
		init(new Parameter[] { hookHandle, eventCode, sourceWindowHandle,
				objectId, childId, eventThreadId, eventTime }, null);
	}

	/**
	 * Constructs and fires a HookEventObject.
	 */
	@Override
	public void callback() {
		HookEventObject event = new HookEventObject(this, hookHandle,
				eventCode, sourceWindowHandle, objectId, childId,
				eventThreadId, eventTime);
		hook.fireHookEvent(event);
	}
}
