package application.UI;

import java.io.File;
import java.io.IOException;

import application.Main;
import application.Audio.AudioFile;
import application.Audio.getMetadata;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CoverArt {

	public static Rectangle art = new Rectangle(1034, 198, 126, 126);

	public static ImageView coverArt = new ImageView(), cat = new ImageView();

	public static Image catLogo, catLogoBlack;

	public static void createCoverArt() {
		MainDisplay.root.getChildren().add(art);

		cat.setX(1034);
		cat.setY(198);
		cat.setFitHeight(126);
		cat.setFitWidth(126);
		cat.setRotate(180);

		Main a = new Main();
		catLogo = null;
		catLogoBlack = null;
		try {
			catLogo = new Image(a.getClass().getResource("Resources/mcatTransparent.png").openStream());
			catLogoBlack = new Image(a.getClass().getResource("Resources/mcatTransparentBlack.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		cat.setImage(catLogo);
		MainDisplay.root.getChildren().add(cat);
	}

	public static void setArt(Image image) {
		coverArt.setX(1034);
		coverArt.setY(198);
		coverArt.setImage(image);
		coverArt.setFitHeight(126);
		coverArt.setFitWidth(126);
		coverArt.setRotate(180);
		if (!MainDisplay.root.getChildren().contains(coverArt)) {
			MainDisplay.root.getChildren().add(coverArt);
		}

	}

	public static void autoSetArt(String source) {
		File file = new File(AudioFile.toFilePath(source));
		if (source.contains(".mp3")) {
			getMetadata.getMp3(file);
		} else if (source.contains(".mp4") || source.contains(".m4a") || source.contains(".m4v")) {
			getMetadata.getMp4(file);
		}

	}

	public static void blackCat(boolean blackCat) {
		if (blackCat) {
			cat.setImage(catLogoBlack);
		} else {
			cat.setImage(catLogo);
		}
	}

}
