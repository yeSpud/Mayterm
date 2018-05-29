package application.Audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import application.Database.Environment;
import application.Errors.UnrecognizableOperatingSystem;
import application.SpectrumThings.SpectrumListener;

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
			clip.setFramePosition((int) SpectrumListener.timestamp);
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

	public void play() {
		clip.start();
	}

	public void stop() {
		clip.stop();
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

		if (sampleNumber < 0 || sampleNumber >= data.length / sampleSize) {
			throw new IllegalArgumentException("sample number can't be < 0 or >= data.length/" + sampleSize);
		}

		byte[] sampleBytes = new byte[4]; // 4byte = int

		for (int i = 0; i < sampleSize; i++) {
			sampleBytes[i] = data[sampleNumber * sampleSize * channelsNum + i];
		}

		int sample = ByteBuffer.wrap(sampleBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
		return sample;
	}
}