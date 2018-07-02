package application.Tests;

import application.Core.Audio.AudioPlayer;

// Ripped directly from: https://github.com/caseif/vis.js/blob/4beea1390a3dae879610a7d9f7c283b575cfc4b2/js/analysis/spectrum_algorithms.js
@Deprecated
public class spectrum_algorithms {

	/* *************************** */
	/* * Basic spectrum settings * */
	/* *************************** */
	// BASIC ATTRIBUTES
	static int spectrumSize = 63; // number of bars in the spectrum
	static double spectrumDimensionScalar = 4.5f; // the ratio of the spectrum width to its height
	double spectrumSpacing = 3.5d; // the separation of each spectrum bar in pixels at width=1280
	int maxFftSize = 16384; // the preferred fftSize to use for the audio node (actual fftSize may be lower)
	double audioDelay = 0.4d; // audio will lag behind the rendered spectrum by this amount of time (in
								// seconds)
	// BASIC TRANSFORMATION
	static int spectrumStart = 4; // the first bin rendered in the spectrum
	static int spectrumEnd = 1200; // the last bin rendered in the spectrum
	static double spectrumScale = 2.5d; // the logarithmic scale to adjust spectrum values to
	// EXPONENTIAL TRANSFORMATION
	static int spectrumMaxExponent = 6; // the max exponent to raise spectrum values to
	static int spectrumMinExponent = 3; // the min exponent to raise spectrum values to
	static int spectrumExponentScale = 2; // the scale for spectrum exponents
	// DROP SHADOW
	int spectrumShadowBlur = 6; // the blur radius of the spectrum's drop shadow
	int spectrumShadowOffsetX = 0; // the x-offset of the spectrum's drop shadow
	int spectrumShadowOffsetY = 0; // the y-offset of the spectrum's drop shadow

	/* ********************** */
	/* * Smoothing settings * */
	/* ********************** */
	static int smoothingPoints = 3; // points to use for algorithmic smoothing. Must be an odd number.
	static int smoothingPasses = 1; // number of smoothing passes to execute
	double temporalSmoothing = 0.2d; // passed directly to the JS analyzer node

	/* ************************************ */
	/* * Spectrum margin dropoff settings * */
	/* ************************************ */
	static int headMargin = 7; // the size of the head margin dropoff zone
	static int tailMargin = 0; // the size of the tail margin dropoff zone
	static double minMarginWeight = 0.7d; // the minimum weight applied to bars in the dropoff zone

	static double spectrumHeight = 2 / spectrumDimensionScalar;
	static double value = 0;
	
	static double marginDecay = 1.6; // I admittedly forget how this works but it probably shouldn't be changed from 1.6
	// margin weighting follows a polynomial slope passing through (0, minMarginWeight) and (marginSize, 1)
	static double headMarginSlope = (1 - minMarginWeight) / Math.pow(headMargin, marginDecay);
	static double tailMarginSlope = (1 - minMarginWeight) / Math.pow(tailMargin, marginDecay);
	
	public static double[] smooth(double[] array) {
	    return savitskyGolaySmooth(array);
	}
	
	public static double[] savitskyGolaySmooth(double[] array) {
		double lastArray[] = array;
		double newArr[] = new double[63];
		for (int pass = 0; pass < smoothingPasses; pass++) {
			double sidePoints = Math.floor(smoothingPoints / 2); // our window is centered so this is both nL and nR
			double cn = 1 / (2 * sidePoints + 1); // constant
			for (int i = 0; i < sidePoints; i++) {
				newArr[i] = lastArray[i];
				newArr[lastArray.length - i - 1] = lastArray[lastArray.length - i - 1];
			}
			for (int i = (int) sidePoints; i < lastArray.length - sidePoints; i++) {
				double sum = 0.0d;
				for (int n = (int) -sidePoints; n <= sidePoints; n++) {
					sum += cn * lastArray[i + n] + n;
				}
				newArr[i] = sum;
			}
			lastArray = newArr;
		}
		return newArr;
	}

