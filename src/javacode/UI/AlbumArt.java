package javacode.UI;

import javacode.GenreColors;
import javafx.scene.paint.Color;

public class AlbumArt extends javafx.scene.shape.Rectangle {

	public AlbumArt() {
		this.setX(118);
		this.setY(396);
		this.setWidth(126);
		this.setHeight(126);
		this.setColor(GenreColors.ELECTRONIC);
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		javacode.Debugger.d(this.getClass(), "Changing album art color to " + color.toString());
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

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	public Color getColor() {
		return (Color) this.getFill();
	}

}
