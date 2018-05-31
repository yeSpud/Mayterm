package application.Audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import application.Database.Environment;
import application.Errors.UnrecognizableOperatingSystem;
import application.SpectrumThings.FTTVis;

public class WaveFile {
	public final int NOT_SPECIFIED = AudioSystem.NOT_SPECIFIED; // -1
	public final int INT_SIZE = 4;

	private int sampleSize = NOT_SPECIFIED;
	private long framesCount = NOT_SPECIFIED;
	@SuppressWarnings("unused")
	private int sampleRate = NOT_SPECIFIED;
	private int channelsNum;
	private byte[] data; // wav bytes
	private AudioInputStream ais;
	private AudioFormat af;
	
	private Clip clip;
	private boolean canPlay;

	public WaveFile(File file) throws UnsupportedAudioFileException, IOException {

		// Check if the file exists
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		File newFile;
		try {
			newFile = new File(Environment.getWavFile().getAbsolutePath());
		} catch (UnrecognizableOperatingSystem e1) {
			e1.printStackTrace();
			return;
		}
		newFile.createNewFile();

		ais = AudioSystem.getAudioInputStream(newFile);

		af = ais.getFormat();

		framesCount = ais.getFrameLength();

		sampleRate = (int) af.getSampleRate();

		sampleSize = af.getSampleSizeInBits() / 8;

		channelsNum = af.getChannels();

		long dataLength = framesCount * af.getSampleSizeInBits() * af.getChannels() / 8;

		data = new byte[(int) dataLength];
		ais.read(data);

		AudioInputStream aisForPlay = AudioSystem.getAudioInputStream(newFile);
		try {
			clip = AudioSystem.getClip();
			clip.open(aisForPlay);
			clip.setFramePosition((int) FTTVis.timestamp);
			canPlay = true;
		} catch (LineUnavailableException e) {
			canPlay = false;
			System.out.println("I can play only 8bit and 16bit music.");
		}
		clip.close();
	}

	public boolean isCanPlay() {
		return canPlay;
	}

	public AudioFormat getAudioFormat() {
		return af;
	}

	/**
	 * 
	 * @return sampleSize
	 */
	public int getSampleSize() {
		return sampleSize;
	}

	/**
	 * 
	 * @return Duration
	 */
	public double getDurationTime() {
		return getFramesCount() / getAudioFormat().getFrameRate();
	}

	/**
	 * 
	 * @return framesCount
	 */
	public long getFramesCount() {
		return framesCount;
	}

	/**
	 * Returns sample (amplitude value). Note that in case of stereo samples go one
	 * after another. I.e. 0 - first sample of left channel, 1 - first sample of the
	 * right channel, 2 - second sample of the left channel, 3 - second sample of
	 * the rigth channel, etc.
	 */
	public int getSampleInt(int sampleNumber) {

		//int sample = 0;
		
		if (sampleNumber < 0 || sampleNumber >= data.length / sampleSize) {
			throw new IllegalArgumentException("sample number can't be < 0 or >= data.length/" + sampleSize);
		}

		short[] sampleBytes = new short[4]; // 4byte = int, but they need to be shorts due to overflow issues :P
		short firstSampleByte[] = new short[2];
		short secondSampleByte[] = new short[2];
		short[] NEWsampleBytes = new short[4]; // 4byte = int
		//System.out.println(sampleSize); // Sample size  = 2
		for (int i = 0; i < sampleSize; i++) {
			sampleBytes[i] = data[sampleNumber * sampleSize * channelsNum + i];
		}
		//System.out.println("SampleBytes: " + Arrays.toString(sampleBytes));
		firstSampleByte[0] = sampleBytes[0];
		firstSampleByte[1] = sampleBytes[1];
		secondSampleByte[0] = sampleBytes[2];
		secondSampleByte[1] = sampleBytes[3];
		NEWsampleBytes[0] = secondSampleByte[0];
		NEWsampleBytes[1] = secondSampleByte[1];
		NEWsampleBytes[2] = firstSampleByte[0];
		NEWsampleBytes[3] = firstSampleByte[1];
		//System.out.println("NEW SampleBytes™: " + Arrays.toString(NEWsampleBytes));
	
		//int sample = ShortBuffer.wrap(sampleBytes).get(ByteOrder.LITTLE_ENDIAN).getInt();
		int sample = ShortBuffer.wrap(NEWsampleBytes).get(3);
		return sample;
	}
}