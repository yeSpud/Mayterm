package application.SpectrumThings;

import application.Audio.AudioPlayer;
import application.UI.CoverArt;
import application.UI.DisplayText;
import application.UI.MainDisplay;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Spectrum {

	public static Group spectrum = new Group();

	public static void createSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle rectangle = new Rectangle(115 + (i * (16.65)), 356, 12, 2); // 1.9453968254
			rectangle.setStrokeType(StrokeType.CENTERED);
			spectrum.getChildren().add(rectangle);
		}
	}

	public static void clearSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) spectrum.getChildren().get(i);
			bar.setHeight(12);
		}
	}

	public static void disableSpectrum(boolean retry) {
		MainDisplay.root.getChildren().remove(spectrum);
		MainDisplay.root.getChildren().add(MainDisplay.nothing);
		if (!retry) {
			DisplayText.setArtist("No file currently selected");
			DisplayText.setTitle("Press \"O\" to select a file");
		}
		CoverArt.setArt(null);
	}

	public static void enableSpectrum() {
		MainDisplay.root.getChildren().remove(MainDisplay.nothing);
		MainDisplay.root.getChildren().add(spectrum);
	}

	public static void setupSpectrumMovement() {
		AudioPlayer.player.setAudioSpectrumNumBands(63); // 63
		AudioPlayer.player.setAudioSpectrumInterval(0.033d); // 0.0167
		AudioPlayer.player.setAudioSpectrumThreshold(-60);
		//AudioPlayer.player.setAudioSpectrumListener(new SpectrumListener(12000));
		AudioPlayer.player.setAudioSpectrumListener(new SpectrumListener());
		
	}

}
