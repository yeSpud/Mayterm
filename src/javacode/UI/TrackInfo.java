package javacode.UI;

import javacode.Debugger;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.UUID;

public class TrackInfo {

	public class Title extends Text {

		private static final double TEXT_SIZE = 35d;

		public Title() {
			this.setFont(Font.font("Arial", Title.TEXT_SIZE));
			this.setX(1012 - this.getLayoutBounds().getWidth());
			this.setY(244);
			this.setFill(Color.WHITE);
		}

		/**
		 * TODO Documentation
		 *
		 * @param text
		 */
		public void setTitle(String text) {
			if (text != null) {
				Debugger.d(this.getClass(), "Changing title to " + text);
				this.setText(text.toUpperCase());
			}
		}

	}

	public class Artist extends Text {

		private static final double TEXT_SIZE = 68.75d;

		public Artist() {
			this.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, TEXT_SIZE));
			this.setX(1012 - this.getLayoutBounds().getWidth());
			this.setY(306);
			this.setFill(Color.WHITE);
		}

		/**
		 * TODO Documentation
		 *
		 * @param text
		 */
		public void setArtist(String text) {
			if (text != null) {
				Debugger.d(this.getClass(), "Changing artist to " + text);
				this.setText(text.toUpperCase());
			}
		}

	}

	/**
	 * TODO Documentation
	 * TODO Test
	 *
	 * @param text
	 * @param weight
	 * @param fontSize
	 */
	private static void checkTextSpacing(Text text, FontWeight weight, double fontSize) {
		double overSize = (text.getLayoutBounds().getWidth() + 20) - 1012;
		if (overSize > 0) {
			text.setFont(Font.font("Arial", weight, fontSize - 1));
			text.setX(1012 - text.getLayoutBounds().getWidth());
			TrackInfo.checkTextSpacing(text, weight, fontSize - 1);
		}
	}


	// TODO Art from track

}
