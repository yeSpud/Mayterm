package application.SpectrumThings;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.jtransforms.fft.DoubleFFT_2D;

import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import application.Audio.WaveFile;

public class newFFT {
	static WaveFile wav = null;
	public newFFT() {
		
		try {
			wav = new WaveFile(new File(AudioFile.toFilePath(AudioPlayer.media.getSource())));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
		long ampNumber = (long) (wav.getAudioFormat().getFrameRate());
		int amp[] = new int[(int) wav.getAudioFormat().getFrameRate()];
		
		for (int i = 0; i < (int) wav.getAudioFormat().getFrameRate(); i++) {
		    int amplitude = wav.getSampleInt(i + (int) ampNumber); // Gets the amplitude at a given value
		    amp[i] = amplitude;
		}
		
		int greatest = 0;
		int least = 0;
		for (int i = 0; i < amp.length; i++) {
			if (amp[i] < least) {
				least = amp[i];
			}
			if (amp[i] > greatest) {
				greatest = amp[i];
			}
		}
		
		DoubleFFT_2D fftArray = new DoubleFFT_2D((long) greatest, (long) amp.length);
		
		fftArray.realForward((double[]) amp);
	}
	
}
