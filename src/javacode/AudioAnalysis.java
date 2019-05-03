package javacode;

import javafx.scene.media.AudioSpectrumListener;

import java.util.Arrays;

public class AudioAnalysis implements AudioSpectrumListener {

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		Debugger.d(this.getClass(), "Timestamp: " + timestamp);
		Debugger.d(this.getClass(), "Duration: " + duration);
		Debugger.d(this.getClass(), "Magnitudes: " + Arrays.toString(magnitudes));
		Debugger.d(this.getClass(), "Phases: " + Arrays.toString(phases));
	}
}
