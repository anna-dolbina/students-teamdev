package hook;

import com.jniwrapper.Int;
import com.jniwrapper.LongInt;
import com.jniwrapper.Parameter;
import com.jniwrapper.Structure;
import com.jniwrapper.UInt;
import com.jniwrapper.UInt32;

/**
 * Class represents a MSG structure described in Winuser.h. Contains message
 * information from a message queue.
 * 
 * @author anna.dolbina
 * 
 */
public class Msg extends Structure {
	private final Int windowHandle = new Int();
	private final UInt messageId = new UInt();
	private final LongInt wParameter = new LongInt();
	private final LongInt lParameter = new LongInt();
	private final UInt32 time = new UInt32(0);
	private final Point point = new Point();

	public Msg() {
		init(new Parameter[] { windowHandle, messageId, wParameter, lParameter,
				time, point }, (short) 8);
	}

	/**
	 * Returns a handle to the window whose window procedure receives the
	 * message.
	 * 
	 * @return a window handle
	 */
	public long getWindowHandle() {
		return windowHandle.getValue();
	}

	/**
	 * Sets a handle to the window whose window procedure receives the message.
	 * 
	 * @param windowHandle
	 *            a window handle
	 */
	public void setWindowHandle(long windowHandle) {
		this.windowHandle.setValue(windowHandle);
	}

	/**
	 * Returns the additional information about the message. The exact meaning
	 * depends on the value of the message member.
	 * 
	 * @return the value of WPARAM for this message.
	 */
	public long getWParameter() {
		return wParameter.getValue();
	}

	/**
	 * Sets the additional information about the message. The exact meaning
	 * depends on the value of the message member.
	 * 
	 * @param wParameter
	 *            the meaning of WPARAM for this message.
	 */
	public void setWParameter(long wParameter) {
		this.wParameter.setValue(wParameter);
	}

	/**
	 * Returns the additional information about the message. The exact meaning
	 * depends on the value of the message member.
	 * 
	 * @return the meaning of LPARAM for this message.
	 */
	public long getLParameter() {
		return lParameter.getValue();
	}

	/**
	 * Sets the additional information about the message. The exact meaning
	 * depends on the value of the message member.
	 * 
	 * @param lParameter
	 *            the meaning of LPARAM for this message.
	 */
	public void setLParameter(long lParameter) {
		this.lParameter.setValue(lParameter);
	}

	/**
	 * Returns the message identifier. Applications can only use the low word;
	 * the high word is reserved by the system.
	 * 
	 * @return The message identifier
	 */
	public long getMessage() {
		return messageId.getValue();
	}

	/**
	 * Sets the message identifier. Applications can only use the low word; the
	 * high word is reserved by the system.
	 * 
	 * @param message
	 *            The value of the message identifier to set
	 * 
	 */
	public void setMessage(long message) {
		this.messageId.setValue(message);
	}

	/**
	 * Returns the time at which the message was posted.
	 * 
	 * @return time in milliseconds.
	 */
	public long getTime() {
		return time.getValue();
	}

	/**
	 * Sets the time at which the message was posted
	 * 
	 * @param time
	 *            The time at which the message was posted.
	 */
	public void setTime(long time) {
		this.time.setValue(time);
	}

	/**
	 * Returns the cursor position, in screen coordinates, when the message was
	 * posted.
	 * 
	 * @return the point representing cursor position
	 */
	public java.awt.Point getPoint() {
		return new java.awt.Point( (int) point.getX(), (int) point.getY());
	}

	/**
	 * Sets the cursor position, in screen coordinates, when the message was
	 * posted.
	 * 
	 * @param point
	 *            The point representing the cursor position
	 */
	public void setPoint(Point p) {
		this.point.setX(p.getX());
		this.point.setY(p.getY());
	}

	/**
	 * Sets the cursor position, in screen coordinates, when the message was
	 * posted.
	 * 
	 * @param x
	 *            The x coordinate of a point representing the cursor position
	 * @param y
	 *            The y coordinate of a point representing the cursor position
	 */
	public void setPoint(long x, long y) {
		this.point.setX(x);
		this.point.setY(y);
	}

}
