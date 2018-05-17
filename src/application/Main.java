package application;

import application.Audio.Spectrum;
import application.Database.Database;
import application.UI.Genre;
import application.UI.VisulizerDisplay;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		System.out.println(Database.databaseExist());
		if (!Database.databaseExist()) {
			System.out.println("Creating Database");
			Database.createDatabase();
		}
		VisulizerDisplay.createLoad();
		Spectrum.createSpectrum();
		VisulizerDisplay.createInfo();
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());
		
		VisulizerDisplay.createMainStage(primaryStage);
		//primaryStage.setOpacity(.4);

	}

	public static void main(String[] args) {
		launch(args);

	}
}
