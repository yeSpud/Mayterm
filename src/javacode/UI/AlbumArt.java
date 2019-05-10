package javacode.UI;

import javacode.GenreColors;
import javafx.scene.paint.Color;

/**
 * Corresponds to the colored square that is behind the cat.
 *
 * @author Spud
 */
public class AlbumArt extends javafx.scene.shape.Rectangle implements javacode.UI.ColoredNode {

	public AlbumArt() {
		this.setX(118);
		this.setY(396);
		this.setWidth(126);
		this.setHeight(126);
		this.setColor(GenreColors.ELECTRONIC);
	}

	@Override
	public void setColor(Color color) {
		javacode.Debugger.d(this.getClass(), "Changing album art color to " + color.toString());
		this.setFill(color);
	}

	@Override
	public void setColor(GenreColors color) {
		this.setColor(color.getColor());
	}

	/**
	 * Gets the color of the album art.
	 *
	 * @return The color (as a Color object) of the album art.
	 */
	public Color getColor() {
		return (Color) this.getFill();
	}

}
