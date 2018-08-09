package javacode.Audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javacode.Database.Environment;
import javacode.Errors.UnrecognizableOperatingSystem;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

/**
 * Ever wondered why FFMPEG is a large part of this project? This is why.<br>
 * 
 * This class's sole purpose is to convert the given file to a .wav file for
 * spectrum analysis.
 * 
 * @author Spud
 *
 */
public class wavConverter {

	/**
	 * Converts a provided file to a .wav file, stored either in the %Appdata%
	 * folder (Windows), the the application support folder (macOS), or .Spud
	 * (Linux)
	 * 
	 * @param file
	 *            - The file to convert
	 */
	public static void convertToWAV(File file) {

		FFmpeg ffmpeg = null;
		FFprobe ffprobe = null;
		try {
			if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
				try {
					ffmpeg = new FFmpeg(wavConverter.class.getClassLoader().getResource("ffmpeg/windows/bin/ffmpeg.exe")
							.toURI().toURL().toString().replace("file:", ""));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					return;
				}
				try {
					ffprobe = new FFprobe(
							wavConverter.class.getClassLoader().getResource("ffmpeg/windows/bin/ffprobe.exe").toURI()
									.toURL().toString().replace("file:", ""));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					return;
				}
			} else {
				try {
					ffmpeg = new FFmpeg(wavConverter.class.getClassLoader().getResource("ffmpeg/mac/bin/ffmpeg").toURI()
							.toURL().toString().replace("file:", ""));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					return;
				}
				try {
					ffprobe = new FFprobe(wavConverter.class.getClassLoader().getResource("ffmpeg/mac/bin/ffprobe")
							.toURI().toURL().toString().replace("file:", ""));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					return;
				}
			}
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
			return;
		}

		FFmpegBuilder builder;
		try {
			builder = new FFmpegBuilder().setInput(file.getAbsolutePath()).overrideOutputFiles(true)
					.addOutput(Environment.getWavFile().getAbsolutePath()).setFormat("wav").done();
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
			return;
		}

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		// Run a one-pass encode
		executor.createJob(builder).run();

	}
}
