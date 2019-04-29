package javacode.UI.Text;

import javacode.Debugger;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PauseText extends Text {

	/**
	 * TODO Documentation
	 */
	private final FadeTransition animation = new FadeTransition(Duration.millis(10000), this);

	public PauseText() {
		this.setText("Press \"P\" to pause / unpause");
		this.setFont(new Font(25));
		this.setFill(Color.WHITE);
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
