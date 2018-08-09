package javacode.UI;

import javacode.Audio.AudioPlayer;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Volume {

	private static Text volume = new Text(240, 700, "Volume: 75%");
	public static double volumeValue = 0.75d;
	
	public static void setup() {
		volume.setFont(Font.font(20));
		volume.setRotate(180);
		volume.setFill(Color.WHITE);
		volume.setOpacity(0);
		MainDisplay.root.getChildren().add(volume);
	}
	
	public static void adjustVolume(double value) {
		volumeValue = Double.parseDouble(String.format("%.2f", volumeValue + value));
		if (volumeValue > 1) {
			volumeValue = 1;
		}
		if (volumeValue < 0) {
			volumeValue = 0;
		}
		AudioPlayer.player.setVolume(volumeValue);
		volume.setText(String.format("Volume: %d%%", (int) (volumeValue * 100)));
		FadeTransition volumeFade = new FadeTransition(Duration.millis(3000), volume);
		volumeFade.setFromValue(1);
		volumeFade.setToValue(0);
		volumeFade.play();
	}
	
}
