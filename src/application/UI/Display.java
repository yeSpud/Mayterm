package application.UI;

import java.io.IOException;

import application.Main;
import application.Audio.AudioPlayer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Display {

	public static Text author = new Text("No file currently selected"/*"TRISTAM & BRAKEN"*/),
			
			title = new Text("Press \"O\" to select a file"/*"FRAME OF MIND"*/), volumeHUD = new Text(240, 700, "Volume: 75%");

	public static BorderPane root = new BorderPane();

	public static Scene scene = new Scene(root, 1280, 720, Color.BLACK);
	
	public static final int BAND_COUNT = 63;
	public static final double STARTING_FREQUENCY = 250.0;

	public static int space = (int) ((scene.getWidth() - ((7 * 63) * 1.95)) / 2);

	public static Group bars = new Group();

	public static Rectangle nothing = new Rectangle(115, 356, 1046, 2);

	public static Rectangle art = new Rectangle(1034, 198, 126, 126);

	public static ImageView coverArt = new ImageView(), cat = new ImageView();

	public static void createMainStage(Stage primaryStage) {
		try {

			Main.mainStage = primaryStage;

			Main a = new Main();

			scene.getStylesheets().add(a.getClass().getResource("application.css").toExternalForm());
			Main.mainStage.setScene(scene);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {

					Display.keyPresed(event.getCode());

				}

			});

			Main.mainStage.setResizable(false);
			Main.mainStage.show();

			root.getStyleClass().add("stage");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createBars() {

		for (int i = 0; i < 63; i++) {
			Rectangle rectangle = new Rectangle(115 + (i * (16.65)), 356, 12, 2); // 1.9453968254
			rectangle.setStrokeType(StrokeType.CENTERED);
			bars.getChildren().add(rectangle);
 
		}

	}

	public static void createLoad() {

		nothing.setStrokeType(StrokeType.CENTERED);
		nothing.setStroke(Color.WHITE);
		nothing.setStrokeWidth(2);
		nothing.fillProperty();
	}

	public static void createInfo() {

		root.getChildren().add(art);
		
		cat.setX(1034);
		cat.setY(198);
		cat.setFitHeight(126);
		cat.setFitWidth(126);
		cat.setRotate(180);
		Main a = new Main();
		Image catLogo = null;
		try {
			catLogo = new Image(a.getClass().getResource("mcatTransparent.png").openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cat.setImage(catLogo);
		root.getChildren().add(cat);

		author.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 68.75));
		author.setX(1012 - author.getLayoutBounds().getWidth());
		author.setY(306);
		author.setRotate(180);
		author.setFill(Color.WHITE);
		author.setId("artist");
		root.getChildren().add(author);

		title.setFont(Font.font("Arial", 35));
		title.setX(1012 - title.getLayoutBounds().getWidth());
		title.setY(244);
		title.setRotate(180);
		title.setFill(Color.WHITE);
		title.setId("title");
		root.getChildren().add(title);

		volumeHUD.setFont(Font.font(20));
		volumeHUD.setRotate(180);
		volumeHUD.setFill(Color.WHITE);
		volumeHUD.setOpacity(0);
		root.getChildren().add(volumeHUD);

		root.getChildren().add(Display.nothing);
	}

	public static void keyPresed(KeyCode key) {
		if (key.equals(KeyCode.O)) {
			/* Pick a song from local storage */
			AudioPlayer.pickSong();
		} else if (key.equals(KeyCode.P)) {
			/* Pause/Unpasue */
			AudioPlayer.pause();
		} else if (AudioPlayer.isPlaying && !AudioPlayer.isPaused) {
			if (key.equals(KeyCode.UP)) {
				/* Increase volume */
				AudioPlayer.handleVolume(0.05d);
			} else if (key.equals(KeyCode.DOWN)) {
				/* Decrase volume */
				AudioPlayer.handleVolume(-0.05d);
			} else if (key.equals(KeyCode.RIGHT)) {
				/* Skip current track*/
				AudioPlayer.skip();
			} else {
				System.out.println(key);
			}
		}
	}

	public static void setGenre(Color genre) {
		
		nothing.setFill(genre);
		nothing.setStroke(genre);
		art.setFill(genre);
		for (int i = 0; i < 63; i++) {
			Rectangle bar = (Rectangle) bars.getChildren().get(i);
			bar.setFill(genre);
		}
		
	}
	
	public static void setCoverArt(Image image) {
		coverArt.setX(1034);
		coverArt.setY(198);
		coverArt.setImage(image);
		coverArt.setFitHeight(126);
		coverArt.setFitWidth(126);
		coverArt.setRotate(180);
		if (!root.getChildren().contains(coverArt)) {
			root.getChildren().add(coverArt);
		}
		
	}
	
	/*
	public static void createSpectrumBars() {
		//spectrumBars = new SpectrumBar[BAND_COUNT];
		final ObservableList<EqualizerBand> bands = AudioPlayer.player.getAudioEqualizer().getBands();
		bands.clear();
		double min = EqualizerBand.MIN_GAIN, max = EqualizerBand.MAX_GAIN, mid = (min-max)/2, frequency = STARTING_FREQUENCY;
		
		for (int j = 0; j < BAND_COUNT; j++) {
			double theta = (double)j/(double) (BAND_COUNT - 1) * (2*Math.PI), scale = 0.4 * (1+Math.cos(theta)), gain = min + max + (mid * scale);
			bands.add(new EqualizerBand(frequency, frequency/2, gain));
			frequency *= 2;
		}
		// System.out.println(bands.size()); // 63
	}
	*/
	
}
