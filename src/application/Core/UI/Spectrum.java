package application.Core.UI;

import application.Core.Debugger;
import application.Core.Audio.AudioPlayer;
import application.Core.Database.Database;
import application.SpectrumAlg.Default;
import application.SpectrumAlg.FTTVis;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Class responsible for creating, clearing, enabling, and setting the spectrum
 * movements.
 * 
 * @author Spud
 *
 */
public class Spectrum {

	public static Group spectrum = new Group();

	/**
	 * Creates the spectrum bars (all 63 of them).
	 */
	public static void createSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle rectangle = new Rectangle(115 + (i * (16.65)), 356, 12, 2); // 1.9453968254
			rectangle.setStrokeType(StrokeType.CENTERED);
			spectrum.getChildren().add(rectangle);
		}
	}

	/**
	 * Forces the spectrum bar's height to 12.
	 */
	public static void clearSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) spectrum.getChildren().get(i);
			bar.setHeight(12);
		}
	}

	/**
	 * Removes the spectrum from the scene. If retry is true, then it will just
	 * remove the cover art.
	 * 
	 * @param retry
	 *            - If true, only the cover art will be removed, the title and
	 *            artist remain the same.
	 */
	public static void disableSpectrum(boolean retry) {
		MainDisplay.root.getChildren().remove(spectrum);
		MainDisplay.root.getChildren().add(MainDisplay.zeroBar);
		if (!retry) {
			Artist.setArtist("No file currently selected");
			Title.setTitle("Press \"O\" to select a file");
		}
		CoverArt.setArt(null);
	}

	/**
	 * Removes the place holder bar, and adds the spectrum back to the scene.
	 */
	public static void enableSpectrum() {
		MainDisplay.root.getChildren().remove(MainDisplay.zeroBar);
		MainDisplay.root.getChildren().add(spectrum);
	}

	/**
	 * Sets the number of bands, refresh internal, and the type of algorithm to use
	 * for the spectrum movement.
	 */
	public static void setupSpectrumMovement() {
		AudioPlayer.player.setAudioSpectrumNumBands(63); // 63
		AudioPlayer.player.setAudioSpectrumInterval(0.033d); // 0.0167
		AudioPlayer.player.setAudioSpectrumThreshold(-60);
		if (Database.getSettings().toString().equals("[1]")) {
			Debugger.d(Spectrum.class, "Using algorithm: FFTVis");
			AudioPlayer.player.setAudioSpectrumListener(new FTTVis());
		} else {
			Debugger.d(Spectrum.class, "Using algorithm: Default");
			AudioPlayer.player.setAudioSpectrumListener(new Default());
		}

	}

}
