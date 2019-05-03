import sys

import numpy as np
import scipy
import scipy.fftpack
import scipy.io.wavfile as wav
from matplotlib import pyplot as plt


def process(file):
    # Load in the data from the converted wav
    frequency_sample_rate, data = wav.read(file)

    # Get the duration of the file
    shape = data.shape[0]
    seconds = shape / frequency_sample_rate
    print(f"Duration of clip: {seconds}")

    time = scipy.arange(0, seconds, 1 / frequency_sample_rate)  # time vector as scipy arange field / numpy.ndarray

    fft = abs(scipy.fft(data))
    fft_side = fft[range(shape // 2)]  # one side FFT range

    sample = time[1] - time[0]
    print(f"Getting sample of {sample}")

    freq = scipy.fftpack.fftfreq(data.size, sample)
    freq_side = freq[range(shape // 2)]  # one side frequency range

    array = np.column_stack((freq_side, fft_side))

    for x in range(0, len(array), 100):
        print(f"{array[x][0]}, {array[x][1]}")

    plt.plot(freq_side, abs(fft_side), "b")  # plotting the positive fft spectrum
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Count single-sided')
    plt.xlim(20, 20000)
    plt.show()


# Determine CLI arguments
if len(sys.argv) != 2:
    print("Usage: python3 fftify.py <file.wav>")
else:
    process(sys.argv[1])
