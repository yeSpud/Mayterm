package javacode.UI.Text;

import javacode.Debugger;
import javafx.animation.FadeTransition;

public class PauseText extends javafx.scene.text.Text {

	/**
	 * TODO Documentation
	 */
	private final FadeTransition animation = new FadeTransition(javafx.util.Duration.millis(10000), this);

	public PauseText() {
		this.setText("Press \"P\" to pause / unpause");
		this.setFont(new javafx.scene.text.Font(25));
		this.setFill(javafx.scene.paint.Color.WHITE);
		this.updateOpacity(0);
	}

	/**
	 * TODO Documentation
	 *
	 * @param width
	 * @param height
	 */
	public void updatePosition(double width, double height) {
		double x = 10, y = height - this.getLayoutBounds().getHeight();
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	/**
	 * TODO Documentation
	 */
	public void playAnimation() {
		this.animation.setFromValue(1);
		this.animation.setToValue(0);
		this.animation.playFromStart();
	}


	/**
	 * TODO Documentation
	 *
	 * @param opacity
	 */
	public void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %d%%", Math.round(opacity * 100)));
		this.setOpacity(opacity);
	}

}
