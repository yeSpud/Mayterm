package application.Audio;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.mp4.Mp4TagReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;

import application.UI.CoverArt;
import application.UI.Display;
import javafx.animation.FadeTransition;
import javafx.collections.MapChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

//TODO Seperate some of the functions
public class AudioPlayer {

	public static Media media;
	public static MediaPlayer player;
	public static Stack<String> queue = new Stack<String>();
	public static boolean isPlaying = false, isPaused = false, up = false;
	public static double volume = 0.75d, pmag = 0;
	public static int BPM = 0, beat = 0;

	public static void play() {
		media = new Media(queue.get(0));
		queue.remove(0);

		BPM = 0;
		beat = 0;
		pmag = 0;

		Display.title.setText(media.getSource());
		Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());

		Display.author.setText("");
		Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());

		CoverArt.setArt(null);

		setTitleAndArtist();

		player = new MediaPlayer(media);
		player.setVolume(volume);

		player.play();
		isPlaying = true;
		
		//Display.createeBands();

		Display.root.getChildren().remove(Display.nothing);
		try {
			Display.root.getChildren().add(Display.bars);
		} catch (IllegalArgumentException Duplicate) {
			Display.root.getChildren().removeAll(Display.bars);
			Display.root.getChildren().add(Display.bars);

		}

		player.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				if (!queue.isEmpty()) {
					play();
				} else {
					Display.root.getChildren().remove(Display.bars);
					Display.root.getChildren().add(Display.nothing);
					Display.author.setText("No file currently selected");
					Display.title.setText("Press \"O\" to select a file");
					Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());
					Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());
					CoverArt.setArt(null);
				}

			}

		});

		setupSpectrum();

	}

	public static void pause() {
		try {
			if (isPlaying) {
				player.pause();
				isPaused = true;
				isPlaying = false;

				for (int i = 0; i < 63; i++) {
					Rectangle bar = (Rectangle) Display.bars.getChildren().get(i);
					bar.setHeight(12);
				}
			} else {
				player.play();
				isPaused = false;
				isPlaying = true;
			}
		} catch (NullPointerException NPE) {
			return;
		}
	}

	public static void handleVolume(double vol) {

		volume = Double.parseDouble(String.format("%.2f", volume + vol));

		if (volume > 1) {
			volume = 1;
		}
		if (volume < 0) {
			volume = 0;
		}

		AudioPlayer.player.setVolume(AudioPlayer.volume);

		Display.volumeHUD.setText(String.format("Volume: %d%%", (int) (volume * 100)));

		FadeTransition volumeFade = new FadeTransition(Duration.millis(3000), Display.volumeHUD);
		volumeFade.setFromValue(1);
		volumeFade.setToValue(0);

		volumeFade.play();

	}

	public static void pickSong() {

		FileChooser pickFile = new FileChooser();
		ExtensionFilter fileFilter = new ExtensionFilter("Music", "*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav", "*.m3u8",
				"*.flv", "*.fxm", "*.aif", "*.aiff");
		pickFile.getExtensionFilters().addAll(fileFilter);
		String filePath;
		try {
			filePath = pickFile.showOpenDialog(null).getAbsolutePath().toString();
		} catch (NullPointerException a) {
			filePath = "";
		}

		if (!filePath.isEmpty()) {
			addToQueue(filePath);
		}

	}

	public static void skip() {
		player.stop();
		isPaused = false;
		isPlaying = false;
		if (!queue.isEmpty()) {
			play();
		} else {
			Display.root.getChildren().remove(Display.bars);
			Display.root.getChildren().add(Display.nothing);
			Display.author.setText("No file currently selected");
			Display.title.setText("Press \"O\" to select a file");
			Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());
			Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());
		}
	}

	public static void addToQueue(String filePath) {
		queue.push(String.format("file://%s", filePath.replace(" ", "%20").replace("[", "%5B").replace("]", "%5D")));

		if (!isPlaying && !isPaused) {
			play();
		}
		System.out.println(queue);

	}

	public static void setTitleAndArtist() {

		if (media.getSource().contains(".mp3")) {
			media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
				if (change.wasAdded()) {

					System.out.println(
							String.format("Key: %s\nChanged value: %s", change.getKey(), change.getValueAdded()));
					if (change.getKey().equals("title")) {
						Display.title.setText(change.getValueAdded().toString().toUpperCase());
						Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());
					}
					if (change.getKey().equals("artist")) {
						Display.author.setText(change.getValueAdded().toString().toUpperCase());
						Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());
					}
					if (change.getKey().equals("image")) {
						CoverArt.setArt((Image) change.getValueAdded());
					}
				}
			});
		} else {

			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(media.getSource().replace("file:", "").replace("%20", " ")
						.replace("%5B", "[").replace("%5D", "]"), "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				pause();
			}
			Mp4TagReader metadata = new Mp4TagReader();
			try {
				Display.author.setText(metadata.read(raf).getFirst(FieldKey.ARTIST).toUpperCase());
				Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());
				raf.close();
			} catch (CannotReadException e) {
				e.printStackTrace();
				pause();
			} catch (IOException e) {
				e.printStackTrace();
				pause();
			}

			try {
				raf = new RandomAccessFile(media.getSource().replace("file:", "").replace("%20", " ")
						.replace("%5B", "[").replace("%5D", "]"), "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				pause();
			}
			try {
				Display.title.setText(metadata.read(raf).getFirst(FieldKey.TITLE).toUpperCase());
				Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());
				raf.close();
			} catch (KeyNotFoundException e) {
				e.printStackTrace();
				pause();
			} catch (CannotReadException e) {
				e.printStackTrace();
				pause();
			} catch (IOException e) {
				e.printStackTrace();
				pause();
			}

			try {
				raf = new RandomAccessFile(media.getSource().replace("file:", "").replace("%20", " ")
						.replace("%5B", "[").replace("%5D", "]"), "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				pause();
			}
			try {
				try {
					CoverArt.setArt(SwingFXUtils
							.toFXImage((BufferedImage) (metadata.read(raf).getArtworkList().get(0)).getImage(), null));
				} catch (IndexOutOfBoundsException e1) {
					CoverArt.setArt(null);
				}
			} catch (CannotReadException e) {
				e.printStackTrace();
				pause();
			} catch (IOException e) {
				e.printStackTrace();
				pause();
			}
		}
	}

	public static void setupSpectrum() {
		player.setAudioSpectrumNumBands(63); // 7
		player.setAudioSpectrumInterval(0.033d); // 0.0167d

		// player.setAudioSpectrumThreshold(-100);
		player.setAudioSpectrumListener(null);
		//spectrumListener = new SpectrumListener(Display.STARTING_FREQUENCY, player, spectrimBars)
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				// TODO: Redo spectrum
				/*
				 * System.out.println(String.format("timestamp: %s\nmagnitides: %s", timestamp,
				 * Arrays.toString(magnitudes)));
				 */

				for (int i = 0; i < 63; i++) { // 7
					Rectangle bar = (Rectangle) Display.bars.getChildren().get(i);
					bar.setHeight((63 - magnitudes[i] * -1) * 4);
					/*
					 * Group tests = ((Group) Display.bars); Rectangle test = (Rectangle)
					 * tests.getChildren().get(57 - (i * 9)); if ((60 - magnitudes[i] * -1) < 2) {
					 * test.setHeight(2); } else { test.setHeight(Math.pow(60 - magnitudes[i], 2) /
					 * 50); }
					 */
				}

				//System.out.println((magnitudes[1] + 60));
				if (Math.abs(magnitudes[0] - pmag) > 7.75) { // works for blossom, doesnt work for anything else 🙄
					if (!up) {
						up = true;
					}
				} else {
					if (up) {
						up = false;
						beat++;
					}
				}

				BPM = (int) ((beat / timestamp) * 60);
				
				pmag = magnitudes[1];
				
				if (timestamp > 5 && !(Math.abs(magnitudes[0] - pmag) > 7.625) && up) {
					System.out.println(BPM);
				}

				/*
				 * for (int i = 0; i < 7; i++) { Group tests = ((Group) Display.bars); Rectangle
				 * test = (Rectangle) tests.getChildren().get(56 - (i * 9)); if ((60 -
				 * magnitudes[i] * -1) < 2) { test.setHeight(2); } else { test.setHeight((60 -
				 * magnitudes[i])); } }
				 * 
				 * for (int i = 0; i < 7; i++) { Group tests = ((Group) Display.bars); Rectangle
				 * test = (Rectangle) tests.getChildren().get(58 - (i * 9)); if ((60 -
				 * magnitudes[i] * -1) < 2) { test.setHeight(2); } else { test.setHeight((60 -
				 * magnitudes[i])); } }
				 */

			}

		});
	}
}
