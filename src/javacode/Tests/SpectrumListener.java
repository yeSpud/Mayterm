package javacode.Tests;

import javacode.Audio.AudioPlayer;
import javacode.UI.Spectrum;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.shape.Rectangle;

public class SpectrumListener implements AudioSpectrumListener {

	//public final SpectrumBar[] bars;
	public double minValue;
	public double[] norms;
	public int[] spectrumBuketCounts = createBucketCounts(20, 63);
	
	public SpectrumListener(double startFreq/*, MediaPlayer player, SpectrumBar[] bars*/) {
		//this.bars = bars;
		//this.minValue = player.getAudioSpectrumThreshold();
		this.minValue = AudioPlayer.player.getAudioSpectrumThreshold();
		this.norms = createNormArray();
	}
	
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		int index = 0, bucketIndex = 0, currentBucketCount = 0;
		double sum = 0.0d;
		
		//if (timestamp < .125d) {
			//CoverArt.autoSetArt(AudioPlayer.media.getSource());
		//}
		
		
		while (index < magnitudes.length) {
			sum += magnitudes[index] - minValue;
			++currentBucketCount;
			
			if (currentBucketCount >= spectrumBuketCounts[bucketIndex]) {
				
				//bars[bucketIndex].setValue(sum / norms[bucketIndex]);
				
				
				//for (int i = 0; i < 63; i++) { // 7
					Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(bucketIndex);
					bar.setHeight(sum / norms[bucketIndex]);
				//}
				
				currentBucketCount = 0;
				sum = 0.0d;
				++bucketIndex;
			}
			index++;
		}
		
	}
	
	private double[] createNormArray() {
		double[] normArray = new double[Spectrum.spectrum.getChildren().size()];
		double currentNorm = 0.05;
		for (int i = 0; i < normArray.length; i++) {
			normArray[i] = 1 + currentNorm;
			currentNorm *= 2;
		}
		return normArray;
	}
	
	
	private int[] createBucketCounts(double startFreq, int bandCount) {
		int[] bucketCounts = new int[Spectrum.spectrum.getChildren().size()];
		
		double bandwidth = 22050.0/bandCount, centerFreq = bandwidth/2, currentSpectrumFreq = centerFreq,
				currentEQFreq = startFreq/2, currentCutoff = 0;
		int currentBucketIndex = -1;
		
		for (int i = 0; i < bandCount; i++) {
			if (currentSpectrumFreq > currentCutoff) {
				currentEQFreq *= 2;
				currentCutoff = currentEQFreq + currentEQFreq / 2;
				++currentBucketIndex;
				if (currentBucketIndex == bucketCounts.length) {
					break;
				}
			}
			++bucketCounts[currentBucketIndex];
			currentSpectrumFreq += bandwidth;
		}
		return bucketCounts;
		
	}
	

}
