package javacode.SpectrumAlg;

import javacode.Audio.AudioFile;
import javacode.Audio.AudioPlayer;
import javacode.UI.CoverArt;
import javacode.UI.Spectrum;
import javacode.UI.SpectrumDebug;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The original spectrum algorithm.<br >
 * <br >
 * Yeah it's really simple, but its not accurate. That being said, if the
 * official one never gets finished, there's always this {@code Fallback}.<br >
 * <br >
 * <i>See what I did there? Look I'm funny.</i>
 * 
 * @author Spud
 *
 */
public class Default implements AudioSpectrumListener {

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

		if (timestamp > 16.00d && timestamp < 16.07d) {
			String src = AudioFile.toFilePath(AudioPlayer.media.getSource());
			CoverArt.setArt(src);
		}

		if ((AudioPlayer.media.getDuration().toSeconds() - timestamp) < 35.0d) {
			CoverArt.setArt(null);
		}

		for (int i = 0; i < magnitudes.length; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(62 - i);
			float height = ((63 - magnitudes[i] * -1) * 4);
			if (height != bar.getHeight()) {
				bar.setHeight(height);
				try {
					Text text = (Text) SpectrumDebug.spectrumText.getChildren().get(i);
					text.setText(String.valueOf(String.format("%.2f", bar.getHeight())));
					text.setX(bar.getX());
					text.setY(bar.getY() - 15);
				} catch (IndexOutOfBoundsException foo) {
					// Do nothing
				}
			}
		}

	}

}
