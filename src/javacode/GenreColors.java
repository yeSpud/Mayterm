package javacode;

import javafx.scene.paint.Color;

/**
 * The list of genres, their RGB color, and normally what defines them (Though
 * in the end its users preference).
 *
 * @author Spud
 */
public enum GenreColors {

	/**
	 * Trap: 75 - 175 bpm.
	 */
	TRAP(140, 15, 39),

	/**
	 * DnB: 160 - 180 or 87 bpm.
	 */
	DNB(242, 25, 4),

	/**
	 * House: ~128 bpm.
	 */
	HOUSE(234, 140, 0),

	/**
	 * Electro: ~128 bpm.
	 */
	ELECTRO(230, 206, 0),

	/**
	 * Hard-dance: 150 or 170 bpm.
	 */
	HARDDANCE(1, 151, 0),

	/**
	 * Glitch: 110 or 108 bpm.
	 */
	GLITCHHOP(11, 151, 87),

	/**
	 * NuDisco: 84 - 159 bpm.
	 */
	NUDISCO(28, 171, 179),

	/**
	 * Future bass: 75 - 175 bpm.
	 */
	FUTUREBASS(154, 152, 252),

	/**
	 * Trance: 125 (135) - 150 bpm.
	 */
	TRANCE(0, 126, 231),

	/**
	 * Dubstep: 70 bpm or 140 - 150 bpm.
	 */
	DUBSTEP(141, 4, 225),

	/**
	 * Drumstep: 160 - 180 bpm.
	 */
	DRUMSTEP(243, 33, 136),

	/**
	 * Catch all.
	 */
	ELECTRONIC(193, 193, 193),

	/**
	 * Special occasion color! <b>:D</b>
	 */
	OTHER(255, 255, 255);

	private final int r, g, b;

	private GenreColors(final int r, final int g, final int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Gets the color of the genre.
	 *
	 * @return Color - The color of the genre (RGB).
	 */
	public Color getColor() {
		return Color.rgb(this.r, this.g, this.b);
	}

}
