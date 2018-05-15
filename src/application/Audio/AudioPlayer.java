package application.Audio;

import java.io.File;
import java.util.Stack;

import application.UI.CoverArt;
import application.UI.Display;
import application.UI.DisplayText;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

// TODO Seperate some of the functions
public class AudioPlayer {

	public static Media media;
	public static MediaPlayer player;
	public static Stack<String> queue = new Stack<String>();
	public static boolean isPlaying = false, isPaused = false, up = false;
	public static double pmag = 0;
	public static int BPM = 0, beat = 0;

	public static void play() {
		media = new Media(queue.get(0));
		queue.remove(0);

		BPM = 0;
		beat = 0;
		pmag = 0;

		DisplayText.setTitle(media.getSource());
		DisplayText.setAuthor("");

		CoverArt.setArt(null);

		setTitleAndArtist();

		player = new MediaPlayer(media);
		player.setVolume(DisplayText.volume);

		player.play();
		isPlaying = true;

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
					DisplayText.setAuthor("No file currently selected");
					DisplayText.setTitle("Press \"O\" to select a file");
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
			DisplayText.setAuthor("No file currently selected");
			DisplayText.setTitle("Press \"O\" to select a file");
			CoverArt.setArt(null);
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

		// TODO: {ID3=java.nio.HeapByteBufferR[pos=222 lim=10343 cap=10343]}

		if (media.getSource().contains(".mp3")) {
			media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
				if (change.wasAdded()) {
					System.out.println(
							String.format("Key: %s\nChanged value: %s", change.getKey(), change.getValueAdded()));
					if (change.getKey().equals("title")) {
						DisplayText.setTitle(change.getValueAdded().toString().toUpperCase());
					}
					if (change.getKey().equals("artist")) {
						DisplayText.setAuthor(change.getValueAdded().toString().toUpperCase());
					}
					if (change.getKey().equals("image")) {
						CoverArt.setArt((Image) change.getValueAdded());
					}
				}
			});
		} else {
				String[] stuff = getMetadata.getMp4(new File(media.getSource().replace("file:", "").replace("%20", " ")
							.replace("%5B", "[").replace("%5D", "]")));
				DisplayText.setAuthor(stuff[0]);
				DisplayText.setTitle(stuff[1]);
		}
	}

	public static void setupSpectrum() {
		player.setAudioSpectrumNumBands(63); // 7
		player.setAudioSpectrumInterval(0.033d); // 0.0167d

		// player.setAudioSpectrumThreshold(-100);
		player.setAudioSpectrumListener(null);
		// spectrumListener = new SpectrumListener(Display.STARTING_FREQUENCY, player,
		// spectrimBars)
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

				// System.out.println((magnitudes[1] + 60));
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
