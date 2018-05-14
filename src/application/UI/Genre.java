package application.UI;

import javafx.scene.paint.Color;

public enum Genre {
	TRAP(140, 15, 39), // Trap: 75 - 175 bpm
	DNB(242, 25, 4), // DnB: 160 - 180 or 87 bpm
	HOUSE(234, 140, 0), // House: ~128 bpm
	ELECTRO(230, 206, 0), // Electro: ~128 bpm
	HARDDANCE(1, 151, 0), // Harddance: 150 or 170 bpm
	GLITCHHOP(11, 151, 87), // Glitch: 110 or 108 bpm
	NUDISCO(28, 171, 179), // NuDisco: 84 - 159 bpm
	FUTUREBASS(154, 152, 252), // Future bass: 75 - 175 bpm
	TRANCE(0, 126, 231), // Trance: 125 (135) - 150 bpm
	DUBSTEP(141, 4, 225), // Dubstep: 70 bpm or 140 - 150 bpm
	DRUMSTEP(243, 33, 136), // Drumstep: 160 - 180 bpm
	ELECTRONIC(193, 193, 193); // Catch all

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
