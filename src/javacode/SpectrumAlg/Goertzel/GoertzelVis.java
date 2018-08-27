package javacode.SpectrumAlg.Goertzel;

public class GoertzelVis {

	final static float audioSampleRate = 44100;

	public static double[] queryFrequencies = new double[22000], just50 = new double[1];

	public static GoertzelFilter filter50;
	public static GoertzelFilter filter200;

	public GoertzelVis(double frequency) {
		just50[0] = 50;
		for (int i = 0; i < queryFrequencies.length; i++) {
			queryFrequencies[i] = i + 1;
		}
		filter50 = new GoertzelFilter(frequency, audioSampleRate);
		filter200 = new GoertzelFilter(frequency, audioSampleRate);
	}

	public static void calculateFrequency(double frequency, double[] samples) {
		System.out.println(String.format("50hz frequency: %s\n200hz frequency: %s\n", filter50.process(samples),
				filter200.process(samples)));
	}
}