	public static double[] transformToVisualBins(double[] array) {
		double newArray[] = new double[spectrumSize];
		for (int i = 0; i < spectrumSize; i++) {
			double bin = Math.pow(i / spectrumSize, spectrumScale) * (spectrumEnd - spectrumStart) + spectrumStart;
			newArray[i] = array[(int)(Math.floor(bin) + spectrumStart)] * (bin % 1) + array[(int)(Math.floor(bin + 1) + spectrumStart)] * (1 - (bin % 1));
		}
		return newArray;
	}
	
	public static double[] getTransformedSpectrum(double[] array) {
	    double newArr[] = normalizeAmplitude(array);
	    newArr = averageTransform(newArr);
	    newArr = tailTransform(newArr);
	    newArr = smooth(newArr);
	    newArr = exponentialTransform(newArr);
	    return newArr;
	}
	
	public static double[] normalizeAmplitude(double[] array) {
	    double values[] = new double[63];
	    for (int i = 0; i < spectrumSize; i++) {
	        if (AudioPlayer.isPlaying) {
	            values[i] = array[i] / 255 * spectrumHeight;
	        } else {
	            value = 1;
	        }
	    }
	    return values;
	}

	public static double[] averageTransform(double[] array) {
	    double values[] = new double[63];
	    int length = array.length;

	    for (int i = 0; i < length; i++) {
	        value = 0;
	        if (i == 0) {
	            value = array[i];
	        } else if (i == length - 1) {
	            value = (array[i - 1] + array[i]) / 2;
	        } else {
	            double prevValue = array[i - 1];
	            double curValue = array[i];
	            double nextValue = array[i + 1];

	            if (curValue >= prevValue && curValue >= nextValue) {
	              value = curValue;
	            } else {
	              value = (curValue + Math.max(nextValue, prevValue)) / 2;
	            }
	        }
	        value = Math.min(value + 1, spectrumHeight);

	        values[i] = value;
	    }

	    double newValues[] = new double[63];
	    for (int i = 0; i < length; i++) {
	        value = 0;
	        if (i == 0) {
	            value = values[i];
	        } else if (i == length - 1) {
	            value = (values[i - 1] + values[i]) / 2;
	        } else {
	            double prevValue = values[i - 1];
	            double curValue = values[i];
	            double nextValue = values[i + 1];

	            if (curValue >= prevValue && curValue >= nextValue) {
	              value = curValue;
	            } else {
	              value = ((curValue / 2) + (Math.max(nextValue, prevValue) / 3) + (Math.min(nextValue, prevValue) / 6));
	            }
	        }
	        value = Math.min(value + 1, spectrumHeight);

	        newValues[i] = value;
	    }
	    return newValues;
	}

	public static double[] tailTransform(double[] array) {
	    double values[] = new double[63];
	    for (int i = 0; i < spectrumSize; i++) {
	        value = array[i];
	        if (i < headMargin) {
	            value *= headMarginSlope * Math.pow(i + 1, marginDecay) + minMarginWeight;
	        } else if (spectrumSize - i <= tailMargin) {
	            value *= tailMarginSlope * Math.pow(spectrumSize - i, marginDecay) + minMarginWeight;
	        }
	        values[i] = value;
	    }
	    return values;
	}

	public static double[] exponentialTransform(double[] array) {
	    double newArr[] = new double[63];
	    for (int i = 0; i < array.length; i++) {
	        double exp = (spectrumMaxExponent - spectrumMinExponent) * (1 - Math.pow(i / spectrumSize, spectrumExponentScale)) + spectrumMinExponent;
	        newArr[i] = Math.max(Math.pow(array[i] / spectrumHeight, exp) * spectrumHeight, 1);
	    }
	    return newArr;
	}

	// top secret bleeding-edge shit in here
	public static double[] experimentalTransform(double[] array) {
	    int resistance = 3; // magic constant
	    double newArr[] = new double[63];
	    for (int i = 0; i < array.length; i++) {
	        int sum = 0;
	        int divisor = 0;
	        for (int j = 0; j < array.length; j++) {
	            int dist = Math.abs(i - j);
	            double weight = 1 / Math.pow(2, dist);
	            if (weight == 1) weight = resistance;
	            sum += array[j] * weight;
	            divisor += weight;
	        }
	        newArr[i] = sum / divisor;
	    }
	    return newArr;
	}
	
}
