package application.SpectrumThings;

import javafx.scene.media.AudioSpectrumListener;

public class FTTVis implements AudioSpectrumListener {
	public static double timestamp;
	double previousTime = 0;
	
	newFFT fft = new newFFT();
	
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		// TODO: Redo spectrum
		FTTVis.timestamp = timestamp;
		

		if (timestamp - previousTime > 1) {
			//System.out.println(oldFFT.getAmplitude((int) timestamp));
			//previousTime = timestamp;
		}

		

		// System.out.println(StackOverflow.calculate());

		/*
		 * AudioProcessor fftProcessor = new AudioProcessor() {
		 * 
		 * FFT fft = new FFT(4096); float[] amplitudes = new float[4096/2];
		 * 
		 * @Override public boolean process(AudioEvent audioEvent) { return false; }
		 * 
		 * @Override public void processingFinished() { float[] audioFloatBuffer =
		 * audioEvent.getFloatBuffer(); float[] transformbuffer = new float[4096*2];
		 * System.arraycopy(audioFloatBuffer, 0, transformbuffer, 0,
		 * audioFloatBuffer.length); fft.forwardTransform(transformbuffer);
		 * fft.modulus(transformbuffer, amplitudes); }
		 * 
		 * };
		 */

		// System.out.println(wav.getSampleInt((int)timestamp)/timestamp);

		/*
		 * 
		 * I think I may have figured it out! So heres how I'm going to set up the
		 * Visualizer: Yes, it needs to be a histogram, but what are the ranges? Well
		 * There's going to be 9 major ranges (Bands), spread out among 7 lines like so:
		 * 
		 * Range 1 will be from 20 Hz to 50 Hz. Range 2 will be from 50 Hz to 100 Hz.
		 * Range 3 will be from 100 Hz to 200 Hz. Range 4 will be from 200 Hz to 500 Hz.
		 * Range 5 will be from 500 Hz to 1,000 Hz. Range 6 will be from 1,000 Hz to
		 * 2,000 Hz. Range 7 will be from 2,000 Hz to 5,000 Hz. Range 8 will be from
		 * 5,000 Hz to 10,000 Hz. And range 9 will be from 10,000 Hz to 20,000 Hz
		 * 
		 * If I need a refresher, just take a look at the EQ in garage band
		 * 
		 */

	}

}
