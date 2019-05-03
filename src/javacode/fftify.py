import scipy.io.wavfile as wavfile
import scipy
import scipy.fftpack
import numpy as np
from matplotlib import pyplot as plt
import sys

def process(file):
	# Load in the data from the conterted wav
	frequency_sample_rate, data = wavfile.read(file)

	# Get the duration of the file
	shape = data.shape[0]
	seconds = shape / frequency_sample_rate
	print(f"Duration of clip: {seconds}")

	time = scipy.arange(0, seconds, 1/frequency_sample_rate) # time vector as scipy arange field / numpy.ndarray

	FFT = abs(scipy.fft(data))
	FFT_side = FFT[range(shape//2)] # one side FFT range

	sample = time[1]-time[0]
	print(f"Getting sample of {sample}")

	freqs = scipy.fftpack.fftfreq(data.size, sample)
	freqs_side = freqs[range(shape//2)] # one side frequency range

	twod = np.column_stack((freqs_side,FFT_side))

	for x in range(0, len(twod), 100):
		print(f"{twod[x][0]}, {twod[x][1]}")

	plt.plot(freqs_side, abs(FFT_side), "b") # plotting the positive fft spectrum
	plt.xlabel('Frequency (Hz)')
	plt.ylabel('Count single-sided')
	plt.xlim(20, 20000)
	plt.show()

process(sys.argv[1])
