package javacode.UI.Text;

import javacode.Debugger;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrackInfo {

	public class Title extends Text {

		private static final double TEXT_SIZE = 36d;

		public Title() {
			this.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, Title.TEXT_SIZE));
			this.setX(266.5);
			this.setY(502);
			this.setFill(Color.WHITE);
		}

		/**
		 * TODO Documentation
		 *
		 * @param text
		 * @param stage
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

	public class Artist extends Text {

		private static final double TEXT_SIZE = 75d;

		public Artist() {
			this.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, TEXT_SIZE));
			this.setX(268);
			this.setY(466);
			this.setFill(Color.WHITE);
		}

		/**
		 * TODO Documentation
		 *
		 * @param text
		 * @param stage
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
	 * TODO Documentation
	 * TODO Test
	 *
	 * @param text
	 * @param weight
	 * @param fontSize
	 * @param stage
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
