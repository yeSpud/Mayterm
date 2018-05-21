package application.Audio;

import application.Database.Environment;
import application.Database.Environment.OS;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AudioFile {

	/**
	 * Create the file chooser window in order to choose a file to play.
	 */
	public static void pickSong() {
		FileChooser pickFile = new FileChooser();
		ExtensionFilter fileFilter = new ExtensionFilter("Music", "*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav", "*.aif",
				"*.aiff");
		pickFile.getExtensionFilters().addAll(fileFilter);
		String filePath;
		try {
			if (Environment.getOS().equals(OS.WINDOWS)) {
				filePath = "/" + pickFile.showOpenDialog(null).getAbsolutePath().toString();
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

	/**
	 * Adds the entered file to the queue of the media player.
	 * 
	 * @param filePath
	 *            The full path of the file.
	 */
	public static void addToQueue(String filePath) {
		AudioPlayer.queue.push(toURL(filePath));
		if (!AudioPlayer.isPlaying && !AudioPlayer.isPaused) {
			AudioPlayer.play();
		}
	}

	/**
	 * Formats the given path to a URL friendly string.
	 * 
	 * @param FilePath
	 *            The absolute path of the file.
	 * @return String - The formatted URL.
	 */
	public static String toURL(String FilePath) {
		return String.format("file://%s", FilePath.replace(" ", "%20").replace("[", "%5B").replace("]", "%5D")
				.replace(":", "%3A").replace("\\", "%5C"));
	}

	/**
	 * Formats the given URL to a file-path friendly string.
	 * 
	 * @param URL
	 *            The URL of the file
	 * @return String - The formatted URL.
	 */
	public static String toFilePath(String URL) {
		URL = URL.replace("file://", "").replace("%20", " ").replace("%5B", "[").replace("%5D", "]").replace("\\",
				"%5C");
		return URL.replace(":", "%3A");
	}

}
