package javacode.UI.Text;

import javacode.AudioPlayer;
import javacode.Debugger;
import javafx.animation.FadeTransition;

/**
 * Create the volume text that shows the current volume of the player after it has been adjusted, then fade away.
 *
 * @author Spud
 */
public class VolumeText extends javafx.scene.text.Text implements javacode.UI.RelativeNode {

	/**
	 * The volume percentage (default is 75%).
	 */
	public double currentVolume = 0.75d;

	/**
	 * The fade animation that is played after the volume has been changed.
	 * This applies to the VolumeText node, and lasts for 3 seconds.
	 */
	private final FadeTransition animation = new FadeTransition(javafx.util.Duration.millis(3000), this);

	public VolumeText() {
		this.setText(String.format("Volume: %d%%", Math.round(this.currentVolume * 100)));
		this.setFont(javafx.scene.text.Font.font(20));
		this.setFill(javafx.scene.paint.Color.WHITE);
		this.updateOpacity(0);
	}

	@Override
	public void updatePosition(double width, double height) {
		double x = width - (this.getLayoutBounds().getWidth() + (width / 4.5)), y = this.getLayoutBounds().getHeight();
		Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Adjusts the volume by the increment amount.
	 *
	 * @param increment The value to adjust the volume by. This should be negative to decrease the volume.
	 * @param audioPlayer The audio player this volume value applies to.
	 */
	public void adjustVolume(double increment, AudioPlayer audioPlayer) {
		this.setVolume(this.currentVolume += increment, audioPlayer);
	}

	/**
	 * Sets the volume percentage of the audio player.
	 *
	 * @param volume The volume percentage value (from 0 to 1.0).
	 * @param audioPlayer The audio player this applies to.
	 */
	public void setVolume(double volume, AudioPlayer audioPlayer) {
		this.currentVolume = volume;
		Debugger.d(this.getClass(), String.format("Setting volume to: %.2f", this.currentVolume));

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
	 * Plays the fade animation.
	 */
	public void playAnimation() {
		this.setText(String.format("Volume: %d%%", Math.round(this.currentVolume * 100)));
		this.animation.setFromValue(1);
		this.animation.setToValue(0);
		this.animation.playFromStart();
	}

	/**
	 * Updates the opacity the volume text to the provided argument.
	 *
	 * @param opacity The opacity as a value from 0 to 1.0.
	 */
	public void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %d%%", Math.round(opacity * 100)));
		this.setOpacity(opacity);
	}

}
