package javacode;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.LinkedList;
import java.util.Queue;

public class AudioPlayer {

	/**
	 * TODO Documentation
	 */
	public Media track;

	/**
	 * TODO Documentation
	 */
	public MediaPlayer mediaPlayer;

	/**
	 * TODO Documentation
	 */
	public Queue queue = new LinkedList();

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

}
