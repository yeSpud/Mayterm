package application.SpectrumThings;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import application.Audio.WaveFile;

public class FFT {
	static WaveFile wav = null;
	
	public static int getAmplitude(int time) {
		try {
			wav = new WaveFile(new File(AudioFile.toFilePath(AudioPlayer.media.getSource())));
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(wav.getAudioFormat().getFrameRate()); // Gets the number of amplitudes in 1 second
		//System.out.println(wav.getFramesCount()); // The total number of amplitudes
		long ampNumber = (long) (time * wav.getAudioFormat().getFrameRate());
		int amp[] = new int[(int) wav.getAudioFormat().getFrameRate()];
		for (int i = 0; i < (int) wav.getAudioFormat().getFrameRate(); i++) {
		    int amplitude = wav.getSampleInt(i + (int) ampNumber); // Gets the amplitude at a given value
		    amp[i] = amplitude;
		}
		//System.out.println(Arrays.toString(amp));
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
		//System.out.println("Largest: " + greatest);
		//System.out.println("Smallest: " + least);
		//System.out.println("Change: " + (greatest - least));
		return (int) (greatest - least); 
	}

}
