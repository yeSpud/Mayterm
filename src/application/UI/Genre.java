package application.UI;

import application.Audio.AudioPlayer;
import application.Audio.Spectrum;
import application.Database.Database;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Genre {
	public enum genre {
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

		private genre(final int r, final int g, final int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public Color getColor() {
			return Color.rgb(this.r, this.g, this.b);
		}

	}

	public static void setGenre(Color genre) {

		VisulizerDisplay.nothing.setFill(genre);
		VisulizerDisplay.nothing.setStroke(genre);
		CoverArt.art.setFill(genre);
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(i);
			bar.setFill(genre);
		}

	}

	public static void rotateGenre(int number) {
		String path = AudioPlayer.media.getSource().replace("file:\\\\", "").replace("%20", " ").replace("%5B", "[")
				.replace("%5D", "]").replace(":", "%3A").replace("\\", "%5C");
		if (number > 11) {
			number = 0;
		}
		if (number < 0) {
			number = 11;
		}
		switch (number) {
		case 0: {
			setGenre(genre.DNB.getColor());
			Database.editGenre(path, genre.DNB);
			break;
		}
		case 1: {
			setGenre(genre.DRUMSTEP.getColor());
			Database.editGenre(path, genre.DRUMSTEP);
			break;
		}
		case 2: {
			setGenre(genre.DUBSTEP.getColor());
			Database.editGenre(path, genre.DUBSTEP);
			break;
		}
		case 3: {
			setGenre(genre.ELECTRO.getColor());
			Database.editGenre(path, genre.ELECTRO);
			break;
		}
		case 4: {
			setGenre(genre.ELECTRONIC.getColor());
			Database.editGenre(path, genre.ELECTRONIC);
			break;
		}
		case 5: {
			setGenre(genre.FUTUREBASS.getColor());
			Database.editGenre(path, genre.FUTUREBASS);
			break;
		}
		case 6: {
			setGenre(genre.GLITCHHOP.getColor());
			Database.editGenre(path, genre.GLITCHHOP);
			break;
		}
		case 7: {
			setGenre(genre.HARDDANCE.getColor());
			Database.editGenre(path, genre.HARDDANCE);
			break;
		}
		case 8: {
			setGenre(genre.HOUSE.getColor());
			Database.editGenre(path, genre.HOUSE);
			break;
		}
		case 9: {
			setGenre(genre.NUDISCO.getColor());
			Database.editGenre(path, genre.NUDISCO);
			break;
		}
		case 10: {
			setGenre(genre.TRANCE.getColor());
			Database.editGenre(path, genre.TRANCE);
			break;
		}
		case 11: {
			setGenre(genre.TRAP.getColor());
			Database.editGenre(path, genre.TRAP);
			break;
		}
		}
	}

}
