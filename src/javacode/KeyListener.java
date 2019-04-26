package javacode;

import javacode.Windows.Window;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyListener implements EventHandler<KeyEvent> {

	private Window window;

	public KeyListener(Window window) {
		this.window = window;
	}

	@Override
	public void handle(KeyEvent event) {

		Debugger.d(this.getClass(), "Key pressed: " + event.getCode().getName());

		GenreColors albumGenre = GenreColors.getGenre(this.window.albumArt.getColor());

		switch (event.getCode()) {
			case PERIOD:
				// Try to get the genre color from the album art
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
				}
				break;
			case COMMA:
				// Try to get the genre color from the album art
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
				}
				break;
		}


	}
}
