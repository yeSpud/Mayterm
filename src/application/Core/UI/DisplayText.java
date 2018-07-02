package application.Core.UI;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import application.Audio.AudioFile;
import application.Audio.AudioPlayer;
import application.Audio.getMetadata;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Class responsible for the text on the scene. This includes the volume HUD,
 * Pause HUD, Artist, Title, and more!
 * 
 * @author Spud
 *
 */
public class DisplayText {

	static Text author = new Text("No file currently selected"/* "TRISTAM & BRAKEN" */),
			title = new Text("Press \"O\" to select a file"/* "FRAME OF MIND" */),
			volumeHUD = new Text(240, 700, "Volume: 75%"), pauseInfo = new Text("Press \"P\" to pause and unpause"),
			gitLink = new Text("GitHub");
	static final double authorSize = 68.75d, titleSize = 35, volumeSize = 20, otherSize = 15;

	public static double volume = 0.75d;

	/**
	 * Sets the text and font for the volume HUD, pause HUD, GitHub link, artist,
	 * and title text.
	 */
	public static void setupText() {
		author.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, authorSize));
		author.setX(1012 - author.getLayoutBounds().getWidth());
		author.setY(306);
		author.setRotate(180);
		author.setFill(Color.WHITE);
		MainDisplay.root.getChildren().add(author);
		title.setFont(Font.font("Arial", titleSize));
		title.setX(1012 - title.getLayoutBounds().getWidth());
		title.setY(244);
		title.setRotate(180);
		title.setFill(Color.WHITE);
		MainDisplay.root.getChildren().add(title);
		volumeHUD.setFont(Font.font(volumeSize));
		volumeHUD.setRotate(180);
		volumeHUD.setFill(Color.WHITE);
		volumeHUD.setOpacity(0);
		MainDisplay.root.getChildren().add(volumeHUD);
		pauseInfo.setFont(Font.font(otherSize));
		pauseInfo.setX(1260 - pauseInfo.getLayoutBounds().getWidth());
		pauseInfo.setY(10 + pauseInfo.getLayoutBounds().getHeight());
		pauseInfo.setRotate(180);
		pauseInfo.setFill(Color.WHITE);
		pauseInfo.setOpacity(0);
		MainDisplay.root.getChildren().add(pauseInfo);
		gitLink.setFont(Font.font(otherSize));
		gitLink.setUnderline(true);
		gitLink.setX(20);
		gitLink.setY(10 + gitLink.getLayoutBounds().getHeight());
		gitLink.setRotate(180);
		gitLink.setFill(Color.WHITE);
		gitLink.setCursor(Cursor.HAND);
		gitLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					java.awt.Desktop.getDesktop().browse(URI.create("https://github.com/jeffrypig23/Mayterm"));
				} catch (IOException e) {
					return;
				}
			}

		});
		MainDisplay.root.getChildren().add(gitLink);
	}

	/**
	 * Changes the artist text.
	 * 
	 * @param athr
	 *            - The new artist text.
	 */
	public static void setArtist(String athr) {
		author.setText(athr);
		author.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, authorSize));
		author.setX(1012 - author.getLayoutBounds().getWidth());
		checkTextSpacing(author, FontWeight.EXTRA_BOLD, authorSize);
	}

	/**
	 * Changes the title text.
	 * 
	 * @param ttl
	 *            - the new title text.
	 */
	public static void setTitle(String ttl) {
		title.setText(ttl);
		title.setFont(Font.font("Arial", titleSize));
		title.setX(1012 - title.getLayoutBounds().getWidth());
		checkTextSpacing(title, null, titleSize);
	}

	/**
	 * Changes the volume of the player.
	 * 
	 * @param vol
	 *            - The new volume value (as a decimal).
	 */
	public static void handleVolume(double vol) {
		volume = Double.parseDouble(String.format("%.2f", volume + vol));
		if (volume > 1) {
			volume = 1;
		}
		if (volume < 0) {
			volume = 0;
		}
		AudioPlayer.player.setVolume(volume);
		volumeHUD.setText(String.format("Volume: %d%%", (int) (volume * 100)));
		FadeTransition volumeFade = new FadeTransition(Duration.millis(3000), volumeHUD);
		volumeFade.setFromValue(1);
		volumeFade.setToValue(0);
		volumeFade.play();
	}

	/**
	 * Handles the pause HUD fade.
	 */
	public static void handleInfo() {
		FadeTransition pauseFade = new FadeTransition(Duration.millis(10000), pauseInfo);
		pauseFade.setFromValue(1);
		pauseFade.setToValue(0);
		pauseFade.play();
	}

	/**
	 * Sets the artist and title text based on the provided source.
	 * 
	 * @param source
	 *            - the URL friendly string of the source.
	 */
	public static void setTitleAndArtist(String source) {
		try {
			File file = new File(AudioFile.toFilePath(source));
			if (source.contains(".mp3")) {
				String[] stuff = getMetadata.getMp3(file);
				setArtist(stuff[0]);
				setTitle(stuff[1]);
			} else if (source.contains(".mp4") || source.contains(".m4a") || source.contains(".m4v")) {
				String[] stuff = getMetadata.getMp4(file);
				setArtist(stuff[0]);
				setTitle(stuff[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Adjusts the text size based on how long it is.
	 * 
	 * @param text
	 *            - The text to be changed.
	 * @param weight
	 *            - The font weight of the text.
	 * @param fontSize
	 *            - The original font size.
	 */
	public static void checkTextSpacing(Text text, FontWeight weight, double fontSize) {
		double overSize = (text.getLayoutBounds().getWidth() + 20) - 1012;
		if (overSize > 0) {
			text.setFont(Font.font("Arial", weight, fontSize - 1));
			text.setX(1012 - text.getLayoutBounds().getWidth());
			checkTextSpacing(text, weight, fontSize - 1);
		}

	}

}