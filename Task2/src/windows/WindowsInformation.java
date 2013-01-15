package windows;

/**
 * Interface for a class storing information about windows.
 * 
 * @author anna.dolbina
 * 
 */
public interface WindowsInformation {
	/**
	 * Refreshes information about windows.
	 */
	void refresh();

	/**
	 * Returns the number of windows.
	 * 
	 * @return the number of windows.
	 */
	int size();

	/**
	 * Returns the window information corresponding to this index.
	 * 
	 * @param index
	 * @return The window information corresponding to this index.
	 */
	public WindowInformation get(int index);
}
