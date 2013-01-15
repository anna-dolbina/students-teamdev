package hook;

import java.util.Date;
import java.util.EventObject;

import com.jniwrapper.Int;
import com.jniwrapper.LongInt;
import com.jniwrapper.UInt32;

/**
 * An event object which describes the event caught with the hook.
 * 
 * @author anna.dolbina
 * 
 */
public class HookEventObject extends EventObject {

	public static final long CHILDID_SELF = 0;
	private static final long serialVersionUID = 1L;

	private final Int hookHandle;
	private final UInt32 eventCode;
	private final Int sourceWindowHandle;
	private final LongInt objectId;
	private final LongInt childId;
	private final UInt32 sourceThreadId;
	private final UInt32 eventTime;

	/**
	 * Constructs and initializes the instance of this class.
	 * 
	 * @param source
	 *            Source object which generates this event object.
	 * @param hookHandle
	 *            Hook handle
	 * @param eventCode
	 *            Event code
	 * @param windowHandle
	 *            Source window handle
	 * @param objectId
	 *            Object id
	 * @param childId
	 *            Child id
	 * @param sourceThreadHandle
	 *            Event thread identifier
	 * @param eventTime
	 *            Time when event happened
	 */
	public HookEventObject(Object source, Int hookHandle, UInt32 eventCode,
			Int windowHandle, LongInt objectId, LongInt childId,
			UInt32 sourceThreadHandle, UInt32 eventTime) {
		super(source);
		this.hookHandle = hookHandle;
		this.sourceWindowHandle = windowHandle;
		this.eventCode = eventCode;
		this.objectId = objectId;
		this.childId = childId;
		this.sourceThreadId = sourceThreadHandle;
		this.eventTime = eventTime;

	}

	/**
	 * Returns the handle to an event hook.
	 * 
	 * @return the hook handle
	 */
	public long getHookHandle() {
		return hookHandle.getValue();
	}

	/**
	 * Returns the event code.
	 * 
	 * @return The constant specifying the event that had occurred.
	 */
	public long getEventCode() {
		return eventCode.getValue();
	}

	/**
	 * Returns the source window handle.
	 * 
	 * @return Handle to the window that generates the event, or NULL if no
	 *         window is associated with the event.
	 */
	public long getSourceWindowHandle() {
		return sourceWindowHandle.getValue();
	}

	/**
	 * Returns an object identifier
	 * 
	 * @return Identifier of the object associated with the event. This is one
	 *         of the object identifiers or a custom object ID.
	 */
	public long getObjectId() {
		return objectId.getValue();
	}

	/**
	 * Returns a child identifier.
	 * 
	 * @return Value which identifies whether the event was triggered by an
	 *         object or a child element of the object. If this value is
	 *         CHILDID_SELF, the event was triggered by the object; otherwise,
	 *         this value is the child ID of the element that triggered the
	 *         event.
	 */
	public long getChildId() {
		return childId.getValue();
	}

	/**
	 * Returns an event thread identifier.
	 * 
	 * @return Value which identifies the thread that generated the event, or
	 *         the thread that owns the current window.
	 */
	public long getSourceThreadId() {
		return sourceThreadId.getValue();
	}

	/**
	 * Returns the event time.
	 * 
	 * @return Value which specifies the time, in milliseconds, when the event
	 *         was generated.
	 */
	public long getEventTime() {
		return eventTime.getValue();
	}

	/**
	 * Returns the event time as a date
	 * 
	 * @return The time when event was generated as a Date object
	 */
	//TODO: implement date correctly
	public Date getEventDate() {
		Date date = new Date(getEventTime());
		return date;
	}
}
