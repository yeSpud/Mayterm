package javacode.Windows;

import javacode.Debugger;
import javacode.KeyListener;
import javacode.UI.AlbumArt;
import javacode.UI.LoadingBar;
import javacode.UI.Monstercat;
import javacode.UI.TrackInfo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	public Stage stage;

	public LoadingBar loadingBar = new LoadingBar();

	public AlbumArt albumArt = new AlbumArt();

	public Monstercat cat = new Monstercat();

	private TrackInfo trackInfo = new TrackInfo();

	public TrackInfo.Title title = this.trackInfo.new Title();

	public TrackInfo.Artist artist = this.trackInfo.new Artist();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Starting application");

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 1280, 720);

		// Setup the stage, so it can be accessed by other classes statically
		this.stage = primaryStage;

		// Add the loading bar
		root.getChildren().add(this.loadingBar);

		// Add the album art
		root.getChildren().add(this.albumArt);

		// Add the Monstercat stock art for the album art
		root.getChildren().add(this.cat);

		// Add the title text
		root.getChildren().add(this.title);

		// Add the artist text
		root.getChildren().add(this.artist);

		// Add the github link
		// TODO

		// Add a listener for key presses
		scene.setOnKeyPressed(new KeyListener(this));

		// TODO
		// Add genre text

		// Add the audio player and track selector
		// TODO

		// Add pause text
		// TODO

		// Add volume text
		// TODO

		primaryStage.setScene(scene);

		// Set the default background
		this.setStageBackground(Color.BLACK);

		primaryStage.setOnCloseRequest((event -> System.exit(0)));
		primaryStage.show();
	}

	/**
	 * TODO Documentation
	 *
	 * @param opacity
	 */
	public void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %.2f%%", opacity));
		this.stage.setOpacity(opacity);
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public void setStageBackground(Color color) {
		Debugger.d(this.getClass(), "Changing background color to " + color.toString());
		Scene scene = this.stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public void hideLoadingBar(boolean hide) {
		Debugger.d(this.getClass(), "Hiding loading bar? " + hide);
		this.loadingBar.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public void hideAlbumArt(boolean hide) {
		Debugger.d(this.getClass(), "Hiding album art? " + hide);
		this.albumArt.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param invert
	 */
	public void invertCat(boolean invert) {
		Debugger.d(Window.class, "Inverting cat: " + invert);
		if (invert) {
			this.cat.setBlack();
		} else {
			this.cat.setWhite();
		}
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public void hideCat(boolean hide) {
		Debugger.d(this.getClass(), "Hide cat? " + hide);
		this.cat.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public void hideTitle(boolean hide) {
		Debugger.d(this.getClass(), "Hide title? " + hide);
		this.title.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public void hideArtist(boolean hide) {
		Debugger.d(this.getClass(), "Hide artist? " + hide);
		this.artist.setVisible(!hide);
	}

}
