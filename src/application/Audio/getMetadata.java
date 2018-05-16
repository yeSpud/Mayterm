package application.Audio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.jaudiotagger.audio.aiff.AiffFileReader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp4.Mp4TagReader;
import org.jaudiotagger.audio.wav.WavFileReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.aiff.AiffTag;
import org.jaudiotagger.tag.mp4.Mp4Tag;

import application.UI.CoverArt;
import javafx.embed.swing.SwingFXUtils;

public class getMetadata {

	static FieldKey artist = (FieldKey.ARTIST), title = FieldKey.TITLE, art = FieldKey.COVER_ART;

	public static String[] getMp3(File file) {
		String[] returnedData = new String[2];
		MP3File metadata = null;
		try {
			metadata = new MP3File(file);
		} catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
			return returnedData;
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
		String[] returnedData = new String[2];
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return returnedData;
		}
		Mp4TagReader metadata = new Mp4TagReader();
		Mp4Tag data = null;
		try {
			data = metadata.read(raf);
			raf.close();
		} catch (CannotReadException | IOException e) {
			e.printStackTrace();
			return returnedData;
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

	public static String[] getWAV(File file) {
		String[] returnedData = new String[2];
		WavFileReader metadata = new WavFileReader();
		Tag data = null;
		try {
			data = metadata.read(file).getTag();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
			return returnedData;
		}
		
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
		
		return returnedData;
	}

	public static String[] getAIF(File file) {
		String[] returnedData = new String[2];
		AiffFileReader metadata = new AiffFileReader();
		AiffTag data = null;
		try {
			data = (AiffTag) metadata.read(file).getTag();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
			return returnedData;
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

}
