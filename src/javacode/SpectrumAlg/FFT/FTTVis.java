package javacode.SpectrumAlg.FFT;

import org.jtransforms.fft.FloatFFT_2D;

import javacode.Audio.AudioFile;
import javacode.Audio.AudioPlayer;
import javacode.SpectrumAlg.Goertzel.GoertzelVis;
import javacode.UI.CoverArt;
import javafx.scene.media.AudioSpectrumListener;

public class FTTVis implements AudioSpectrumListener {
	public static double timestamp;
	double previousTime = 0;
	FloatFFT_2D fft = new FloatFFT_2D(AudioPlayer.player.getAudioSpectrumNumBands(), 22000);
	float[] fftData = new float[AudioPlayer.player.getAudioSpectrumNumBands()];
	//TarsosDSPSpectrogramParser TDSPSP = new TarsosDSPSpectrogramParser();
	GoertzelVis gv = new GoertzelVis(50);
	private static boolean executeOnce = false;
	
	//newFFT fft = new newFFT();

	/**
	 * 
	 * I think I may have figured it out!<br>
	 * <br>
	 * So heres how I'm going to set up the Visualizer: Yes, it needs to be a
	 * histogram, but what are the ranges? Well There's going to be 9 major ranges
	 * (Bands), spread out among 7 lines like so: <br>
	 * Range 1 will be from 20 Hz to 50 Hz.<br>
	 * Range 2 will be from 50 Hz to 100 Hz.<br>
	 * Range 3 will be from 100 Hz to 200 Hz.<br>
	 * Range 4 will be from 200 Hz to 500 Hz.<br>
	 * Range 5 will be from 500 Hz to 1,000 Hz.<br>
	 * Range 6 will be from 1,000 Hz to 2,000 Hz.<br>
	 * Range 7 will be from 2,000 Hz to 5,000 Hz.<br>
	 * Range 8 will be from 5,000 Hz to 10,000 Hz.<br>
	 * And range 9 will be from 10,000 Hz to 20,000 Hz.<br>
	 * <br>
	 * 
	 * If I need a refresher, just take a look at the EQ in garage band
	 * 
	 */
	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		// TODO: Redo spectrum
		//FTTVis.timestamp = timestamp;

		//if (timestamp - previousTime > 1) {
			// System.out.println(oldFFT.getAmplitude((int) timestamp));
			// previousTime = timestamp;
		//}
		
		if (timestamp > 16.00d && timestamp < 16.07d) {
			String src = AudioFile.toFilePath(AudioPlayer.media.getSource());
			CoverArt.setArt(src);
		}

		if ((AudioPlayer.media.getDuration().toSeconds() - timestamp) < 35.0d) {
			CoverArt.setArt(null);
		}
		
		double[] samples = new double[magnitudes.length];
		for (int i = 0; i < samples.length; i++) {
			
			samples[i] = magnitudes[i];
			
		}
		
		GoertzelVis.calculateFrequency(50, samples);
		
		if  (!executeOnce) {
			
		}
		
		
		
		/*
		
		fftData = magnitudes;
		
		fft.realForwardFull(fftData);
		
		for (int i = 0; i < fftData.length; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(62 - i);
			float height = ((63 - (fftData[i]) * -1) * 4);
			if (height != bar.getHeight()) {
				bar.setHeight(height);
				try {
					Text text = (Text) SpectrumDebug.spectrumText.getChildren().get(i);
					text.setText(String.valueOf(String.format("%.2f", bar.getHeight())));
					text.setX(bar.getX());
					text.setY(bar.getY() - 15);
				} catch (IndexOutOfBoundsException foo) {
					// Do nothing
				}
			}
		}
		*/

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

	}

}
