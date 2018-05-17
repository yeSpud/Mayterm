package application.Audio;

import application.UI.CoverArt;
import application.UI.DisplayText;
import application.UI.VisulizerDisplay;
import javafx.scene.Group;
import javafx.scene.media.AudioSpectrumListener;
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
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(i);
			bar.setHeight(12);
		}
	}

	public static void disableSpectrum(boolean retry) {
		VisulizerDisplay.root.getChildren().remove(Spectrum.spectrum);
		VisulizerDisplay.root.getChildren().add(VisulizerDisplay.nothing);
		if (!retry) {
			DisplayText.setArtist("No file currently selected");
			DisplayText.setTitle("Press \"O\" to select a file");
		}
		CoverArt.setArt(null);
	}

	public static void enableSpectrum() {
		VisulizerDisplay.root.getChildren().remove(VisulizerDisplay.nothing);
		VisulizerDisplay.root.getChildren().add(Spectrum.spectrum);
	}

	public static void setupSpectrumMovement() {
		AudioPlayer.player.setAudioSpectrumNumBands(63); // 7
		AudioPlayer.player.setAudioSpectrumInterval(0.033d); // 0.0167d

		// player.setAudioSpectrumThreshold(-100);
		AudioPlayer.player.setAudioSpectrumListener(null);
		// spectrumListener = new SpectrumListener(Display.STARTING_FREQUENCY, player,
		// spectrimBars)
		AudioPlayer.player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				// TODO: Redo spectrum
				/*
				 * System.out.println(String.format("timestamp: %s\nmagnitides: %s", timestamp,
				 * Arrays.toString(magnitudes)));
				 */
				
				/*
				double t[] = new double[magnitudes.length];
				for (int a = 0; a < magnitudes.length; a++) {
					t[a] = (double) magnitudes[a];
				}
				TestSpectrtum.foo(t);
				*/
				
				for (int i = 0; i < 63; i++) { // 7
					Rectangle bar = (Rectangle) spectrum.getChildren().get(i);
					bar.setHeight((63 - magnitudes[i] * -1) * 4);
					/*
					 * Group tests = ((Group) Display.bars); Rectangle test = (Rectangle)
					 * tests.getChildren().get(57 - (i * 9)); if ((60 - magnitudes[i] * -1) < 2) {
					 * test.setHeight(2); } else { test.setHeight(Math.pow(60 - magnitudes[i], 2) /
					 * 50); }
					 */
				}

				// System.out.println((magnitudes[1] + 60));
				if (Math.abs(magnitudes[0] - AudioPlayer.pmag) > 7.75) { // works for blossom, doesnt work for anything
																			// else 🙄
					if (!AudioPlayer.up) {
						AudioPlayer.up = true;
					}
				} else {
					if (AudioPlayer.up) {
						AudioPlayer.up = false;
						AudioPlayer.beat++;
					}
				}

				AudioPlayer.BPM = (int) ((AudioPlayer.beat / timestamp) * 60);

				AudioPlayer.pmag = magnitudes[1];

				if (timestamp > 5 && !(Math.abs(magnitudes[0] - AudioPlayer.pmag) > 7.625) && AudioPlayer.up) {
					//System.out.println(AudioPlayer.BPM);
				}

				/*
				 * for (int i = 0; i < 7; i++) { Group tests = ((Group) Display.bars); Rectangle
				 * test = (Rectangle) tests.getChildren().get(56 - (i * 9)); if ((60 -
				 * magnitudes[i] * -1) < 2) { test.setHeight(2); } else { test.setHeight((60 -
				 * magnitudes[i])); } }
				 * 
				 * for (int i = 0; i < 7; i++) { Group tests = ((Group) Display.bars); Rectangle
				 * test = (Rectangle) tests.getChildren().get(58 - (i * 9)); if ((60 -
				 * magnitudes[i] * -1) < 2) { test.setHeight(2); } else { test.setHeight((60 -
				 * magnitudes[i])); } }
				 */

			}

		});
	}

}
