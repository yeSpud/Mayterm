package application;

import application.UI.VisulizerDisplay;
import application.Audio.Spectrum;
import application.UI.Genre;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		
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
