package javacode.SpectrumAlg.Goertzel;

/**
 * The Goertzel filter implementation in pure Java. It is designed with speed
 * and portability in mind. This provides both the static methods for "pure
 * Goertzel calculations" and regular methods for representing a Goertzel filter
 * with an object of this class.
 */
public class GoertzelFilter {
	/** Helper class to hold previous two results. */
	public static class Vn {
		/** Previous result */
		public static double _1;
		/** Result before the previous */
		public static double _2;
	}

	/** The frequency of the filter */
	static double  f_;
	/** The sampling frequency */
	static double fs_;
	/** The Goertzel coefficient, calculate from frequencies */
	static double koef_;
	/** Past results of the Goertzel recursive formula */
	static Vn vn_;

	/**
	 * Constructor takes the frequency of the filter and the sampling frequency.
	 */
	public GoertzelFilter(double f, double fs) {
		f_ = f;
		fs_ = fs;
		koef_ = calc_koef(f, fs);
		vn_ = new Vn();
		reset();
	}

	/** Resets the filter for a new calculation */
	public static void reset() {
		Vn._1 = Vn._2 = 0.0;
	}

	/** Returns the frequency of the filter */
	public static  double get_f() {
		return f_;
	}

	/** Anything less than this is meaningless */
	static final double EPSILON = 0.000000001;

	/**
	 * The "kernel" of the Goertzel recursive calculation.
	 * 
	 * @param sample Array of samples to pass through the filter
	 * @param k      The Goertzel coefficient
	 * @param vn     Previous (two) results - on output, the new two results
	 */
	static void kernel(double[] sample, double k, Vn vn) {
		for (double x : sample) {
			double t = k * Vn._1 - Vn._2 + x;
			Vn._2 = Vn._1;
			Vn._1 = t;
		}
	}

	/**
	 * Calculate the power of the signal that was passed through the filter.
	 * 
	 * @param koef The Goertzel coefficient
	 * @paarm vn Previous (two) results
	 * @param n The number of samples that have passed through the filter
	 */
	static double power(double koef, Vn vn, int n) {
		double rslt = Vn._1 * Vn._1 + Vn._2 * Vn._2 - koef * Vn._1 * Vn._2;
		if (rslt < EPSILON) {
			rslt = EPSILON;
		}
		return rslt / (n * n);
	}

	/**
	 * The 'dBm', or 'dBmW' - decibel-milliwatts, a power ratio in dB (decibels) of
	 * the (given) measured power referenced to one (1) milliwat (mW). This uses the
	 * audio/telephony usual 600 Ohm impedance.
	 */
	public static double dBm(double power) {
		return 10 * java.lang.Math.log10(2 * power * 1000 / 600.0);
	}

	/**
	 * Calculates the Goertzel coefficient for the given frequency of the filter and
	 * the sampling frequency.
	 */
	static double calc_koef(double f, double fs) {
		return 2 * java.lang.Math.cos(2 * java.lang.Math.PI * f / fs);
	}

	/**
	 * Process the given array of samples on this filter.
	 * 
	 * @return The current power of the signal passed through the filter (from the
	 *         start).
	 */
	public static double process(double[] samples) {
		kernel(samples, koef_, vn_);
		return power(koef_, vn_, samples.length);
	}
}
