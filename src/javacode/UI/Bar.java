package javacode.UI;

import javacode.Debugger;
import javacode.GenreColors;
import javafx.scene.paint.Color;

/**
 * Creates a single instance of a bar that is used for the spectrogram when a track is playing or is paused.
 *
 * @author Spud
 */
public class Bar extends javafx.scene.shape.Rectangle implements javacode.UI.ColoredNode {

	public Bar() {
		this.setStrokeType(javafx.scene.shape.StrokeType.CENTERED);
		this.setWidth(12);
		this.setHeight(3);
		this.setColor(GenreColors.ELECTRONIC);
	}

	/**
	 * Sets the relative position of the node in the window based off of the provided width and height, and then applies
	 * an offset based on the index.
	 *
	 * @param width  The current window width.
	 * @param height The current window height.
	 * @param index  The index of the bar. ie: The first bar would have an index of 0, the second bar would have an index
	 *               of 1...
	 */
	public void updatePosition(double width, double height, int index) {
		double x = (118 + (16.625 * index)), y = height * 0.4851752022d;
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void setColor(Color color) {
		Debugger.d(this.getClass(), "Changing bar color to " + color.toString());
		this.setFill(color);
	}

	@Override
	public void setColor(GenreColors color) {
		this.setColor(color.getColor());
	}

}
