package javacode.UI;

import javacode.GenreColors;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AlbumArt extends Rectangle {

	public AlbumArt() {

	}

	private AlbumArt(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public AlbumArt createAlbumArt() {
		AlbumArt albumArt = new AlbumArt(1034, 198, 126, 126);

		albumArt.setColor(GenreColors.ELECTRONIC);
		return albumArt;
	}

	/**
	 * TODO Documentation
	 * @param color
	 */
	public void setColor(Color color) {
		this.setFill(color);
	}

	/**
	 * TODO Documentation
	 * @param color
	 */
	public void setColor(GenreColors color) {
		this.setColor(color.getColor());
	}

}
