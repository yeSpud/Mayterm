package javacode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public boolean debug = false;

	@Override
	public void start(Stage stage) {

		
		// Check for updates
		System.out.println("Checking for update");
		System.out.println("New update avalible: " + Updater.updateAvalible());
		if (Updater.updateAvalible()) {
			Updater.showUpdatePrompt();
		}
		
		// We need an anchor pane go help automatically constrain the maximum and minimum sizes of things
		AnchorPane anchor = new AnchorPane();
		
		
		// Set the scene for the visualizer, use the anchor pane defined above
		Scene mCatScene = new Scene(anchor);
		
		
		// Set the contents of the window to that of the scene
		stage.setScene(mCatScene);
		
		
		// Show the window (stage)
		stage.show();

	}

	public static void main(String[] args) {
		
		Main This = new Main();
		
		// Check if debug mode is enabled (Basically check for console spam enabled)
		try {
			This.debug = Boolean.parseBoolean(args[0]);
		} catch (Exception e) {
			This.debug = false;
		}
		
		
		launch(args);

	}
}
