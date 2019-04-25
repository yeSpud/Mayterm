package javacode.UI;

import javacode.GenreColors;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AlbumArt extends Rectangle {

	public AlbumArt() {
		this.setX(1034);
		this.setY(198);
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
