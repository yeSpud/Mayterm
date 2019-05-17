package javacode;

import javacode.UI.Bar;
import javacode.Windows.Window;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;

public class KeyListener implements javafx.event.EventHandler<KeyEvent> {

	private Window window;

	public KeyListener(Window window) {
		this.window = window;
	}

	@Override
	public void handle(KeyEvent event) {

		Debugger.d(this.getClass(), "Key pressed: " + event.getCode().getName());

		GenreColors albumGenre;

		switch (event.getCode()) {
			case PERIOD:
				// Try to get the genre color from the album art
				albumGenre = GenreColors.getGenre(this.window.albumArt.getColor());

				if (albumGenre != null) {
					Debugger.d(this.getClass(), "Current genre: " + albumGenre);

					// Get the current index of the genre in the list
					int currentIndex = albumGenre.ordinal();
					Debugger.d(this.getClass(), "Current genre index: " + currentIndex);

					// Go up an an index, but make sure it does not wrap the max value
					int nextIndex = (currentIndex + 1) % GenreColors.values().length;
					Debugger.d(this.getClass(), "Next genre index: " + nextIndex);

					// Get the next genre
					GenreColors newGenre = GenreColors.values()[nextIndex];
					Debugger.d(this.getClass(), "New genre: " + newGenre);

					// If the genre color is OTHER, invert the cat
					this.window.invertCat(newGenre.equals(GenreColors.OTHER));
					this.window.albumArt.setColor(newGenre);
					this.window.loadingBar.setColor(newGenre);
					for (Bar bar : this.window.bars) {
						bar.setColor(newGenre);
					}

					// Change the genre in the database
					Database.updateGenre(this.window.player.currentTrack, newGenre);
				}
				break;
			case COMMA:
				// Try to get the genre color from the album art
				albumGenre = GenreColors.getGenre(this.window.albumArt.getColor());

				if (albumGenre != null) {
					Debugger.d(this.getClass(), "Current genre: " + albumGenre);

					// Get the current index of the genre in the list
					int currentIndex = albumGenre.ordinal();
					Debugger.d(this.getClass(), "Current genre index: " + currentIndex);

					// Make it wrap around in order to utilize the mod functionality
					int nextIndex = (currentIndex - 1 + GenreColors.values().length) % GenreColors.values().length;
					Debugger.d(this.getClass(), "Next genre index: " + nextIndex);

					// Get the next genre
					GenreColors newGenre = GenreColors.values()[nextIndex];
					Debugger.d(this.getClass(), "New genre: " + newGenre);

					// If the genre color is OTHER, invert the cat
					this.window.invertCat(newGenre.equals(GenreColors.OTHER));
					this.window.albumArt.setColor(newGenre);
					this.window.loadingBar.setColor(newGenre);
					for (Bar bar : this.window.bars) {
						bar.setColor(newGenre);
					}

					// Change the genre in the database
					Database.updateGenre(this.window.player.currentTrack, newGenre);
				}
				break;
			case UP:
				// Increase the volume
				this.window.volumeText.adjustVolume(0.05d, this.window.player);
				break;
			case DOWN:
				// Decrease the volume
				this.window.volumeText.adjustVolume(-0.05d, this.window.player);
				break;
			case P:
				// Pause / Unpause the media player
				try {
					if (this.window.player.status.equals(MediaPlayer.Status.PLAYING)) {
						Debugger.d(this.getClass(), "Pausing track");
						this.window.player.mediaPlayer.pause();
						this.window.player.status = MediaPlayer.Status.PAUSED;
						for (Bar bar : this.window.bars) {
							bar.setHeight(3);
							bar.setY(bar.rootY - 3);
						}
						this.window.stage.setTitle("PAUSED");
					} else if (this.window.player.status.equals(MediaPlayer.Status.PAUSED)) {
						Debugger.d(this.getClass(), "Resuming track");
						this.window.player.mediaPlayer.play();
						this.window.player.status = MediaPlayer.Status.PLAYING;
						this.window.stage.setTitle("PLAYING - " + this.window.player.currentTrack.Title);
					} else {
						return;
					}
					// Play the text animation
					this.window.pauseText.playAnimation();
				} catch (NullPointerException NPE) {
					// No media has been loaded, so just return
					return;
				}
				break;
			case O:
				// Load a new track
				this.window.player.loadTrack();
				break;

			case RIGHT:
				// Skip the track
				try {
					if (this.window.player.status.equals(MediaPlayer.Status.PLAYING) || this.window.player.status.equals(MediaPlayer.Status.PAUSED)) {
						this.window.player.mediaPlayer.stop();
						this.window.player.status = MediaPlayer.Status.STOPPED;
						this.window.player.play();
					}
				} catch (NullPointerException NPE) {
					// No media has been loaded, so just return
					return;
				}
				break;
		}


	}
}
