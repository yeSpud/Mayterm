package application.Core;

import application.Core.Database.Database;
import application.Core.UI.Genre;
import application.Core.UI.MainDisplay;
import application.Core.UI.Spectrum;
import application.Core.UI.SpectrumDebug;
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

		Debugger.d(this.getClass(), "Does database exist: " + Database.databaseExist());
		
		if (!Database.databaseExist()) {
			Debugger.d(this.getClass(), "Creating Database");
			Database.createDatabase();
		}
		
		Debugger.d(this.getClass(), "Creating load bar");
		MainDisplay.createPlaceholderBar();
		
		
		Debugger.d(this.getClass(), "Creating specturm");
		Spectrum.createSpectrum();
		
		Debugger.d(this.getClass(), "Creating info stuff");
		MainDisplay.createInfo();
		
		Debugger.d(this.getClass(), "Setting genre");
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());

		Debugger.d(this.getClass(), "Displaying window");
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
