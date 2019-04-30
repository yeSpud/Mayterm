package javacode;

import javacode.UI.Text.PauseText;
import javacode.UI.Text.VolumeText;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class AudioPlayer {

	/**
	 * TODO Documentation
	 */
	public MediaPlayer mediaPlayer;

	/**
	 * TODO Documentation
	 */
	public Queue<Media> queue = new LinkedList<>();

	/**
	 * TODO Documentation
	 *
	 * @param volume
	 */
	public void changeVolume(double volume) {
		Debugger.d(this.getClass(), "Changing volume to: " + volume);
		try {
			mediaPlayer.setVolume(volume);
		} catch (NullPointerException npe) {
			Debugger.d(this.getClass(), "Cannot set volume as media player is null");
		}
	}


	/**
	 * TODO Documentation
	 */
	public void loadTrack(VolumeText volume, PauseText pauseText) {

		// Create a file picker dialog for loading the file into the queue
		FileChooser pickFile = new FileChooser();

		// Create an extension regex for only audio files
		String[] supportedExtensions = new String[]{"*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav", "*.aif", ".aiff"};
		FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Music", supportedExtensions);
		pickFile.getExtensionFilters().addAll(fileFilter);

		// Get the file chosen
		File file = pickFile.showOpenDialog(null);
		Debugger.d(this.getClass(), "File path: " + file.getPath());

		// Create a new media object given the file path
		Media media = new Media(file.toPath().toUri().toString());
		Debugger.d(this.getClass(), "Queueing from URI: " + media.getSource());

		// Add the media to the queue
		this.queue.add(media);

		// Play the track
		this.play(volume, pauseText);
	}

	/**
	 * TODO Documentation
	 */
	public void play(VolumeText volume, PauseText pauseText) {

		// Make sure the media player isn't null before the initial checks
		if (this.mediaPlayer != null) {

			// Check if the player is stopped or paused, in order to play
			if (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
				// Dispose of the player
				Debugger.d(this.getClass(), "Disposing of old media player");
				this.mediaPlayer.dispose();
			} else if (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
				// Continue to play
				Debugger.d(this.getClass(), "Unpausing media");
				this.mediaPlayer.play();
				return;
			} else {
				// If its currently playing, return early
				return;
			}
		}

		// Then create a new player with the media in the queue (might be null)
		Media media = this.queue.poll();

		if (media != null) {
			Debugger.d(this.getClass(), "Loading media at URI: " + media.getSource());
			this.mediaPlayer = new MediaPlayer(media);
		} else {
			// Since there aren't anymore tracks, let the user know
			Debugger.d(this.getClass(), "No more tracks in queue");
			return;
		}

		// Just go through the play method again once done
		this.mediaPlayer.setOnEndOfMedia(() -> this.play(volume, pauseText));

		// If there are any errors, report it
		this.mediaPlayer.setOnError(() -> Debugger.d(this.getClass(), "Error with media: " + this.mediaPlayer.getError()));

		// Prematurely set the volume
		this.mediaPlayer.setVolume(volume.currentVolume);

		// Play the track
		this.mediaPlayer.play();

		// Play the text animation
		pauseText.playAnimation();

	}

}
