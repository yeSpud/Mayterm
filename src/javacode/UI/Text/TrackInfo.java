package javacode.UI.Text;

import javacode.Debugger;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Track info includes the specific metadata, such as the track title, track artist, and track cover art.
 *
 * @author Spud
 */
public class TrackInfo {

	/**
	 * Creates the title text for the given track.
	 */
	public class Title extends Text {

		private static final double TEXT_SIZE = 36d;

		public Title() {
			this.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, Title.TEXT_SIZE));
			this.setX(266.5);
			this.setY(502);
			this.setFill(Color.WHITE);
		}

		/**
		 * Sets the title text, and updates the text size if it's too big.
		 *
		 * @param text  The title string.
		 * @param stage The stage (used for checking title size spacing).
		 */
		public void setTitle(String text, Stage stage) {
			if (text != null) {
				Debugger.d(this.getClass(), "Changing title to " + text);
				this.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, TEXT_SIZE));
				this.setText(text.toUpperCase());
				TrackInfo.checkTextSpacing(this, FontWeight.EXTRA_LIGHT, TEXT_SIZE, stage);
			}
		}

	}

	/**
	 * Creates the artist text for the given track
	 */
	public class Artist extends Text {

		private static final double TEXT_SIZE = 75d;

		public Artist() {
			this.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, TEXT_SIZE));
			this.setX(268);
			this.setY(466);
			this.setFill(Color.WHITE);
		}

		/**
		 * Sets the artist text, and updates the text size of it's too big.
		 *
		 * @param text  The artist string.
		 * @param stage THe stage (used for checking artist size spacing).
		 */
		public void setArtist(String text, Stage stage) {
			if (text != null) {
				Debugger.d(this.getClass(), "Changing artist to " + text);
				this.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, TEXT_SIZE));
				this.setText(text.toUpperCase());
				TrackInfo.checkTextSpacing(this, FontWeight.EXTRA_BOLD, TEXT_SIZE, stage);
			}
		}

	}

	/**
	 * TODO
	 */
	public class CoverArt {

	}

	/**
	 * Checks the spacing of the text in comparison to the rest of the stage. If the text exceeds a specified boundary,
	 * the text size is shrunk. This check then repeats within itself (it's a recursive function) in order to get the text
	 * to fit within the bounds.
	 *
	 * @param text     The text node.
	 * @param weight   The font weight.
	 * @param fontSize The original font size.
	 * @param stage    The stage this text node belongs to.
	 */
	private static void checkTextSpacing(Text text, FontWeight weight, double fontSize, Stage stage) {
		double overSize = (text.getLayoutBounds().getWidth() + 268) - (stage.getWidth() * 0.96d);
		if (overSize > 0) {
			Debugger.d(TrackInfo.class, "Resizing text...");
			text.setFont(Font.font("Arial", weight, fontSize - 1));
			TrackInfo.checkTextSpacing(text, weight, fontSize - 1, stage);
		}
	}
}
