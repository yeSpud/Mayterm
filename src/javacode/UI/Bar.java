package javacode.UI;

import javacode.Debugger;
import javacode.GenreColors;
import javafx.scene.paint.Color;

public class Bar extends javafx.scene.shape.Rectangle {

	public Bar() {
		this.setStrokeType(javafx.scene.shape.StrokeType.CENTERED);
		this.setWidth(13);
		this.setHeight(3);
		this.setColor(GenreColors.ELECTRONIC);
	}

	/**
	 * TODO Documentation
	 *
	 * @param width
	 * @param height
	 */
	public void updatePosition(double width, double height, int index) { // TODO Add index variable for different spacing
		double x = (117.5 + (1 * index)), y = height * 0.4851752022d;
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		Debugger.d(this.getClass(), "Changing bar color to " + color.toString());
		this.setFill(color);
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public void setColor(GenreColors color) {
		this.setColor(color.getColor());
	}

}
