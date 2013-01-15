package hook;

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
	/*
	 * Object id. Identifier of the object associated with the event. This is
	 * one of the object identifiers or a custom object ID.
	 */
	private LongInt objectId = new LongInt();
	/*
	 * Child id. Value which identifies whether the event was triggered by an
	 * object or a child element of the object.
	 */
	private LongInt childId = new LongInt();
	private UInt32 eventThreadId = new UInt32();
	private UInt32 eventTime = new UInt32();
	private final AbstractHook hook;

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
