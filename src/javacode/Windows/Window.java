package javacode.Windows;

import javacode.Debugger;
import javacode.GenreColors;
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

	private static Stage stage;

	/**
	 * TODO Documentation
	 */
	private static LoadingBar loadingBar = new LoadingBar().createLoadingBar();

	/**
	 * TODO Documentation
	 */
	private static AlbumArt albumArt = new AlbumArt().createAlbumArt();

	/**
	 * TODO Documentation
	 */
	private static Monstercat cat = new Monstercat(Window.albumArt.getX(), Window.albumArt.getY());

	private static TrackInfo trackInfo = new TrackInfo();

	/**
	 * TODO Documentation
	 */
	private static TrackInfo.Title title = trackInfo.new Title();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Starting application");

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 1280, 720);

		// Setup the stage, so it can be accessed by other classes statically
		Window.stage = primaryStage;

		// Add the loading bar TODO Fix Y position
		root.getChildren().add(Window.loadingBar);

		// Add the album art TODO Fix X position
		root.getChildren().add(Window.albumArt);

		// Add the Monstercat stock art for the album art TODO Fix positioning
		root.getChildren().add(Window.cat);

		// Add the title text TODO Fix positioning
		root.getChildren().add(Window.title);

		// Add the artist text
		// TODO

		// Add a listener for key presses
		// TODO

		// TODO
		// Add genre text

		// Add the audio player and track selector
		// TODO

		// TODO
		// Add pause text

		// TODO
		// Add volume text

		primaryStage.setScene(scene);

		// Set the default background
		Window.setStageBackground(Color.BLACK);

		primaryStage.setOnCloseRequest((event -> System.exit(0)));
		primaryStage.show();
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public static void setStageBackground(Color color) {
		Debugger.d(Window.class, "Changing background color to " + color.toString());
		Scene scene = Window.stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public static void setLoadingColor(Color color) {
		Debugger.d(Window.class, "Changing loading bar color to " + color.toString());
		Window.loadingBar.setFill(color);
	}

	/**
	 * TODO Documentation
	 */
	public static void playLoadingAnimation() {
		Debugger.d(Window.class, "Playing animation");
		Window.loadingBar.playAnimation();
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public static void hideLoadingBar(boolean hide) {
		Debugger.d(Window.class, "Hiding loading bar? " + hide);
		Window.loadingBar.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param color
	 */
	public static void setAlbumArtColor(GenreColors color) {
		Debugger.d(Window.class, "Changing album art color to " + color.toString());
		Window.albumArt.setColor(color);
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public static void hideAlbumArt(boolean hide) {
		Debugger.d(Window.class, "Hiding album art? " + hide);
		Window.albumArt.setVisible(!hide);
	}

	/**
	 * TODO Documentation
	 *
	 * @param invert
	 */
	public static void invertCat(boolean invert) {
		Debugger.d(Window.class, "Inverting cat: " + invert);
		if (invert) {
			Window.cat.setBlack();
		} else {
			Window.cat.setWhite();
		}
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public static void hideCat(boolean hide) {
		Debugger.d(Window.class, "Hide cat? " + hide);
		Window.cat.setVisible(!hide);
	}
	
	/**
	 * TODO Documentation
	 *
	 * @param text
	 */
	public static void setTitle(String text) {
		Window.title.setTitle(text);
	}

	/**
	 * TODO Documentation
	 *
	 * @param hide
	 */
	public static void hideTitle(boolean hide) {
		Debugger.d(Window.class, "Hide title? " + hide);
		Window.title.setVisible(!hide);
	}

}
