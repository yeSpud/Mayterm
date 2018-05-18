package application.Audio;

import java.net.URI;
import java.util.Stack;

import application.Database.Database;
import application.Database.Environment;
import application.Database.Environment.OS;
import application.UI.CoverArt;
import application.UI.DisplayText;
import application.UI.Genre;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		
		CoverArt.setArt(null);
		
		DisplayText.setTitle(media.getSource());
		DisplayText.setArtist("");

		DisplayText.setTitleAndArtist(URI.create(media.getSource()).getPath());

		
		Database.addSong(
				media.getSource().replace("file:\\\\", "").replace("%20", " ").replace("%5B", "[").replace("%5D", "]").replace(":", "%3A").replace("\\", "%5C"),
				Genre.genre.ELECTRONIC.name(), getMetadata.getTitle(media.getSource()),
				getMetadata.getArtist(media.getSource()));
				

		player = new MediaPlayer(media);
		player.setVolume(DisplayText.volume);

		player.play();
		isPlaying = true;
		DisplayText.handleInfo();

		try {
			Spectrum.enableSpectrum();
		} catch (IllegalArgumentException Duplicate) {
			Spectrum.disableSpectrum(true);
			Spectrum.enableSpectrum();
		}

		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				if (!queue.isEmpty()) {
					play();
				} else {
					Spectrum.disableSpectrum(false);
				}
			}
		});

		Spectrum.setupSpectrumMovement();

	}

	public static void pause() {
		try {
			if (isPlaying) {
				player.pause();
				isPaused = true;
				isPlaying = false;

				Spectrum.clearSpectrum();

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
		ExtensionFilter fileFilter = new ExtensionFilter("Music", "*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav", "*.aif",
				"*.aiff");
		pickFile.getExtensionFilters().addAll(fileFilter);
		String filePath;
		try {
			if (Environment.getOS().equals(OS.WINDOWS)) {
				filePath = "/" +pickFile.showOpenDialog(null).getAbsolutePath().toString();
			} else {
			filePath = pickFile.showOpenDialog(null).getAbsolutePath().toString();
			}
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
			Spectrum.disableSpectrum(false);
		}
	}

	public static void addToQueue(String filePath) {
		queue.push(String.format("file://%s", filePath.replace(" ", "%20").replace("[", "%5B").replace("]", "%5D").replace(":", "%3A").replace("\\", "%5C")));
		if (!isPlaying && !isPaused) {
			play();
		}
	}
}
