package hook;

import com.jniwrapper.LongInt;
import com.jniwrapper.Parameter;
import com.jniwrapper.Structure;

public class Point extends Structure {
	private final LongInt x = new LongInt();
	private final LongInt y = new LongInt();

	public Point() {
		init(new Parameter[] { x, y },(short) 8);
	}

	/**
	 * Returns the x-coordinate of a point.
	 * 
	 * @return The x-coordinate of the point.
	 */
	public long getX() {
		return x.getValue();
	}

	/**
	 * Sets the x-coordinate of a point.
	 * 
	 * @param x The x-coordinate of the point.
	 */
	public void setX(long x) {
		this.x.setValue(x);
	}

	/**
	 * Returns the y-coordinate of a point.
	 * 
	 * @return The y-coordinate of the point.
	 */
	public long getY() {
		return y.getValue();
	}

	/**
	 * Sets the y-coordinate of a point.
	 * 
	 * @param y The y-coordinate of the point.
	 */
	public void setY(long y) {
		this.y.setValue(y);
	}
}

