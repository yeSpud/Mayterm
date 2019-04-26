package javacode.UI;

import javacode.GenreColors;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AlbumArt extends Rectangle {

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
