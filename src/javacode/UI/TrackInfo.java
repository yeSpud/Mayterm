package javacode.UI;

import javacode.Debugger;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

		}

	}

}
