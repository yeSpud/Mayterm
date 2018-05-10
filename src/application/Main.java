package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		
		Display.createLoad();
		Display.createBars();
		Display.createInfo();
		
		Display.createMainStage(primaryStage);
		//primaryStage.setOpacity(.4);

	}

	public static void main(String[] args) {
		launch(args);

	}
}
