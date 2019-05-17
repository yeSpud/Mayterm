package javacode.Windows;

import javacode.AudioPlayer;
import javacode.Debugger;
import javacode.UI.*;
import javacode.UI.Text.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends javafx.application.Application {

	public Stage stage;

	public LoadingBar loadingBar = new LoadingBar();

	public Bar[] bars = new Bar[63];

	public AlbumArt albumArt = new AlbumArt();

	public Monstercat cat = new Monstercat();

	private TrackInfo trackInfo = new TrackInfo();

	public TrackInfo.Title title = this.trackInfo.new Title();

	public TrackInfo.Artist artist = this.trackInfo.new Artist();

	public GitHubText gitHubText = new GitHubText();

	public GenreText genreText = new GenreText();

	public AudioPlayer player = new AudioPlayer(this);

	public PauseText pauseText = new PauseText();

	public VolumeText volumeText = new VolumeText();

	public static ErrorPrompt errorPrompt = new ErrorPrompt();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Starting application");

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 1280, 720);

		// TODO set a minimum size

		// Setup the stage, so it can be accessed by other classes statically
		this.stage = primaryStage;

		// Add the loading bar
		root.getChildren().add(this.loadingBar);

		// Initialize the bars
		for (int i = 0; i < this.bars.length; i++) {
			this.bars[i] = new Bar();
		}

		// Add the main bars, and then hide them
		for (Bar bar : this.bars) {
			root.getChildren().add(bar);
			this.hideElement(bar, true);
		}

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
		// FIXME
		//root.getChildren().add(this.genreText);

		// Add pause text
		root.getChildren().add(this.pauseText);

		// Add volume text
		root.getChildren().add(this.volumeText);

		// Add error stuff
		root.getChildren().add(Window.errorPrompt);

		primaryStage.setScene(scene);

		// Set the default background
		this.setStageBackground(Color.BLACK);

		// Update the position of all elements on a screen size adjustments
		javafx.beans.value.ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			double width = this.stage.getWidth(), height = this.stage.getHeight();
			Debugger.d(this.getClass(), "New stage height: " + height);
			Debugger.d(this.getClass(), "New stage width: " + width);

			// Setup all the relative positioning
			this.gitHubText.updatePosition(width, height);
			this.pauseText.updatePosition(width, height);
			this.volumeText.updatePosition(width, height);

			// Update bar positioning
			for (int i = 0; i < this.bars.length; i++) {
				this.bars[i].updatePosition(width, height, i);
			}

		};
		this.stage.widthProperty().addListener(stageSizeListener);
		this.stage.heightProperty().addListener(stageSizeListener);

		primaryStage.setOnCloseRequest((event -> System.exit(0)));
		Debugger.d(this.getClass(), "Showing stage");
		primaryStage.show();

		Debugger.d(this.getClass(), "Error location: " + Window.errorPrompt.getLayoutX() + ", " + Window.errorPrompt.getLayoutY());

	}

	/**
	 * Updates the window opacity.
	 *
	 * @param opacity The opacity value from 0 to 1.0.
	 */
	void updateOpacity(double opacity) {
		Debugger.d(this.getClass(), String.format("Setting opacity to %d%%", Math.round(opacity * 100)));
		this.stage.setOpacity(opacity);
	}

	/**
	 * Changes the background color of the window.
	 *
	 * @param color The color to set the background to.
	 */
	void setStageBackground(Color color) {
		Debugger.d(this.getClass(), "Changing background color to " + color.toString());
		Scene scene = this.stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		root.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(color, javafx.scene.layout.CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
	}

	/**
	 * Helper function to set the fill of the cat to either white or black. Default is white. Inverted is black.
	 *
	 * @param invert Whether or not to invert the cat.
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
	 * Helper function to toggle the visibility a specific node in the window, effectively hiding or showing it.
	 *
	 * @param node The node to apply the visibility changes to
	 * @param hide Whether or not to hide the node.
	 */
	public void hideElement(javafx.scene.Node node, boolean hide) {
		Debugger.d(this.getClass(), String.format("Hide %s? %s", node.toString(), hide));
		node.setVisible(!hide);
	}


}
