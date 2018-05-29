package application.SpectrumThings;

import application.Audio.AudioPlayer;
import application.UI.CoverArt;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SpectrumListener implements AudioSpectrumListener {
	public static double timestamp;
	double previousTime = 0;

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		// TODO: Redo spectrum
		SpectrumListener.timestamp = timestamp;
		if (timestamp < .07d) {
			CoverArt.autoSetArt(AudioPlayer.media.getSource());
		}

		for (int i = 0; i < magnitudes.length; i++) { // 7
			Text text = (Text) SpectrumDebug.spectrumText.getChildren().get(i);
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(62 - i);
			bar.setHeight((63 - magnitudes[i] * -1) * 4);
			text.setText(String.valueOf(String.format("%.2f", bar.getHeight())));
			text.setX(bar.getX());
			text.setY(bar.getY() - 15);
		}

		if (timestamp - previousTime > 1) {
			System.out.println(FFT.getAmplitude((int) timestamp));
			previousTime = timestamp;
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
		 * I think I may have figured it out! So heres how Im going to set up the
		 * visulalizer: Yes, it needs to be a histogram, but what are the ranges? Well
		 * theres going to be 9 major rangesc (Bands), spread out among 7 lines like so:
		 * 
		 * Range 1 will be from 20 Hz to 50 Hz. Range 2 will be from 50 Hz to 100 Hz.
		 * Range 3 will be from 100 Hz to 200 Hz. Range 4 will be from 200 Hz to 500 Hz.
		 * Range 5 will be from 500 Hz to 1,000 Hz. Range 6 will be from 1,000 Hz to
		 * 2,000 Hz. Range 7 will be from 2,000 Hz to 5,000 Hz. Range 8 will be from
		 * 5,000 Hz to 10,000 Hz. And range 9 will be from 10,000 Hz to 20,000 Hz
		 * 
		 * If I need a refresheer, just take a look at the EQ in garage band
		 * 
		 */

	}

}
