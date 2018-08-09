package javacode.UI;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PauseText {
	
	private static Text pause = new Text("Press \"P\" to pause and unpause");
	
	
	public static void setup() {
		pause.setFont(Font.font(15));
		pause.setX(1260 - pause.getLayoutBounds().getWidth());
		pause.setY(10 + pause.getLayoutBounds().getHeight());
		pause.setRotate(180);
		pause.setFill(Color.WHITE);
		pause.setOpacity(0);
		MainDisplay.root.getChildren().add(pause);
	}
	
	public static void fade() {
		FadeTransition pauseFade = new FadeTransition(Duration.millis(10000), pause);
		pauseFade.setFromValue(1);
		pauseFade.setToValue(0);
		pauseFade.play();
	}
}
