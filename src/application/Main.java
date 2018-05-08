package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		Display.createLoad();
		Display.createBars();
		Display.root.getChildren().add(Display.nothing);
		Display.createMainStage(primaryStage);
		
		//pickSong();

	}

	public static void main(String[] args) {
		launch(args);
		
	}
}
