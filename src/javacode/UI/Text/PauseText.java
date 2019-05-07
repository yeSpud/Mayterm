package javacode.UI.Text;

import javacode.Debugger;
import javafx.animation.FadeTransition;

/**
 * Creates the pause text node for the user when a track is played/resumed. Once it is shown, the text then fades out.
 *
 * @author Spud
 */
public class PauseText extends javafx.scene.text.Text implements javacode.UI.RelativeNode {

	/**
	 * Creates the fade animation that the pause text will utilize.
	 * This animation should take 1 second to execute fully.
	 */
	private final FadeTransition animation = new FadeTransition(javafx.util.Duration.millis(10000), this);

	public PauseText() {
		this.setText("Press \"P\" to pause / unpause");
		this.setFont(new javafx.scene.text.Font(25));
		this.setFill(javafx.scene.paint.Color.WHITE);
		this.updateOpacity(0);
	}

	@Override
	public void updatePosition(double width, double height) {
		double x = 10, y = height - this.getLayoutBounds().getHeight();
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Plays the fade animation of the text.
	 */
	public void playAnimation() {
		this.animation.setFromValue(1);
		this.animation.setToValue(0);
		this.animation.playFromStart();
	}


	/**
	 * Updates the opacity the pause text to the provided argument.
	 *
	 * @param opacity The opacity as a value from 0 to 1.0.
	 */
	public void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %d%%", Math.round(opacity * 100)));
		this.setOpacity(opacity);
	}

}
