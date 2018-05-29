package application.UI;

import application.Main;
import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class MainDisplay {

	public static BorderPane root = new BorderPane();

	public static Scene scene = new Scene(root, 1280, 720, Color.BLACK);

	public static final int BAND_COUNT = 63;
	public static final double STARTING_FREQUENCY = 250.0;

	public static int space = (int) ((scene.getWidth() - ((7 * 63) * 1.95)) / 2), rotatoe = 4;

	public static Rectangle nothing = new Rectangle(115, 356, 1046, 2);

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

	public static void createLoad() {

		nothing.setStrokeType(StrokeType.CENTERED);
		nothing.setStroke(Color.WHITE);
		nothing.setStrokeWidth(2);
		nothing.fillProperty();
	}

	public static void createInfo() {

		DisplayText.setupText();
		CoverArt.createCoverArt();
		root.getChildren().add(MainDisplay.nothing);
	}

	public static void keyPresed(KeyCode key) {
		if (key.equals(KeyCode.O)) {
			/* Pick a song from local storage */
			AudioFile.pickSong();
		} else if (key.equals(KeyCode.P)) {
			/* Pause/Unpasue */
			AudioPlayer.pause();
		} else if (key.equals(KeyCode.SEMICOLON)) {
			/* Settings */
			// SettingsDisplay.createAndShowSettings();
			// TODO: Finish settings
		} else if (AudioPlayer.isPlaying && !AudioPlayer.isPaused) {
			if (key.equals(KeyCode.UP)) {
				/* Increase volume */
				DisplayText.handleVolume(0.05d);
			} else if (key.equals(KeyCode.DOWN)) {
				/* Decrase volume */
				DisplayText.handleVolume(-0.05d);
			} else if (key.equals(KeyCode.RIGHT)) {
				/* Skip current track */
				AudioPlayer.skip();
			} else if (key.equals(KeyCode.PERIOD)) {
				/* Rotate current genre */
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
				// TODO: Finish this
			}
		} else {
			System.out.println(key);
		}

	}

}
