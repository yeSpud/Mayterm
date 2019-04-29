package javacode.Windows;

import javacode.AudioPlayer;
import javacode.Debugger;
import javacode.UI.AlbumArt;
import javacode.UI.LoadingBar;
import javacode.UI.Monstercat;
import javacode.UI.Text.GenreText;
import javacode.UI.Text.GitHubText;
import javacode.UI.Text.PauseText;
import javacode.UI.Text.VolumeText;
import javacode.UI.TrackInfo;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends javafx.application.Application {

	public Stage stage;

	public LoadingBar loadingBar = new LoadingBar();

	public AlbumArt albumArt = new AlbumArt();

	public Monstercat cat = new Monstercat();

	private TrackInfo trackInfo = new TrackInfo();

	public TrackInfo.Title title = this.trackInfo.new Title();

	public TrackInfo.Artist artist = this.trackInfo.new Artist();

	public GitHubText gitHubText = new GitHubText();

	public GenreText genreText = new GenreText();

	public AudioPlayer player = new AudioPlayer();

	public PauseText pauseText = new PauseText();

	public VolumeText volumeText = new VolumeText();

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
		root.getChildren().add(this.gitHubText);

		// Add a listener for key presses
		scene.setOnKeyPressed(new javacode.KeyListener(this));

		// Add genre text
		root.getChildren().add(this.genreText);

		// Add the audio player and track selector
		// TODO

		// Add pause text
		root.getChildren().add(this.pauseText);

		// Add volume text
		root.getChildren().add(this.volumeText);

		primaryStage.setScene(scene);

		// Set the default background
		this.setStageBackground(Color.BLACK);

		// TODO Add a way to update the position of all elements on a screen size adjustments
		javafx.beans.value.ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			double width = this.stage.getWidth(), height = this.stage.getHeight();
			Debugger.d(this.getClass(), "New stage height: " + height);
			Debugger.d(this.getClass(), "New stage width: " + width);

			// Setup all the relative positioning
			this.gitHubText.setPosition(width, height);
			this.pauseText.updatePosition(width, height);
			this.volumeText.updatePosition(width, height);
		};
		this.stage.widthProperty().addListener(stageSizeListener);
		this.stage.heightProperty().addListener(stageSizeListener);

		primaryStage.setOnCloseRequest((event -> System.exit(0)));
		Debugger.d(this.getClass(), "Showing stage");
		primaryStage.show();


	}

	/**
	 * TODO Documentation
	 *
	 * @param opacity
	 */
	public void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %d%%", Math.round(opacity * 100)));
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
		root.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(color, javafx.scene.layout.CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
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
	 * @param node
	 * @param hide
	 */
	public void hideElement(javafx.scene.Node node, boolean hide) {
		Debugger.d(this.getClass(), String.format("Hide %s? %s", node.toString(), hide));
		node.setVisible(!hide);
	}

}
