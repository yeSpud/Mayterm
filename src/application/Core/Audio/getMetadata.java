package application.Core.Audio;

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

import application.Core.Errors.UnrecognizableFileType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Class for handling the metadata of different media file formats. This is what
 * sets the title, artist, and even the source of the cover art.
 * 
 * Supported formats are: mp3, mp4, and wav.<br>
 * 
 * @author Spud
 *
 */
public class getMetadata {

	static FieldKey artist = (FieldKey.ARTIST), title = FieldKey.TITLE, art = FieldKey.COVER_ART;

	/**
	 * Returns a string array of 2. The first argument is the artist, the second is
	 * the title. Oh, and it also tries to set the album art.
	 * 
	 * @param file
	 *            The file location, or path. This needs to be formatted in a way
	 *            that is URL friendly!
	 * @return String[] - Returns a string array, containing the artist (0), and the
	 *         title (1).
	 */
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
		returnedData[0] = data.getFirst(artist);
		returnedData[1] = data.getFirst(title);
		
		return returnedData;
	}

	/**
	 * Returns a string array of 2. The first argument is the artist, the second is
	 * the title. Oh, and it also tries to set the album art.
	 * 
	 * @param file
	 *            The file location, or path. This needs to be formatted in a way
	 *            that is URL friendly!
	 * @return String[] - Returns a string array, containing the artist (0), and the
	 *         title (1).
	 */
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

		returnedData[0] = data.getFirst(artist);
		returnedData[1] = data.getFirst(title);

		return returnedData;
	}
	// TODO: Null issues with .wav files
	public static WritableImage getImage(String filePath) throws UnrecognizableFileType {
		WritableImage returnImage = null;
		if (filePath.endsWith(".mp3")) {
			MP3File metadata = null;
			try {
				metadata = new MP3File(filePath);
			} catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
				e.printStackTrace();
				returnImage = null;
			}
			Tag data = metadata.getTag();
			if (!data.getFirst(art).isEmpty()) {
				try {
					returnImage = SwingFXUtils.toFXImage((BufferedImage) (data.getFirstArtwork().getImage()), null);
				} catch (Exception e) {
					try {
						returnImage = SwingFXUtils.toFXImage((BufferedImage) (data.getArtworkList().get(0)).getImage(), null);
					} catch (IOException | IndexOutOfBoundsException e1) {
						returnImage = null;
					}
				}
			}
		} else if (filePath.endsWith(".mp4") || filePath.endsWith(".m4a") || filePath.endsWith(".m4v")) {
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(new File(filePath), "r");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				returnImage = null;
			}
			Mp4TagReader metadata = new Mp4TagReader();
			Mp4Tag data = null;
			try {
				data = metadata.read(raf);
				raf.close();
			} catch (CannotReadException | IOException e) {
				e.printStackTrace();
				returnImage = null;
			}
			if (!data.getFirst(art).isEmpty()) {
				try {
					returnImage = SwingFXUtils.toFXImage((BufferedImage) (data.getFirstArtwork().getImage()), null);
				} catch (Exception uhhh) {
					try {
						returnImage = SwingFXUtils.toFXImage((BufferedImage) (data.getArtworkList().get(0)).getImage(), null);
					} catch (IOException | IndexOutOfBoundsException e) {
						returnImage = null;
					}
				}
			}
		} else {
			throw new UnrecognizableFileType();
		}
		return returnImage;
	}

	/**
	 * Returns the artist of the song. The source provided needs to be URL friendly!
	 * 
	 * @param source
	 *            The URL of the file.
	 * @return String - The artist.
	 */
	public static String getArtist(String source) {
		String artist = null;

		File file = new File(AudioFile.toFilePath(source));
		try {
			if (source.contains(".mp3")) {
				String[] stuff = getMetadata.getMp3(file);
				artist = (stuff[0]);
			} else if (source.contains(".mp4") || source.contains(".m4a") || source.contains(".m4v")) {
				String[] stuff = getMetadata.getMp4(file);
				artist = (stuff[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return artist;
	}

	/**
	 * Returns the title of the song. The source provided needs to be URL friendly!
	 * 
	 * @param source
	 *            The URL of the file.
	 * @return String - The title.
	 */
	public static String getTitle(String source) {
		String title = null;

		File file = new File(AudioFile.toFilePath(source));
		try {
			if (source.contains(".mp3")) {
				String[] stuff = getMetadata.getMp3(file);
				title = (stuff[1]);
			} else if (source.contains(".mp4") || source.contains(".m4a") || source.contains(".m4v")) {
				String[] stuff = getMetadata.getMp4(file);
				title = (stuff[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return title;
	}

}
