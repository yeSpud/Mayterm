package application;

import java.util.Stack;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AudioPlayer {

	public static Media media;
	public static MediaPlayer player;
	public static Stack<String> queue = new Stack<String>();
	public static boolean isPlaying = false;
	public static float volume = 0.75f;

	public static void play() {
		if (queue.isEmpty()) {
			pickSong();
			isPlaying = false;
		} else {
			media = new Media(String.format("file://%s", queue.pop()));
			player = new MediaPlayer(media);
			player.setVolume(volume);
			
			player.play();
			isPlaying = true;
			player.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					play();
					return;

				}

			});
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
			System.out.println(filePath);
			queue.addElement(filePath);
			System.out.println(queue.peek());
			if (!isPlaying) {
				play();
			}
		}

	}

}
