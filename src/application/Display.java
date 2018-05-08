package application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class Display {

	public static String autor, title;

	public static BorderPane root = new BorderPane();

	public static Scene scene = new Scene(root, 1280, 720, Color.BLACK);

	public static int space = (int) ((scene.getWidth() - ((7 * 63) * 1.95)) / 2);
	
	public static Group bars = new Group();
	
	public static Rectangle nothing = new Rectangle(space, scene.getHeight() / 2, 846.3, 2);

	public static void createMainStage(Stage primaryStage) {
		try {

			Main.mainStage = primaryStage;

			Main a = new Main();
			
			scene.getStylesheets().add(a.getClass().getResource("application.css").toExternalForm());
			Main.mainStage.setScene(scene);

			// root.getChildren().add(bars);
			
			// root.getChildren().add(nothing);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {

					Display.keyPresed(event.getCode());

				}

			});

			Main.mainStage.setResizable(false);
			Main.mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createInfoStage(Stage InfoStage) {

	}

	public static void createBars() {

		for (int i = 0; i < 63; i++) {
			Rectangle rectangle = new Rectangle(space + (7 * i) * (1.95), scene.getHeight() / 2, 2, 0); // 1.9453968254
			rectangle.setStrokeType(StrokeType.OUTSIDE);
			rectangle.setStroke(genre.OTHER.getColor());
			rectangle.setStrokeWidth(4);
			rectangle.setFill(rectangle.getStroke());
			bars.getChildren().add(rectangle);
			//bars.setRotate(180);
			
		}

	}

	public static void createLoad() {
		
		nothing.setStrokeType(StrokeType.CENTERED);
		nothing.setStroke(genre.OTHER.getColor());
		nothing.setStrokeWidth(2);
		nothing.fillProperty();
	}
	
	public static void keyPresed(KeyCode key) {
		if (key.equals(KeyCode.O)) {
			AudioPlayer.pickSong();
		} else if (key.equals(KeyCode.P)) {
			if (AudioPlayer.isPlaying) {
				AudioPlayer.player.pause();
				AudioPlayer.isPlaying = false;
			} else {
				try {
					AudioPlayer.player.play();
					AudioPlayer.isPlaying = true;
				} catch (NullPointerException a) {
					// foo
				}
			}
		} else if (AudioPlayer.isPlaying) {
			if (key.equals(KeyCode.UP)) {
				AudioPlayer.volume = AudioPlayer.volume + 0.05d;
				if (AudioPlayer.volume > 1) {
					AudioPlayer.volume = 1;
				}
				AudioPlayer.player.setVolume(AudioPlayer.volume);
				System.out.println(AudioPlayer.player.getVolume());
			} else if (key.equals(KeyCode.DOWN)) {
				AudioPlayer.volume = AudioPlayer.volume - 0.05d;
				if (AudioPlayer.volume < 0) {
					AudioPlayer.volume = 0;
				}
				AudioPlayer.player.setVolume(AudioPlayer.volume);
				System.out.println(AudioPlayer.player.getVolume());
			} else {
				System.out.println(key);
			}
		}
	}

}
