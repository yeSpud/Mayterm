package javacode.Audio;

import javacode.Debugger;
import javacode.UI.Bar;
import javafx.scene.media.AudioSpectrumListener;

import java.util.Arrays;

public class AudioAnalysis implements AudioSpectrumListener {

	private Bar[] bars;

	public AudioAnalysis(Bar[] bars) {
		this.bars = bars;
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		Debugger.d(this.getClass(), "Timestamp: " + timestamp);
		//Debugger.d(this.getClass(), "Duration: " + duration);
		Debugger.d(this.getClass(), "Magnitudes: " + Arrays.toString(magnitudes));
		//Debugger.d(this.getClass(), "Phases: " + Arrays.toString(phases));

		for (int i = 0; i < magnitudes.length && i < bars.length; i++) {
			bars[i].setHeight((magnitudes[i] + 63) * 4);
			bars[i].setY(bars[i].rootY - bars[i].getHeight());
		}
	}
}
