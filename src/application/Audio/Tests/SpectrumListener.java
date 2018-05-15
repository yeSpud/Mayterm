package application.Audio.Tests;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;

public class SpectrumListener implements AudioSpectrumListener {

	public final SpectrumBar[] bars;
	public double minValue;
	public double[] norms;
	public int[] spectrumBuketCounts;
	
	SpectrumListener(double startFreq, MediaPlayer player, SpectrumBar[] bars) {
		this.bars = bars;
		this.minValue = player.getAudioSpectrumThreshold();
		this.norms = createNormArray();
	}
	
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		// TODO Finish this
		
	}
	
	private double[] createNormArray() {
		double[] normArray = new double[bars.length];
		double currentNorm = 0.05;
		for (int i = 0; i < normArray.length; i++) {
			normArray[i] = 1 + currentNorm;
			currentNorm *= 2;
		}
		return normArray;
	}
	
	/*
	private int[] createBucketCounts(double startFreq, int bandCount) {
		int[] bucketCounts = new int[bars.length];
		
		double bandwidth = 22050.0/bandCount, centerFreq = bandwidth/2, currentSpectrumFreq = centerFreq,
				currentEQFreq = startFreq/2, currentCutoff = 0;
		int currentBucketIndex = -1;
		
		for (int i = 0; i < bandCount; i++) {
			if (currentSpectrumFreq > currentCutoff) {
				currentEQFreq *= 2;
				currentCutoff = currentEQFreq + currentEQFreq / 2;
			}
		}
		
	}
	*/

}
