package application;

import javafx.scene.paint.Color;

public enum Genre {
	TRAP(140, 15, 39), DNB(242, 25, 4), HOUSE(234, 140, 0), ELECTRO(230, 206, 0), HARDDANCE(1, 151, 0), GLITCHHOP(11,
			151, 87), NUDISCO(28, 171, 179), FUTUREBASS(154, 152,
					252), TRANCE(0, 126, 231), DUBSTEP(141, 4, 225), DRUMBSTEP(243, 33, 136), ELECTRONIC(193, 193, 193);

	private final int r, g, b;

	private Genre(final int r, final int g, final int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color getColor() {
		return Color.rgb(this.r, this.g, this.b);
	}

}
