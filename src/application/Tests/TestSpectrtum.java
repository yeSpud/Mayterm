package application.Tests;

import java.util.Arrays;

@Deprecated
public class TestSpectrtum {

	public static void foo(double[] fooArray) {
		double initialArray[] = new double[63];
		initialArray = fooArray;
		double array[] = spectrum_algorithms.transformToVisualBins(initialArray);
		
		array = spectrum_algorithms.getTransformedSpectrum(array);
		System.out.println(Arrays.toString(array));
	}
	
}
