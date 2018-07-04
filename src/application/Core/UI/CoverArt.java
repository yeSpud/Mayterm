package application.Core.UI;

import java.io.IOException;

import application.Core.Debugger;
import application.Core.Main;
import application.Core.Audio.getMetadata;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Class responsible for the cover art of the visualizer.
 * 
 * @author Spud
 *
 */
public class CoverArt {

	public static Rectangle art = new Rectangle(1034, 198, 126, 126);

	public static ImageView coverArt = new ImageView(), cat = new ImageView();

	public static Image catLogo, catLogoBlack;

	/**
	 * Creates the cover arts section for the display, and adds the respcive cat.
	 * <br>
	 * :3
	 */
	public static void setup() {
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
			catLogo = new Image(a.getClass().getResource("mcatTransparent.png").openStream());
			catLogoBlack = new Image(a.getClass().getResource("mcatTransparentBlack.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		cat.setImage(catLogo);
		MainDisplay.root.getChildren().add(cat);
	}

	/**
	 * Sets the art image.
	 * 
	 * @param image - the Image to be set.
	 */
	public static void setArt(String filePath) {
		coverArt.setX(1034);
		coverArt.setY(198);
		try {
			coverArt.setImage(getMetadata.getImage(filePath));
		} catch (Exception e) {
			coverArt.setImage(null);
		}
		coverArt.setFitHeight(126);
		coverArt.setFitWidth(126);
		coverArt.setRotate(180);
		if (!MainDisplay.root.getChildren().contains(coverArt)) {
			MainDisplay.root.getChildren().add(coverArt);
		}

	}

	/**
	 * Changes the cat in the cover art to a black cat, preferably only of the
	 * background is white though.
	 * 
	 * @param blackCat - whether or not the cover art cat needs to be a black cat.
	 */
	public static void blackCat(boolean blackCat) {
		Debugger.d(CoverArt.class, "Change to black cat icon? " + blackCat);
		if (blackCat) {
			cat.setImage(catLogoBlack);
		} else {
			cat.setImage(catLogo);
		}
	}

}
