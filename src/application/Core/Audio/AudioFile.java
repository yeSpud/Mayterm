package application.Core.Audio;

import application.Core.Main;
import application.Core.Database.Environment;
import application.Core.Database.Environment.OS;
import application.Core.Errors.UnrecognizableOperatingSystem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Class responsible for picking the next track, adding that track to a queue,
 * and formatting URLs/paths.
 * 
 * @author Spud
 *
 */
public class AudioFile {

	/**
	 * Create the file chooser window in order to choose a file to play.
	 */
	public static void pickSong() {
		FileChooser pickFile = new FileChooser();
		ExtensionFilter fileFilter = new ExtensionFilter("Music", "*.mp3", "*.m4a", "*.mp4", "*.m4v", "*.wav");
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

	/**
	 * Adds the entered file to the queue of the media player.
	 * 
	 * @param filePath The full path of the file.
	 */
	public static void addToQueue(String filePath) {
		AudioPlayer.queue.push(toURL(filePath));
		if (!AudioPlayer.isPlaying && !AudioPlayer.isPaused) {
			AudioPlayer.rotate();
		}
	}

	/**
	 * Formats the given path to a URL friendly string.
	 * 
	 * @param FilePath The absolute path of the file.
	 * @return String - The formatted URL.
	 */
	public static String toURL(String FilePath) {
		String returnString = null;
		try {
			if (Environment.getOS().equals(OS.MACOS) || Environment.getOS().equals(OS.LINUX)) {
				returnString = String.format("file://%s", FilePath.replace(" ", "%20").replace("[", "%5B")
						.replace("]", "%5D").replace(":", "%3A").replace("\\", "%5C"));
			} else if (Environment.getOS().equals(OS.WINDOWS)) {
				returnString = String.format("file:///%s", FilePath.replace(" ", "%20").replace("[", "%5B")
						.replace("]", "%5D").replace(":", "%3A").replace("\\", "%5C"));
			} else {
				throw new UnrecognizableOperatingSystem();
			}
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
		}
		if (Main.debug) {
			System.out.println(String.format("FilePath: %s\nURL: %s", FilePath, returnString));
		}
		return returnString;
	}

	/**
	 * Formats the given URL to a file-path friendly string.
	 * 
	 * @param URL The URL of the file
	 * @return String - The formatted URL.
	 */
	public static String toFilePath(String URL) {
		String returnString = null;
		try {
			if (Environment.getOS().equals(OS.MACOS) || Environment.getOS().equals(OS.LINUX)) {
				returnString = URL.replace("file://", "").replace("%20", " ").replace("%5B", "[").replace("%5D", "]")
						.replace("%5C", "\\").replace("%3A", ":");

			} else if (Environment.getOS().equals(OS.WINDOWS)) {
				returnString = URL.replace("file:///", "").replace("%20", " ").replace("%5B", "[").replace("%5D", "]")
						.replace("%5C", "\\").replace("%3A", ":");
			} else {
				throw new UnrecognizableOperatingSystem();
			}
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
		}
		System.out.println(String.format("URL -> file path\n%s -> %s", URL, returnString));
		return returnString;
	}

}
