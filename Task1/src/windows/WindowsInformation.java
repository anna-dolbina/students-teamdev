package windows;

/**
 * Interface for a for class representing windows information.
 * 
 * @author anna.dolbina
 * 
 */
public interface WindowsInformation {
	/**
	 * Refreshes information about windows.
	 * 
	 * @return refresh status: succeeded or failed.
	 */
	void refresh();

	/**
	 * Returns the number of windows.
	 * 
	 * @return the number of windows.
	 */
	int size();

	/**
	 * Returns information about the window corresponding to this index.
	 * 
	 * @param index
	 * @return The window information corresponding to this index.
	 */
	public WindowInformation get(int index);
}
