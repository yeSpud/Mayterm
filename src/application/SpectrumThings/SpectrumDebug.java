package application.SpectrumThings;

import application.UI.MainDisplay;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SpectrumDebug {

	public static Group spectrumText = new Group();

	/**
	 * Creates the display text for the debug values for the spectrum, and then
	 * makes them visible when a track plays.
	 */
	public static void createAndEnableDebug() {
		for (int i = 0; i < 63; i++) {
			Text debug = new Text();
			debug.setFont(Font.font(10));
			debug.setRotate(120);
			debug.setFill(Color.WHITE);
			spectrumText.getChildren().add(debug);
		}
		MainDisplay.root.getChildren().add(spectrumText);
	}

}
