package application.Audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import application.Database.Environment;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class wavConverter {

	public static void convertToWAV(File file) {

		/*
		 * try { /* //Player p= Manager.createRealizedPlayer(file.toURI().toURL());
		 * DataSource ds = Manager.createDataSource(file.toURI().toURL()); ds.connect();
		 * ds.start(); System.out.println(Arrays.toString(ds.getControls()));
		 * ds.disconnect(); //System.exit(0);
		 */

		/*
		 * System.out.println(file.canRead()); InputStream in = new
		 * FileInputStream(file.getAbsolutePath()); InputStream bufferedIn = new
		 * BufferedInputStream(in); System.out.println(bufferedIn.read());
		 * AudioFileFormat inputFileFormat = AudioSystem.getAudioFileFormat(bufferedIn);
		 * AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
		 * in.close();
		 * 
		 * AudioFormat audioFormat = ais.getFormat();
		 * 
		 * System.out.println("File Format Type: " + inputFileFormat.getType());
		 * System.out.println("File Format String: " + inputFileFormat.toString());
		 * System.out.println("File lenght: " + inputFileFormat.getByteLength());
		 * System.out.println("Frame length: " + inputFileFormat.getFrameLength());
		 * System.out.println("Channels: " + audioFormat.getChannels());
		 * System.out.println("Encoding: " + audioFormat.getEncoding());
		 * System.out.println("Frame Rate: " + audioFormat.getFrameRate());
		 * System.out.println("Frame Size: " + audioFormat.getFrameSize());
		 * System.out.println("Sample Rate: " + audioFormat.getSampleRate());
		 * System.out.println("Sample size (bits): " +
		 * audioFormat.getSampleSizeInBits()); System.out.println("Big endian: " +
		 * audioFormat.isBigEndian()); System.out.println("Audio Format String: " +
		 * audioFormat.toString());
		 * 
		 * AudioInputStream encodedASI =
		 * AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, ais);
		 * 
		 * try { int i = AudioSystem.write(encodedASI, AudioFileFormat.Type.WAVE, new
		 * File("c:\\converted.wav")); System.out.println("Bytes Written: " + i); }
		 * catch (Exception e) { e.printStackTrace(); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		FFmpeg ffmpeg = null;
		FFprobe ffprobe = null;
		if (Environment.getOS() == Environment.OS.WINDOWS) {
			try {
				ffmpeg = new FFmpeg(wavConverter.class.getResource("ffmpeg/windows/bin/ffmpeg.exe").toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ffprobe = new FFprobe(
						wavConverter.class.getResource("ffmpeg/windows/bin/ffprobe.exe").toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//System.out.println(wavConverter.class.getClassLoader().getResource("ffmpeg/mac/bin/ffmpeg").getFile().toString());
			try {
				ffmpeg = new FFmpeg(wavConverter.class.getClassLoader().getResource("ffmpeg/mac/bin/ffmpeg").toURI().toURL().toString());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ffprobe = new FFprobe(wavConverter.class.getClassLoader().getResource("ffmpeg/mac/bin/ffprobe").toURI().toURL().toString());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FFmpegBuilder builder = new FFmpegBuilder().setInput(file.getAbsolutePath()).overrideOutputFiles(true)
				.addOutput(Environment.getFile().getPath().replace("vis.json", "wav.wav")).setFormat("wav").done();
		
		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

		// Run a one-pass encode
		executor.createJob(builder).run();

	}
}
