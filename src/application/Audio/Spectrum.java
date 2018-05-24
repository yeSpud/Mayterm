package application.Audio;

import java.util.Arrays;

import application.UI.CoverArt;
import application.UI.DisplayText;
import application.UI.VisulizerDisplay;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.util.fft.FFT;
import javafx.scene.Group;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Spectrum {

	public static Group spectrum = new Group();

	public static void createSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle rectangle = new Rectangle(115 + (i * (16.65)), 356, 12, 2); // 1.9453968254
			rectangle.setStrokeType(StrokeType.CENTERED);
			spectrum.getChildren().add(rectangle);
		}
	}

	public static void clearSpectrum() {
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) Spectrum.spectrum.getChildren().get(i);
			bar.setHeight(12);
		}
	}

	public static void disableSpectrum(boolean retry) {
		VisulizerDisplay.root.getChildren().remove(Spectrum.spectrum);
		VisulizerDisplay.root.getChildren().add(VisulizerDisplay.nothing);
		if (!retry) {
			DisplayText.setArtist("No file currently selected");
			DisplayText.setTitle("Press \"O\" to select a file");
		}
		CoverArt.setArt(null);
	}

	public static void enableSpectrum() {
		VisulizerDisplay.root.getChildren().remove(VisulizerDisplay.nothing);
		VisulizerDisplay.root.getChildren().add(Spectrum.spectrum);
	}

	public static void setupSpectrumMovement() {
		/*
		AudioEqualizer EQ = AudioPlayer.player.getAudioEqualizer();
		EQ.getBands().clear();
		// Note to self, gain is gain in volume, not what the current decible level is
		double startHz = 20;
		for (int i = 0; i < 7; i++) {
			System.out.println(startHz);
			EqualizerBand EQB = new EqualizerBand(startHz + ((4.29d)/2), 4.29d, 0);
			startHz = (EQB.getCenterFrequency() + (4.29d));
			EQ.getBands().add(EQB);
		}
		*/
		
		//EQB1.setBandwidth(4.29d);
		//EQB1.setCenterFrequency(22.145d);
		//EQB1.setGain(60);
		
		/*
		EqualizerBand EQB2 = new EqualizerBand();
		EQB2.setBandwidth(4.29);
		EQB2.setCenterFrequency(26.435);
		
		EqualizerBand EQB3 = new EqualizerBand();
		EQB3.setBandwidth(4.29);
		EQB3.setCenterFrequency(30.725);
		
		
		
		EQ.getBands().add(EQB1);
		EQ.getBands().add(EQB2);
		EQ.getBands().add(EQB3);
		EQ.setEnabled(true);
	*/
		AudioPlayer.player.setAudioSpectrumNumBands(63); // 63
		AudioPlayer.player.setAudioSpectrumInterval(0.033d); // 0.0167
		AudioPlayer.player.setAudioSpectrumThreshold(-60);
		//AudioPlayer.player.setAudioSpectrumListener(new SpectrumListener(12000));
		AudioPlayer.player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				// TODO: Redo spectrum
				
				if (timestamp < .07d) {
					CoverArt.autoSetArt(AudioPlayer.media.getSource());
				}
				
				for (int i = 0; i < 63; i++) { // 7
					Rectangle bar = (Rectangle) spectrum.getChildren().get(62 - i);
					bar.setHeight((63 - magnitudes[i] * -1) * 4);
				}
				/*
				AudioProcessor fftProcessor = new AudioProcessor() {

					FFT fft = new FFT(4096);
					float[] amplitudes = new float[4096/2];
					
					@Override
					public boolean process(AudioEvent audioEvent) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void processingFinished() {
						// TODO Auto-generated method stub
						float[] audioFloatBuffer = audioEvent.getFloatBuffer();
						float[] transformbuffer = new float[4096*2];
						System.arraycopy(audioFloatBuffer, 0, transformbuffer, 0, audioFloatBuffer.length); 
						fft.forwardTransform(transformbuffer);
						fft.modulus(transformbuffer, amplitudes);
					}
					
				};
				*/
				
				/*
				 * 
				 * I think I may have figured it out! So heres how Im going to set up the visulalizer:
				 * Yes, it needs to be a histogram, but what are the ranges?
				 * Well theres going to be 9 major rangesc (Bands), spread out among 7 lines like so:
				 * 
				 * Range 1 will be from 20 Hz to 50 Hz.
				 * Range 2 will be from 50 Hz to 100 Hz.
				 * Range 3 will be from 100 Hz to 200 Hz.
				 * Range 4 will be from 200 Hz to 500 Hz.
				 * Range 5 will be from 500 Hz to 1,000 Hz.
				 * Range 6 will be from 1,000 Hz to 2,000 Hz.
				 * Range 7 will be from 2,000 Hz to 5,000 Hz.
				 * Range 8 will be from 5,000 Hz to 10,000 Hz.
				 * And range 9 will be from 10,000 Hz to 20,000 Hz
				 * 
				 * If I need a refresheer, just take a look at the EQ in garage band
				 * 
				 */


			}

		});
	}

}
