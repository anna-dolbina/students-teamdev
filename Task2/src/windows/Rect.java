package windows;

import com.jniwrapper.LongInt;
import com.jniwrapper.Parameter;
import com.jniwrapper.Structure;

/**
 * Class represents a RECT structure.
 * 
 * @author anna.dolbina
 * 
 */
public class Rect extends Structure {
	private final LongInt left = new LongInt();
	private final LongInt top = new LongInt();
	private final LongInt right = new LongInt();
	private final LongInt bottom = new LongInt();

	/**
	 * Constructs and initializes the instance of Rect class.
	 */
	public Rect() {
		init(new Parameter[] { left, top, right, bottom },(short) 8);
	}

	/**
	 * Sets the left x coordinate.
	 * 
	 * @param left
	 *            the left x coordinate to set.
	 */
	public void setLeft(long left) {
		this.left.setValue(left);
	}

	/**
	 * Returns the left x coordinate.
	 * 
	 * @return the left x coordinate.
	 */
	public long getLeft() {
		return left.getValue();
	}

	/**
	 * Sets the top y coordinate.
	 * 
	 * @param top
	 *            the top y coordinate to set.
	 */
	public void setTop(long top) {
		this.top.setValue(top);
	}

	/**
	 * Returns the top y coordinate.
	 * 
	 * @return the top y coordinate.
	 */
	public long getTop() {
		return top.getValue();
	}

	/**
	 * Sets the right x coordinate.
	 * 
	 * @param right
	 *            the right x coordinate to set.
	 */
	public void setRight(long right) {
		this.right.setValue(right);
	}

	/**
	 * Returns the right x coordinate.
	 * 
	 * @return the right x coordinate.
	 */
	public long getRight() {
		return right.getValue();
	}

	/**
	 * Sets the bottom y coordinate.
	 * 
	 * @param bottom
	 *            the bottom y coordinate to set.
	 */
	public void setBottom(long bottom) {
		this.bottom.setValue(bottom);
	}

	/**
	 * Returns the bottom y coordinate.
	 * 
	 * @return the bottom y coordinate.
	 */
	public long getBottom() {
		return bottom.getValue();
	}
}