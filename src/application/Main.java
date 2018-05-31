package application;

import application.Database.Database;
import application.SpectrumThings.Spectrum;
import application.SpectrumThings.SpectrumDebug;
import application.UI.Genre;
import application.UI.MainDisplay;
import application.UI.Updater;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	// TODO: Fix spectrum
	// TODO 2: Other background stuffs
	// TODO 2: Moar controlls

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Checking for update");
		System.out.println("New update avalible: " + Updater.updateAvalible());

		System.out.println("\nDoes database exist: " + Database.databaseExist());
		if (!Database.databaseExist()) {
			System.out.println("Creating Database");
			Database.createDatabase();
		}
		System.out.println("Creating load bar");
		MainDisplay.createLoad();
		System.out.println("Creating specturm");
		Spectrum.createSpectrum();
		System.out.println("Creating info stuff");
		MainDisplay.createInfo();
		System.out.println("Setting genre");
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());

		System.out.println("Displaying window");
		MainDisplay.createMainStage(primaryStage);
		// primaryStage.setOpacity(.4);

		//SpectrumDebug.createAndEnableDebug();

	}

	public static void main(String[] args) {
		launch(args);

	}
}
