package javacode.UI;

/**
 * An interface for all the nodes that need to be made relative based on the windows size.
 *
 * @author Spud
 */
public interface RelativeNode {

	/**
	 * Sets the relative position of the node in the window based off of the provided width and height.
	 *
	 * @param width  The current window width.
	 * @param height The current window height.
	 */
	public void updatePosition(double width, double height);

}
