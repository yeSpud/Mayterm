package application.UI;

import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import application.Database.Database;
import application.SpectrumThings.Spectrum;
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
		ELECTRONIC(193, 193, 193), // Catch all
		OTHER(255, 255, 255); // Other

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

		MainDisplay.nothing.setFill(genre);
		MainDisplay.nothing.setStroke(genre);
		CoverArt.art.setFill(genre);
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(i);
			bar.setFill(genre);
		}
		if (genre.equals(Genre.genre.OTHER.getColor())) {
			CoverArt.blackCat(true);
		} else {
			CoverArt.blackCat(false);
		}

	}

	public static void rotateGenre(int number) {
		String path = AudioFile.toFilePath(AudioPlayer.media.getSource());
		if (number > 12) {
			number = 0;
		}
		if (number < 0) {
			number = 12;
		}
		switch (number) {
		case 0: {
			setGenre(genre.DNB.getColor());
			Database.editGenre(path, genre.DNB);
			CoverArt.blackCat(false);
			break;
		}
		case 1: {
			setGenre(genre.DRUMSTEP.getColor());
			Database.editGenre(path, genre.DRUMSTEP);
			CoverArt.blackCat(false);
			break;
		}
		case 2: {
			setGenre(genre.DUBSTEP.getColor());
			Database.editGenre(path, genre.DUBSTEP);
			CoverArt.blackCat(false);
			break;
		}
		case 3: {
			setGenre(genre.ELECTRO.getColor());
			Database.editGenre(path, genre.ELECTRO);
			CoverArt.blackCat(false);
			break;
		}
		case 4: {
			setGenre(genre.ELECTRONIC.getColor());
			Database.editGenre(path, genre.ELECTRONIC);
			CoverArt.blackCat(false);
			break;
		}
		case 5: {
			setGenre(genre.FUTUREBASS.getColor());
			Database.editGenre(path, genre.FUTUREBASS);
			CoverArt.blackCat(false);
			break;
		}
		case 6: {
			setGenre(genre.GLITCHHOP.getColor());
			Database.editGenre(path, genre.GLITCHHOP);
			CoverArt.blackCat(false);
			break;
		}
		case 7: {
			setGenre(genre.HARDDANCE.getColor());
			Database.editGenre(path, genre.HARDDANCE);
			CoverArt.blackCat(false);
			break;
		}
		case 8: {
			setGenre(genre.HOUSE.getColor());
			Database.editGenre(path, genre.HOUSE);
			CoverArt.blackCat(false);
			break;
		}
		case 9: {
			setGenre(genre.NUDISCO.getColor());
			Database.editGenre(path, genre.NUDISCO);
			CoverArt.blackCat(false);
			break;
		}
		case 10: {
			setGenre(genre.TRANCE.getColor());
			Database.editGenre(path, genre.TRANCE);
			CoverArt.blackCat(false);
			break;
		}
		case 11: {
			setGenre(genre.TRAP.getColor());
			Database.editGenre(path, genre.TRAP);
			CoverArt.blackCat(false);
			break;
		}
		case 12: {
			setGenre(genre.OTHER.getColor());
			Database.editGenre(path, genre.OTHER);
			CoverArt.blackCat(true);
			break;
		}
		}
	}

}
