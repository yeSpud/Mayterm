package application.Core.UI;

import application.Core.Main;
import application.Core.Audio.AudioFile;
import application.Core.Audio.AudioPlayer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 * Class responsible for the main display, and key commands.
 * 
 * @author Spud
 *
 */
public class MainDisplay {

	public static BorderPane root = new BorderPane();

	public static Scene scene = new Scene(root, 1280, 720, Color.BLACK);

	public static final int BAND_COUNT = 63;
	public static final double STARTING_FREQUENCY = 250.0;

	public static int space = (int) ((scene.getWidth() - ((7 * 63) * 1.95)) / 2), rotatoe = 4;

	public static Rectangle nothing = new Rectangle(115, 356, 1046, 2);

	/**
	 * Creates the main stage of the display.
	 * 
	 * @param primaryStage
	 *            - The JavaFX stage.
	 */
	public static void createMainStage(Stage primaryStage) {
		try {
			Main.mainStage = primaryStage;
			Main a = new Main();
			scene.getStylesheets().add(a.getClass().getResource("Resources/application.css").toExternalForm());
			Main.mainStage.setScene(scene);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					MainDisplay.keyPresed(event.getCode());
				}
			});
			Main.mainStage.setResizable(false);
			Main.mainStage.show();
			root.getStyleClass().add("stage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the placeholder bar for when the spectrum is disabled.
	 */
	public static void createPlaceholderBar() {
		nothing.setStrokeType(StrokeType.CENTERED);
		nothing.setStroke(Color.WHITE);
		nothing.setStrokeWidth(2);
		nothing.fillProperty();
	}

	/**
	 * Creates the info for the display, so things like display text, and the cover
	 * art.
	 */
	public static void createInfo() {
		Title.setup();
		Artist.setup();
		Volume.setup();
		PauseText.setup();
		GitHubLink.setup();
		CoverArt.createCoverArt();
		root.getChildren().add(MainDisplay.nothing);
	}

	/**
	 * Handles key presses as commands. The currently list of keys are:<br>
	 * <code>O</code> - Opens file picker for playing track.<br>
	 * <code>P</code> - Pause/Unpause.<br>
	 * <code>;</code> - Opens the settings page (To be finished).<br>
	 * <code>ESC</code> - Quits the program.<br>
	 * <br>
	 * <i>The rest of these command need to be executed while a track is being
	 * played</i><br >
	 * <br>
	 * <code>UP arrow</code> - Increases volume.<br>
	 * <code>DOWN arrow</code>- Decreases volume.<br>
	 * <code>RIGHT arrow</code> - Skips track.<br>
	 * <code>.</code> or <code>,</code> - Rotates though genres.<br>
	 * <code>LEFT arrow</code> - restarts the track (To be finished).
	 * 
	 * @param key
	 *            - The key that was pressed.
	 */
	public static void keyPresed(KeyCode key) {
		if (key.equals(KeyCode.O)) {
			/* Pick a song from local storage */
			AudioFile.pickSong();
		} else if (key.equals(KeyCode.P)) {
			/* Pause/Unpause */
			AudioPlayer.pause();
		} else if (key.equals(KeyCode.SEMICOLON)) {
			/* Settings */
			// SettingsDisplay.createAndShowSettings();
			// TODO 2: Finish settings
		} else if (key.equals(KeyCode.ESCAPE)) {
			System.exit(0);
		} else if (AudioPlayer.isPlaying && !AudioPlayer.isPaused) {
			if (key.equals(KeyCode.UP)) {
				/* Increase volume */
				Volume.adjustVolume(0.05d);
			} else if (key.equals(KeyCode.DOWN)) {
				/* Decrease volume */
				Volume.adjustVolume(-0.05d);
			} else if (key.equals(KeyCode.RIGHT)) {
				/* Skip current track */
				AudioPlayer.skip();
			} else if (key.equals(KeyCode.PERIOD)) {
				/* Rotate current genre */
				// TODO: Fix genre rotate
				rotatoe++;
				if (rotatoe > 12) {
					rotatoe = 0;
				}
				if (rotatoe < 0) {
					rotatoe = 12;
				}
				Genre.rotateGenre(rotatoe);
			} else if (key.equals(KeyCode.COMMA)) {
				/* Rotate current genre */
				// TODO: Fix genre rotate
				rotatoe--;
				if (rotatoe > 12) {
					rotatoe = 0;
				}
				if (rotatoe < 0) {
					rotatoe = 12;
				}
				Genre.rotateGenre(rotatoe);
			} else if (key.equals(KeyCode.LEFT)) {
				/* Restart track */
				// TODO 2: Finish this
			}
		} else {
			System.out.println(key);
		}

	}

}
