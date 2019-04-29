package javacode.UI.Text;

import javacode.AudioPlayer;
import javacode.Debugger;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class VolumeText extends Text {

	/**
	 * TODO Documentation
	 */
	public double currentVolume = 0.75d;

	/**
	 * TODO Documentation
	 */
	private final FadeTransition animation = new FadeTransition(Duration.millis(3000), this);

	public VolumeText() {
		this.setText(String.format("Volume: %d%%", Math.round(this.currentVolume * 100)));
		this.setFont(Font.font(20));
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
		double x = width - (this.getLayoutBounds().getWidth() + (width / 4.5)), y = this.getLayoutBounds().getHeight();
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	/**
	 * TODO Documentation
	 *
	 * @param increment
	 * @param audioPlayer
	 */
	public void adjustVolume(double increment, AudioPlayer audioPlayer) {
		this.setVolume(this.currentVolume += increment, audioPlayer);
	}

	/**
	 * TODO Documentation
	 *
	 * @param volume
	 * @param audioPlayer
	 */
	public void setVolume(double volume, AudioPlayer audioPlayer) {
		this.currentVolume = volume;
		Debugger.d(this.getClass(), "Setting volume to: " + this.currentVolume);

		// Make sure its not less than 0, or greater than 1
		if (this.currentVolume > 1) {
			this.currentVolume = 1;
		} else if (this.currentVolume < 0) {
			this.currentVolume = 0;
		}

		audioPlayer.changeVolume(this.currentVolume);

		this.playAnimation();
	}

	/**
	 * TODO Documentation
	 */
	public void playAnimation() {
		this.setText(String.format("Volume: %d%%", Math.round(this.currentVolume * 100)));
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
