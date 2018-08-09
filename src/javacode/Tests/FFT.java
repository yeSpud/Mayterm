package javacode.Tests;

// https://pdfs.semanticscholar.org/d65a/94f0da0fe6059fa8bcd24f85cfe999531192.pdf

public class FFT {
	public static void fastFFT(double[] fdata, int N, boolean fwd) {
		double omega, tempr, tempi, fscale;
		double xtemp, cos, sin, xr, xi;
		int i, j, k, n, m, M;
		j = 0;
		for (i = 0; i < N - 1; i++) {
			if (i < j) {
				tempr = fdata[2 * i];
				tempi = fdata[2 * i + 1];
				fdata[2 * i] = fdata[2 * j];
				fdata[2 * i + 1] = fdata[2 * j + 1];
				fdata[2 * j] = tempr;
				fdata[2 * j + 1] = tempi;
			}
			k = N / 2;
			while (k <= j) {
				j -= k;
				k >>= 1;
			}
			j += k;
		}
		if (fwd) {
			fscale = 1.0;
		} else {
			fscale = -1.0;
		}
		M = 2;
		while (M < 2 * N) {
			omega = fscale * 2.0 * Math.PI / M;
			sin = Math.sin(omega);
			cos = Math.cos(omega) - 1.0;
			xr = 1.0;
			xi = 0.0;
			for (m = 0; m < M - 1; m += 2) {
				for (i = m; i < 2 * N; i += M * 2) {
					j = i + m;
					tempr = xr * fdata[j] - xi * fdata[j + 1];
					tempi = xr * fdata[j + 1] + xi * fdata[j];
					fdata[j] = fdata[i] - tempr;
					fdata[j + 1] = fdata[i + 1] - tempi;
					fdata[i] += tempr;
					fdata[i + 1] += tempi;
				}
				xtemp = xr;
				xr = xr + xr * cos - xi * sin;
				xi = xi + xtemp * sin + xi * cos;
			}
			M *= 2;
		}
		if (fwd) {
			for (k = 0; k < N; k++) {
				fdata[2 * k] /= N;
				fdata[2 * k + 1] /= N;
			}
		}
	}
}
