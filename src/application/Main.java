package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		stages.createMainStage(primaryStage);
		//pickSong();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
