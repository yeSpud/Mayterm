package javacode;

import javacode.Database.Database;
import javacode.Errors.JSExecutionError;
import javacode.UI.Artist;
import javacode.UI.CoverArt;
import javacode.UI.Genre;
import javacode.UI.GitHubLink;
import javacode.UI.MainDisplay;
import javacode.UI.Menu;
import javacode.UI.PauseText;
import javacode.UI.Settings;
import javacode.UI.Spectrum;
import javacode.UI.SpectrumDebug;
import javacode.UI.Title;
import javacode.UI.Volume;
import javacode.javascript.javascriptRunner;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	public static boolean debug = false;

	// TODO: Fix spectrum
	// TODO: Fix background from changing to white
	// TODO 2: Other background stuffs
	// TODO 2: Moar controls

	@Override
	public void start(Stage primaryStage) {

		System.out.println("Checking for update");
		System.out.println("New update avalible: " + Updater.updateAvalible());
		if (Updater.updateAvalible()) {
			Updater.showUpdatePrompt();
		}

		
		/*
		Debugger.d(getClass(), "Checking JavaScript");
		try {
			javascriptRunner.vaidateJs();
		} catch (JSExecutionError e) {
			e.printStackTrace();
		}
		*/
		
		Debugger.d(this.getClass(), "Does database exist: " + Database.databaseExist());

		if (!Database.databaseExist()) {
			Debugger.d(this.getClass(), "Creating Database");
			Database.createDatabase();
		}

		Settings.checkSettings();

		Debugger.d(this.getClass(), "Creating placeholder bar");
		MainDisplay.createPlaceholderBar();

		Debugger.d(this.getClass(), "Creating specturm");
		Spectrum.createSpectrum();

		Debugger.d(this.getClass(), "Creating title");
		Title.setup();

		Debugger.d(this.getClass(), "Creating artist");
		Artist.setup();

		Debugger.d(this.getClass(), "Creating volume handlers");
		Volume.setup();

		Debugger.d(this.getClass(), "Creating pause text");
		PauseText.setup();

		Debugger.d(this.getClass(), "Creating GitHub link");
		GitHubLink.setup();

		Debugger.d(this.getClass(), "Creating cover art");
		CoverArt.setup();

		Debugger.d(this.getClass(), "Setting genre");
		Genre.setGenre(Genre.genre.ELECTRONIC.getColor());

		Debugger.d(this.getClass(), "Creating settings page");
		Settings.setup();

		Debugger.d(this.getClass(), "Setting option for settings");
		Settings.setDefaultEnabled(
				Integer.parseInt(Database.getSettings().toString().replace("[", "").replace("]", "")));

		Debugger.d(this.getClass(), "Enabling settings buttons");
		Settings.enableButtons(true);

		Debugger.d(getClass(), "Setting menu bar");
		Menu.setup();

		Debugger.d(this.getClass(), "Setting option for menu bar");
		Menu.setDefaultEnabled(Integer.parseInt(Database.getSettings().toString().replace("[", "").replace("]", "")));

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
