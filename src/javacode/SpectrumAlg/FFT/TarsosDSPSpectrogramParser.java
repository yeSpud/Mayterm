package javacode.SpectrumAlg.FFT;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.util.PitchConverter;
import javacode.Audio.AudioFile;
import javacode.SpectrumAlg.FFT.CustomPitchProcessor.DetectedPitchHandler;
import javacode.SpectrumAlg.FFT.CustomPitchProcessor.PitchEstimationAlgorithm;

/**
 * Look at the Spectrogram java example in the tarsos dsp library
 * @author Spud
 *
 */
public class TarsosDSPSpectrogramParser implements DetectedPitchHandler {
	public static double bar0, bar1, bar2, bar3, bar4, bar5, bar6, bar7, bar8, bar9, bar10, bar11, bar12, bar13, bar14,
			bar15, bar16, bar17, bar18, bar19, bar20, bar21, bar22, bar23, bar24, bar25, bar26, bar27, bar28, bar29,
			bar30, bar31, bar32, bar33, bar34, bar35, bar36, bar37, bar38, bar39, bar40, bar41, bar42, bar43, bar44,
			bar45, bar46, bar47, bar48;

	private static float sampleRate = 44100;
	
	private static int bufferSize = 1024 * 4;
	
	private final static int height = 480;
	
	private static Mixer currentMixer;	
	
	private static CustomAudioDispatcher dispatcher;
	
	private static PitchEstimationAlgorithm algo;
	
	private static float pitch;
	
	private static final int overlap = 768 * 4 ;
	
	private static String fileName;
	
	public TarsosDSPSpectrogramParser() {
		fileName = AudioFile.toFilePath(javacode.Audio.AudioPlayer.media.getSource());
		try {
			setNewMixer(currentMixer);
		} catch (LineUnavailableException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static CustomAudioProcessor fftProcessor = new CustomAudioProcessor() {

		be.tarsos.dsp.util.fft.FFT fft = new be.tarsos.dsp.util.fft.FFT(bufferSize);
		float[] amplitudes = new float[bufferSize / 2];

		@Override
		public boolean processFull(float[] audioFloatBuffer, byte[] audioByteBuffer) {
			processOverlapping(audioFloatBuffer, audioByteBuffer);
			return true;
		}
		
		@Override
		public boolean processOverlapping(float[] audioFloatBuffer, byte[] audioByteBuffer) {
			float[] transformbuffer = new float[bufferSize * 2];
			System.arraycopy(audioFloatBuffer, 0, transformbuffer, 0, audioFloatBuffer.length);
			fft.forwardTransform(transformbuffer);
			fft.modulus(transformbuffer, amplitudes);
			System.out.println(String.format("TDSPSP pitch value: %s\nTDSPSP bin value: %s", pitch, frequencyToBin(pitch)));
			//panel.drawFFT(pitch, amplitudes, fft);
			
			return true;
		}

		@Override
		public void processingFinished() {
			// TODO Auto-generated method stub
		}

	};
	
	
	// Has to be not static
	private void setNewMixer(Mixer mixer) throws LineUnavailableException, UnsupportedAudioFileException {

		if(dispatcher!= null){
			dispatcher.stop();
		}
		if(fileName == null){
			final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
					false);
			final DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, format);
			TargetDataLine line;
			line = (TargetDataLine) mixer.getLine(dataLineInfo);
			final int numberOfSamples = bufferSize;
			line.open(format, numberOfSamples);
			line.start();
			final AudioInputStream stream = new AudioInputStream(line);

			// create a new dispatcher
			dispatcher = new CustomAudioDispatcher(stream, bufferSize,overlap);
		} else {
			try {
				File audioFile = new File(fileName);
				dispatcher = CustomAudioDispatcher.fromFile(audioFile, bufferSize, overlap);
				AudioFormat format = AudioSystem.getAudioFileFormat(audioFile).getFormat();
				dispatcher.addAudioProcessor(new CustomBlockingAudioPlayer(format, bufferSize, overlap));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentMixer = mixer;
		
		

		// add a processor, handle pitch event.
		dispatcher.addAudioProcessor(new CustomPitchProcessor(algo, sampleRate, bufferSize, overlap, 0, this));
		dispatcher.addAudioProcessor(fftProcessor);
		
		

		// run the dispatcher (on a new thread).
		new Thread(dispatcher,"Audio dispatching").start();
	}
	
	private static int frequencyToBin(final double frequency) {
        final double minFrequency = 50; // Hz
        final double maxFrequency = 11000; // Hz
        int bin = 0;
        final boolean logaritmic = true;
        if (frequency != 0 && frequency > minFrequency && frequency < maxFrequency) {
            double binEstimate = 0;
            if (logaritmic) {
                final double minCent = PitchConverter.hertzToAbsoluteCent(minFrequency);
                final double maxCent = PitchConverter.hertzToAbsoluteCent(maxFrequency);
                final double absCent = PitchConverter.hertzToAbsoluteCent(frequency * 2);
                binEstimate = (absCent - minCent) / maxCent * height;
            } else {
                binEstimate = (frequency - minFrequency) / maxFrequency * height;
            }
            if (binEstimate > 700) {
                System.out.println(binEstimate + "");
            }
            bin = height - 1 - (int) binEstimate;
        }
        return bin;
    }
	
	@Override
	public void handlePitch(float pitch, float probability, float timeStamp,
			float progress) {
		TarsosDSPSpectrogramParser.pitch = pitch;
	}

}
