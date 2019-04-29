package javacode.UI.Text;

import javacode.GenreColors;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;

// FIXME
public class GenreText extends Text {

	public GenreText() {

		// Get a string array fo the genre names, and have the center one be the selected one
		String[] selectedGenre = new String[GenreColors.values().length];
		for (int i = 0; i < selectedGenre.length; i++) {
			selectedGenre[i] = GenreColors.values()[i].name();
		}
		this.setText(Arrays.toString(selectedGenre).replaceAll("[\\[,\\]]", " "));
		this.setFont(new Font(20));
		this.setX(50);
		this.setY(50);

	}
}
