package application.Core;

import application.Core.Database.Database;
import application.Core.UI.Genre;
import application.Core.UI.MainDisplay;
import application.Core.UI.Spectrum;
import application.Core.UI.SpectrumDebug;
import application.Core.UI.Updater;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	public static boolean debug = false;

	// TODO: Fix spectrum
	// TODO 2: Other background stuffs
	// TODO 2: Moar controls

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Checking for update");
		System.out.println("New update avalible: " + Updater.updateAvalible());
		if (Updater.updateAvalible()) {
			Updater.showUpdatePrompt();
		}

		if (debug) {
			System.out.println("\nDoes database exist: " + Database.databaseExist());
		}
		if (!Database.databaseExist()) {
			if (debug) {
				System.out.println("Creating Database");
			}
			Database.createDatabase();
		}
		if (debug) {
			System.out.println("Creating load bar");
		}
		MainDisplay.createPlaceholderBar();
		if (debug) {
			System.out.println("Creating specturm");
		}
		Spectrum.createSpectrum();
		if (debug) {
			System.out.println("Creating info stuff");
		}
		MainDisplay.createInfo();
		if (debug) {
			System.out.println("Setting genre");
		}
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());

		if (debug) {
			System.out.println("Displaying window");
		}
		MainDisplay.createMainStage(primaryStage);
		// primaryStage.setOpacity(.4);
		if (debug) {
			SpectrumDebug.createAndEnableDebug();
		}

	}

	public static void main(String[] args) {
		try {
			debug = Boolean.parseBoolean(args[0]);
		} catch (Exception e) {
			debug = false;
		}
		launch(args);

	}
}