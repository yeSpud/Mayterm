import sys


def find_fft(file):
    import numpy as np
    import scipy.fftpack
    import scipy.io.wavfile as wav

    # Load in the data from the converted wav
    frequency_sample_rate, data = wav.read(file)
    # print(f"Frequency sample rate: {frequency_sample_rate}")

    # Get the duration of the file
    shape = data.shape[0]
    # seconds = shape / frequency_sample_rate
    # print(f"Duration of clip: {seconds}")

    time = scipy.arange(0, (shape / frequency_sample_rate),
                        1 / frequency_sample_rate)  # time vector as scipy arange field / numpy.ndarray

    # fft = abs(scipy.fft(data))
    # fft_side = abs(scipy.fft(data))[range(shape // 2)]  # one side FFT range

    # sample = time[1] - time[0]
    # print(f"Getting sample of {sample}")

    # freq = scipy.fftpack.fftfreq(data.size, (time[1] - time[0]))
    # freq_side = (scipy.fftpack.fftfreq(data.size, (time[1] - time[0])))[range(shape // 2)]  # one side frequency range

    array = np.column_stack((((scipy.fftpack.fftfreq(data.size, (time[1] - time[0])))[range(shape // 2)]), (abs(scipy.fft(data))[range(shape // 2)])))
    test_array = []
    for x in range(0, len(array), 100):
        # print(f"{array[x][0]}, {array[x][1]}")
        test_array.append(array[x][1])

    # print(type(np.asarray(finalarray)))
    # print(type(fft_side))
    他妈的 = np.asarray(test_array)

    # plt.plot(freq_side, abs(fft_side), "b")  # plotting the positive fft spectrum
    # plt.xlabel('Frequency (Hz)')
    # plt.ylabel('Count single-sided')
    # plt.xlim(20, 20000)
    # plt.show()

    # Bars 0 - 6 will get between 20 and 50 hz
    # Bars 7 - 13 will get between 50 and 100 hz
    # Bars 14 - 20 will get between 100 and 200 hz
    # Bars 21 - 27 will get between 200 and 500 hz
    # Bars 28 - 34 will get between 500 and 1 khz
    # Bars 35 - 41 will get between 1 and 2 khz
    # Bars 42 - 48 will get between 2 and 5khz
    # Bars 49 - 55 will get between 5 and 10hkz
    # Bars 56 - 63 will get between 10 and 20 khz

    # print(f"20: {fft_side[20]}")
    # a = generate_bar_size(他妈的, 20, 49)
    # print(a)

    # b = generate_bar_size(他妈的, 50, 99)
    # print(b)

    # c = generate_bar_size(他妈的, 100, 199)

    # d = generate_bar_size(他妈的, 200, 499)

    # e = generate_bar_size(他妈的, 500, 999)

    # f = generate_bar_size(他妈的, 1000, 1999)

    # g = generate_bar_size(他妈的, 2000, 4999)

    # h = generate_bar_size(他妈的, 5000, 9999)

    # i = generate_bar_size(他妈的, 10000, 19999)

    print(generate_bar_size(他妈的, 20, 49) + generate_bar_size(他妈的, 50, 99) + generate_bar_size(他妈的, 100, 199)
          + generate_bar_size(他妈的, 200, 499) + generate_bar_size(他妈的, 500, 999) + generate_bar_size(他妈的, 1000, 1999)
          + generate_bar_size(他妈的, 2000, 4999) + generate_bar_size(他妈的, 5000, 9999) + generate_bar_size(他妈的, 10000,
                                                                                                        19999))

    # Time to beat: 2.3775219917297363


def breakup_file(actualFile):
    '''
    This function should take the actualFile provided, break it up into smaller clips, and then pass those clips to
    process to get the FFT of that clip. The actual file can be broken up by abusing pydub's AudioSegment
    '''
    from pydub import AudioSegment
    from pydub.utils import make_chunks

    full_file = AudioSegment.from_wav(actualFile)

    # Since I want every 1/60th second, ill pass 17 milliseconds (since pydub works in milliseconds)
    rate = 17

    chunks = make_chunks(full_file, rate)

    # For now, export each clip
    for i, chunk in enumerate(chunks):
        chunk_name = "chunk{0}.wav".format(i)
        print(f"exporting {chunk_name}")
        chunk.export(chunk_name, format='wav')


def 总数(L, a, b):
    s = 0
    for i in range(a, b + 1, 1):
        s += L[i]
    return s


def generate_bar_size(fft, start_range, end_range):
    from math import floor
    delta = floor((end_range - start_range) / 7)
    # print(f"Delta: {delta}\nStart range: {start_range}\nEnd range: {end_range}")
    l = [0.0] * 7
    for index in range(len(l)):
        # start = start_range + (index * delta)
        # end = (start_range + delta - 1) + (index * delta)
        # print(f"Getting values between the index of {start} and {end}")
        l[index] = 总数(abs(fft), (start_range + (index * delta)),
                      ((start_range + delta - 1) + (index * delta))) / delta

    return l


# Determine CLI arguments
if len(sys.argv) != 2:
    print("Usage: python3 fftify.py <file.wav>")
else:
    import time

    start_time = time.time()
    find_fft(sys.argv[1])
    end_time = time.time()
    import psutil, os
    process = psutil.Process(os.getpid())
    print(f"Total execution time: {end_time - start_time}")
    print(process.memory_info().rss)  # in bytes
