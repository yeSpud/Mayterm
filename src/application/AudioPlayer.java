package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.mp4.Mp4TagReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;

import javafx.animation.FadeTransition;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class AudioPlayer {

	public static Media media;
	public static MediaPlayer player;
	public static Stack<String> queue = new Stack<String>();
	public static boolean isPlaying = false, isPaused = false;
	public static double volume = 0.75d;

	public static void play() {
		media = new Media(queue.pop());

		Display.title.setText(media.getSource());
		Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());

		Display.author.setText("");
		Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());

		setTitleAndArtist();

		player = new MediaPlayer(media);
		player.setVolume(volume);

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
				// TODO Auto-generated method stub
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

		});

		// TODO: Spectrum freezes on change
		setupSpectrum();

	}

	public static void pause() {
		try {
			if (isPlaying) {
				player.pause();
				isPaused = true;
				isPlaying = false;

				// TODO: Reset bars on pause
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
		queue.add(String.format("file://%s", filePath.replace(" ", "%20").replace("[", "%5B").replace("]", "%5D")));

		if (!isPlaying && !isPaused) {
			play();
		}
		System.out.println(queue);

	}

	public static void setTitleAndArtist() {
		if (media.getSource().endsWith(".mp3")) {
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
						Display.coverArt = new ImageView();
						Display.coverArt.setX(1034);
						Display.coverArt.setY(198);
						Display.coverArt.setImage((Image) change.getValueAdded());
						Display.coverArt.setFitHeight(126);
						Display.coverArt.setFitWidth(126);
						Display.coverArt.setRotate(180);
						Display.root.getChildren().add(Display.coverArt);
					}
				}
			});
		} else {
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(media.getSource().replace("file:", "").replace("%20", " ")
						.replace("%5B", "[").replace("%5D", "]"), "r");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Mp4TagReader metadata = new Mp4TagReader();
			try {
				Display.author.setText(metadata.read(raf).getFirst(FieldKey.ARTIST).toUpperCase());
				Display.author.setX(1012 - Display.author.getLayoutBounds().getWidth());
				raf.close();
			} catch (CannotReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				raf = new RandomAccessFile(media.getSource().replace("file:", "").replace("%20", " ")
						.replace("%5B", "[").replace("%5D", "]"), "r");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Display.title.setText(metadata.read(raf).getFirst(FieldKey.TITLE).toUpperCase());
			} catch (KeyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Display.title.setX(1012 - Display.title.getLayoutBounds().getWidth());
		}
	}

	public static void setupSpectrum() {
		player.setAudioSpectrumNumBands(63); // 7
		player.setAudioSpectrumInterval(0.0167d);

		// player.setAudioSpectrumThreshold(-100);
		player.setAudioSpectrumListener(new AudioSpectrumListener() {

			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
				// TODO Auto-generated method stub
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
