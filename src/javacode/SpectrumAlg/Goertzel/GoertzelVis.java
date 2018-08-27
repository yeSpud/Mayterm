package javacode.SpectrumAlg.Goertzel;

public class GoertzelVis {

	final static float audioSampleRate = 44100;

	final static int bufferSize = 1024 * 4;

	public static double[] queryFrequencies = new double[22000], just50 = new double[1];

	public GoertzelVis() {
		just50[0] = 50;
		for (int i = 0; i < queryFrequencies.length; i++) {
			queryFrequencies[i] = i + 1;
		}
	}

	public static void calculateFrequency(double frequency, double[] samples) {
		GoertzelFilter filter = new GoertzelFilter(frequency, audioSampleRate);
		System.out.println(filter.process(samples));
	}
}
