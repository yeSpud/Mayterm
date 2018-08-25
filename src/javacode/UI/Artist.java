package javacode.UI;

import java.io.File;

import javacode.Audio.getMetadata;
import javacode.Errors.UnrecognizableFileType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Artist {
	private static Text artist = new Text("No file currently selected"/* "TRISTAM & BRAKEN" */);
	private static final double textSize = 68.75d;
	
	public static void setup() {
		artist.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, textSize));
		artist.setX(1012 - artist.getLayoutBounds().getWidth());
		artist.setY(306);
		artist.setRotate(180);
		artist.setFill(Color.WHITE);
		MainDisplay.root.getChildren().add(artist);
	}
	
	public static void setArtist(String text) {
		try {
		artist.setText(text.toUpperCase());
		} catch (NullPointerException noArtist) {
			artist.setText("NO ARTIST");
		}
		artist.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, textSize));
		artist.setX(1012 - artist.getLayoutBounds().getWidth());
		checkTextSpacing(artist, FontWeight.EXTRA_BOLD, textSize);
	}
	
	public static void setArtist(File sourceFile) throws UnrecognizableFileType {
		if (sourceFile.getAbsolutePath().endsWith(".mp3")) {
			setArtist(getMetadata.getMp3(sourceFile)[0]);
		} else if (sourceFile.getAbsolutePath().endsWith(".mp4") || sourceFile.getAbsolutePath().endsWith(".m4a") || sourceFile.getAbsolutePath().endsWith(".m4v")) {
			setArtist(getMetadata.getMp4(sourceFile)[0]);
		} else {
			throw new UnrecognizableFileType();
		}
	}
	
	private static void checkTextSpacing(Text text, FontWeight weight, double fontSize) {
		double overSize = (text.getLayoutBounds().getWidth() + 20) - 1012;
		if (overSize > 0) {
			text.setFont(Font.font("Arial", weight, fontSize - 1));
			text.setX(1012 - text.getLayoutBounds().getWidth());
			checkTextSpacing(text, weight, fontSize - 1);
		}
	}
}
