package javacode.UI;

/**
 * An interface for all the nodes that are colored, and need special functions as a result.
 *
 * @author Spud
 */
public interface ColoredNode {

	/**
	 * Sets the color of the node
	 *
	 * @param color The color to be applied to the node.
	 */
	public void setColor(javafx.scene.paint.Color color);

	/**
	 * Sets the color of the node based on the genre color.
	 *
	 * @param genreColor The genre color to be applied to the node.
	 */
	public void setColor(javacode.GenreColors genreColor);

}
