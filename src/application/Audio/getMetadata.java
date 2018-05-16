package application.Audio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp4.Mp4TagReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4Tag;

import application.UI.CoverArt;
import javafx.embed.swing.SwingFXUtils;

public class getMetadata {

	static FieldKey artist = (FieldKey.ARTIST), title = FieldKey.TITLE, art = FieldKey.COVER_ART;

	/*
	 * "Music", "*.wav", "*.m3u8", "*.flv", "*.fxm", "*.aif", "*.aiff"
	 */

	public static String[] getMp3(File file) {
		String[] returnedData = new String[2];
		MP3File metadata = null;
		try {
			metadata = new MP3File(file);
		} catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tag data = metadata.getTag();
		returnedData[0] = data.getFirst(artist).toUpperCase();
		returnedData[1] = data.getFirst(title).toUpperCase();

		if (!data.getFirst(art).isEmpty()) {
			try {
				CoverArt.setArt(SwingFXUtils.toFXImage((BufferedImage) (data.getFirstArtwork().getImage()), null));
			} catch (Exception uhhh) {
				try {
					CoverArt.setArt(
							SwingFXUtils.toFXImage((BufferedImage) (data.getArtworkList().get(0)).getImage(), null));
				} catch (IOException | IndexOutOfBoundsException e) {
					// Lol :P
				}
			}
		}

		System.out.println(data);

		return returnedData;

	}

	public static String[] getMp4(File file) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String[] returnedData = new String[2];
		Mp4TagReader metadata = new Mp4TagReader();
		Mp4Tag data = null;
		try {
			data = metadata.read(raf);
			raf.close();
		} catch (CannotReadException | IOException e) {
			e.printStackTrace();
		}

		returnedData[0] = data.getFirst(artist).toUpperCase();
		returnedData[1] = data.getFirst(title).toUpperCase();

		System.out.println(data);

		if (!data.getFirst(art).isEmpty()) {
			try {
				CoverArt.setArt(SwingFXUtils.toFXImage((BufferedImage) (data.getFirstArtwork().getImage()), null));
			} catch (Exception uhhh) {
				try {
					CoverArt.setArt(
							SwingFXUtils.toFXImage((BufferedImage) (data.getArtworkList().get(0)).getImage(), null));
				} catch (IOException | IndexOutOfBoundsException e) {
					// Lol :P
				}
			}
		}

		return returnedData;

	}

	public static void getWAV(File file) {

	}

	public static void getM3U8(File file) {

	}

	public static void getFLV(File file) {

	}

	public static void getAIF(File file) {

	}

}
