package application.Audio;

import java.io.File;
import java.net.URI;
import java.util.Stack;

import application.Database.Database;
import application.SpectrumThings.Spectrum;
import application.UI.DisplayText;
import application.UI.Genre;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {

	public static Media media;
	public static MediaPlayer player;
	public static Stack<String> queue = new Stack<String>(); // TODO 2: Maybe use Queue instead of Stack
	public static boolean isPlaying = false, isPaused = false, up = false;

	public static void play() {
		// TODO: Cleanup

		if (!isPaused && !isPlaying) {

			player = new MediaPlayer(media);
			player.setVolume(DisplayText.volume);

			DisplayText.setTitle(media.getSource());
			DisplayText.setArtist("");
			
			// TODO 2: Wait for file to load
			wavConverter.convertToWAV(new File(AudioFile.toFilePath(media.getSource())));

			if (Database.isInDatabase(AudioFile.toFilePath(media.getSource()))) {
				String path = AudioFile.toFilePath(media.getSource());
				System.out.println("Already in database");
				DisplayText.setTitle(Database.getTitle(path));
				DisplayText.setArtist(Database.getArtist(path));
				Genre.setGenre(Genre.genre.valueOf(Database.getGenre(path)).getColor());
			} else {
				System.out.println("Adding to database");
				DisplayText.setTitleAndArtist(URI.create(media.getSource()).getPath());
				Genre.setGenre(Genre.genre.ELECTRONIC.getColor());
				Database.addSong(AudioFile.toFilePath(media.getSource()), Genre.genre.ELECTRONIC,
						getMetadata.getTitle(media.getSource()), getMetadata.getArtist(media.getSource()));
			}

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
						rotate();
					} else {
						stop();
						isPlaying = false;
					}

				}
			});

			Spectrum.setupSpectrumMovement();

			player.play();
			isPlaying = true;
			isPaused = false;

		} else if (isPaused && !isPlaying) {
			pause();
		}

	}

	/**
	 * Pauses the current track, or resumes it if it's already paused.
	 */
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

	/**
	 * Skips the current track.
	 */
	public static void skip() {
		player.stop();
		isPaused = false;
		isPlaying = false;
		if (!queue.isEmpty()) {
			rotate();
		} else {
			stop();
		}
	}

	/**
	 * Stops the player
	 */
	public static void stop() {
		try {
			player.stop();
			isPlaying = false;
			isPaused = false;
			Spectrum.disableSpectrum(false);
			player.dispose();
		} catch (NullPointerException NPE) {
			return;
		}
	}

	/**
	 * Rotates the track queue, and then tries to play whats next
	 */
	public static void rotate() {
		stop();
		media = new Media(queue.get(0));
		queue.remove(0);
		play();
	}

}
