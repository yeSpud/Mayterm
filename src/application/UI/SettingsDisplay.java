package application.UI;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SettingsDisplay {

	public static BorderPane settingsroot = new BorderPane();
	
	public static boolean isDisplayed = false;

	public static Scene scene = new Scene(settingsroot, 500, 500, Color.WHITE);
	
	public static void createAndShowSettings() {
		Stage settings = new Stage();
		settings.setScene(scene);
		settings.setTitle("Settings");
		settings.setOpacity(0.8d);
		settings.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				isDisplayed = false;
				settings.close();
				
			}
			
		});
		
		
		settings.show();
		isDisplayed = true;
	}
	
}
