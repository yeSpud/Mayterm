package application.SpectrumThings;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.jtransforms.fft.DoubleFFT_2D;

import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import application.Audio.WaveFile;
import application.Audio.wavConverter;

public class newFFT {
	static WaveFile wav = null;

	public newFFT() {

		// TODO 2: Wait for file to load
		wavConverter.convertToWAV(new File(AudioFile.toFilePath(AudioPlayer.media.getSource())));
		
		try {
			wav = new WaveFile(new File(AudioFile.toFilePath(AudioPlayer.media.getSource())));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}

		long ampNumber = (long) (wav.getAudioFormat().getFrameRate());
		int amp[] = new int[(int) wav.getAudioFormat().getFrameRate()/50];
		System.out.println("Rate of points per second: " + ampNumber);
		// System.out.print(wav.getSampleInt(2000)/2000); // Gets the amplitude at a
		// given value
		for (int i = 0; i < (int) wav.getAudioFormat().getFrameRate()/50; i++) {
			int amplitude = wav.getSampleInt(i); // Gets the amplitude at a given value
			amp[i] = amplitude;

		}
		double AmpDouble[] = new double[(int) wav.getAudioFormat().getFrameRate()];
		for (int a = 0; a < amp.length; a++) {
			AmpDouble[a] = (double) amp[a];
		}

		// System.out.println(Arrays.toString(amp));

		int greatest = 0;
		int greatestLocaiton = 0;
		int least = 0;
		int leastLocation = 0;
		double doubleAmp[] = new double[amp.length];
		for (int i = 0; i < amp.length; i++) {
			if (amp[i] < least) {
				least = amp[i];
				leastLocation = i;
			}
			if (amp[i] > greatest) {
				greatest = amp[i];
				greatestLocaiton = i;
			}
			doubleAmp[i] = amp[i];
		}
		System.out.println("DoubleAmp before:" + Arrays.toString(doubleAmp));

		System.out.println(String.format("Grestest %s (Locaiton: %s)\nLeast: %s (Location: %s)", greatest,
				greatestLocaiton, least, leastLocation));
		DoubleFFT_2D fftArray = new DoubleFFT_2D(2, (long) Math.pow(2, 8));
		// System.out.println(CommonUtils.isPowerOf2(Math.pow(greatest,2)));
		// System.out.println(CommonUtils.isPowerOf2(1024));
		fftArray.realForward(doubleAmp);
		double newgreatest = 0;
		greatestLocaiton = 0;
		double newleast = 0;
		leastLocation = 0;
		for (int i = 0; i < doubleAmp.length; i++) {
			if (doubleAmp[i] < least) {
				newleast = doubleAmp[i];
				leastLocation = i;
			}
			if (doubleAmp[i] > greatest) {
				newgreatest = doubleAmp[i];
				greatestLocaiton = i;
			}
		}
		System.out.println(
				String.format("NEW Grestest %s (NEW Locaiton: %s)\nNEW Least: %s (NEW Location: %s)\nDelta: %s",
						newgreatest, greatestLocaiton, newleast, leastLocation, newgreatest - newleast)); 
		System.out.println("fftArray.realForward: " + Arrays.toString(doubleAmp));

	}

}
