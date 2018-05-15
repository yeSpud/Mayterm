package application;

import application.UI.Display;
import application.UI.Genre;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		
		Display.createLoad();
		Display.createBars();
		Display.createInfo();
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());
		
		Display.createMainStage(primaryStage);
		//primaryStage.setOpacity(.4);

	}

	public static void main(String[] args) {
		launch(args);

	}
}
