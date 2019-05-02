package javacode;

import javacode.UI.Bar;
import javacode.Windows.Window;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 */
	public MediaPlayer.Status status = MediaPlayer.Status.STOPPED;

	private Window window;

	public AudioPlayer(Window window) {
		this.window = window;
	}

	/**
	 * TODO Documentation
	 *
	 * @param volume
	 */
	public void changeVolume(double volume) {
		Debugger.d(this.getClass(), String.format("Changing volume to: %.2f", volume));
		try {
			mediaPlayer.setVolume(volume);
		} catch (NullPointerException npe) {
			Debugger.d(this.getClass(), "Cannot set volume as media player is null");
		}
	}


	/**
	 * TODO Documentation
	 */
	public void loadTrack() {

		// Create a file picker dialog for loading the file into the queue
		FileChooser pickFile = new FileChooser();

		// Create an extension regex for only audio files
		String[] supportedExtensions = new String[]{"*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav", "*.aif", ".aiff", "*.fxm", "*.flv", "*.m3u8"};
		FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Music", supportedExtensions);
		pickFile.getExtensionFilters().addAll(fileFilter);

		// Get the file chosen
		File file = pickFile.showOpenDialog(null);
		Debugger.d(this.getClass(), "File path: " + file.getPath());

		// Create a new media object given the file path
		Media media = new Media(file.toPath().toUri().toString());
		media.setOnError(() -> Debugger.d(this.getClass(), "Error with media: " + media.getError().toString()));
		Debugger.d(this.getClass(), "Queueing from URI: " + media.getSource());

		// Add the media to the queue
		this.queue.add(media);

		// Play the track
		this.play();
	}

	/**
	 * TODO Documentation
	 */
	public void play() {

		// Make sure the media player isn't null before the initial checks
		if (this.mediaPlayer != null) {

			// Check if the player is stopped or paused, in order to play
			if (this.status.equals(MediaPlayer.Status.STOPPED)) {

				// Show the loading bar
				this.window.hideElement(this.window.loadingBar, false);
				for (Bar bar : this.window.bars) {
					this.window.hideElement(bar, true);
				}

				// Dispose of the player
				Debugger.d(this.getClass(), "Disposing of old media player");
				this.mediaPlayer.dispose();
			} else if (this.status.equals(MediaPlayer.Status.PAUSED)) {
				// Continue to play
				Debugger.d(this.getClass(), "Unpausing media");
				this.mediaPlayer.play();
				return;
			} else if (this.status.equals(MediaPlayer.Status.PLAYING)) {
				Debugger.d(this.getClass(), "Media is still playing...");
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
		this.mediaPlayer.setOnEndOfMedia(() -> {
			Debugger.d(this.getClass(), "End of media!");
			this.status = MediaPlayer.Status.STOPPED;
			this.play();
		});

		// If there are any errors, report it
		this.mediaPlayer.setOnError(() -> Debugger.d(this.getClass(), "Error with media player: " + this.mediaPlayer.getError().toString()));

		// Prematurely set the volume
		this.mediaPlayer.setVolume(this.window.volumeText.currentVolume);

		// Set the title and artist
		this.window.title.setTitle(this.getMetadata(media).getFirst(FieldKey.TITLE), this.window.stage);
		this.window.artist.setArtist(this.getMetadata(media).getFirst(FieldKey.ARTIST), this.window.stage);

		// Play the track
		this.mediaPlayer.play();

		// Update the private status to PLAYING
		this.status = MediaPlayer.Status.PLAYING;

		// Hide the loading bar
		this.window.hideElement(this.window.loadingBar, true);
		for (Bar bar : this.window.bars) {
			this.window.hideElement(bar, false);
		}

		// Setup the analysis
		this.mediaPlayer.setAudioSpectrumInterval(1 / 60); // Think of this as how often it computes the fft (in seconds)
		this.mediaPlayer.setAudioSpectrumNumBands(63); // How many data points there will be per second
		this.mediaPlayer.setAudioSpectrumListener(new AudioAnalysis());

		// Play the text animation
		this.window.pauseText.playAnimation();

	}

	/**
	 * TODO Documentaiton
	 *
	 * @param source
	 * @return
	 */
	private Tag getMetadata(Media source) {
		AudioFile f = null;

		//Disable loggers
		Logger[] pin = new Logger[]{Logger.getLogger("org.jaudiotagger")};
		for (Logger l : pin) {
			l.setLevel(Level.OFF);
		}

		try {
			f = AudioFileIO.read(Paths.get(URI.create(source.getSource())).toFile());

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
		}

		return f != null ? f.getTag() : null;
	}

}
