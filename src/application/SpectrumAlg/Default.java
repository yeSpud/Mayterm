package application.SpectrumAlg;

import application.Audio.AudioPlayer;
import application.Core.UI.CoverArt;
import application.Core.UI.Spectrum;
import application.Core.UI.SpectrumDebug;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The original spectrum algorithm.<br ><br >Yeah it's really simple, but its not accurate. That being said, if the
 * official one never gets finished, there's always this {@code Fallback}.<br ><br ><i>See what I did there? Look I'm funny.</i>
 * 
 * @author Spud
 *
 */
public class Default implements AudioSpectrumListener {

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

		if (timestamp < .07d) {
			CoverArt.autoSetArt(AudioPlayer.media.getSource());
		}

		for (int i = 0; i < magnitudes.length; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(62 - i);
			bar.setHeight((63 - magnitudes[i] * -1) * 4);
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
