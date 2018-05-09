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
		/*
		 * for (int i = 0; i < 7; i++) { Group tests = ((Group) Display.bars); Rectangle
		 * test = (Rectangle) tests.getChildren().get(i * 9); test.setHeight(59);
		 * //test.setHeight = -60; }
		 */
		
		Display.createMainStage(primaryStage);
		//primaryStage.setOpacity(.4);

		// pickSong();

	}

	public static void main(String[] args) {
		launch(args);

	}
}
