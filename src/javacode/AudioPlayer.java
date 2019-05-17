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
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

	/**
	 * TODO Documentation
	 */
	public Track currentTrack = null;

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

		String title = this.getMetadata(media).getFirst(FieldKey.TITLE), artist = this.getMetadata(media).getFirst(FieldKey.ARTIST);

		// Set the title and artist
		this.window.title.setTitle(title, this.window.stage);
		this.window.artist.setArtist(artist, this.window.stage);

		try {
			// Check if the current track is in the database already
			Track[] tracks = Database.getTrackFromDatabase(media.getSource(), title, artist);

			if (tracks != null) {
				// Set the current track to the first track returned
				this.currentTrack = tracks[0];
			} else {
				// Add the track to the database
				Track newTrack = new Track(media.getSource(), title, artist, GenreColors.ELECTRONIC);
				Database.saveToDatabase(newTrack);
				this.currentTrack = newTrack;
			}

		} catch (IOException e) {
			Debugger.e(e);
			return;
		}

		// Set the bar color and album art to the current track color
		this.window.loadingBar.setColor(this.currentTrack.Genre);
		for (Bar bar : this.window.bars) {
			bar.setColor(this.currentTrack.Genre);
		}
		this.window.invertCat(this.currentTrack.Genre.equals(GenreColors.OTHER));
		this.window.albumArt.setColor(this.currentTrack.Genre);

		// Convert the file
		// TODO

		// Pass the file to a python program
		// TODO
		//try {
		//Process p = Runtime.getRuntime().exec(new String[]{"python3", "/Users/stephenogden/Documents/GitHub/Mayterm/src/pycode/fftify.py", "/Users/stephenogden/Desktop/test/50.wav"});
		//BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		//String line = "";
		//while ((line = reader.readLine()) != null) {
		//	System.out.println(line + "\n");
		//}
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		//PythonInterpreter python = new PythonInterpreter();
		//python.exec("print('Hello world')");
		//python.exec(pyCode + " /Users/stephenogden/Desktop/test/50.wav");


		// Setup the analysis
		/*
		this.mediaPlayer.setAudioSpectrumInterval(1); // Think of this as how often it computes the fft (in seconds)
		this.mediaPlayer.setAudioSpectrumNumBands(200); // How many data points there will be per second
		this.mediaPlayer.setAudioSpectrumListener(new AudioAnalysis());
		 */

		// Play the track
		this.mediaPlayer.play();

		// Update the private status to PLAYING
		this.status = MediaPlayer.Status.PLAYING;

		// Hide the loading bar
		this.window.hideElement(this.window.loadingBar, true);
		for (Bar bar : this.window.bars) {
			this.window.hideElement(bar, false);
		}

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
