package javacode.UI.Text;

import javacode.Debugger;
import javafx.animation.FadeTransition;
import javafx.scene.Cursor;

/**
 * TODO Documentation
 */
public class ErrorPrompt extends javafx.scene.text.Text {

	/**
	 * TODO Documentation
	 */
	private final FadeTransition animation = new FadeTransition(javafx.util.Duration.minutes(1d), this);

	// TODO Make selectable when visible
	public ErrorPrompt() {

		// Setup the label
		this.setFont(new javafx.scene.text.Font("Andale Mono", 15));
		this.setFill(javafx.scene.paint.Color.RED);
		this.setX(5);
		this.setY(15);
		this.setOpacity(0d);

		// Copy the top error message to the users clipboard when they click on the text
		this.setOnMouseClicked((event -> {
			java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(this.getText().split("\n")[0]), null);
			Debugger.d(ErrorPrompt.class, "Copied error to clipboard!");
			this.setText("Copied error to clipboard!\n" + this.getText());
		}));

		// Change the cursor back to normal when finished
		this.animation.setOnFinished((event -> this.setCursor(Cursor.DEFAULT)));

	}

	/**
	 * Plays the fade animation of the text.
	 */
	private void playAnimation() {
		this.animation.setFromValue(1);
		this.animation.setToValue(0);
		this.animation.playFromStart();
		this.setCursor(Cursor.TEXT);
	}

	/**
	 * TODO Documentation
	 *
	 * @param stacktrace
	 */
	public void logError(String stacktrace) {
		Debugger.d(this.getClass(), "Logging error: \n" + stacktrace);
		this.setText(stacktrace + "\n");
		this.playAnimation();
	}

}
