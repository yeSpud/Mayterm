package application.UI;

import java.io.IOException;

import application.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CoverArt {

	public static Rectangle art = new Rectangle(1034, 198, 126, 126);

	public static ImageView coverArt = new ImageView(), cat = new ImageView();

	public static void createCoverArt() {
		Display.root.getChildren().add(art);

		cat.setX(1034);
		cat.setY(198);
		cat.setFitHeight(126);
		cat.setFitWidth(126);
		cat.setRotate(180);

		Main a = new Main();
		Image catLogo = null;
		try {
			catLogo = new Image(a.getClass().getResource("Resources/mcatTransparent.png").openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cat.setImage(catLogo);
		Display.root.getChildren().add(cat);
	}
	
	public static void setArt(Image image) {
		coverArt.setX(1034);
		coverArt.setY(198);
		coverArt.setImage(image);
		coverArt.setFitHeight(126);
		coverArt.setFitWidth(126);
		coverArt.setRotate(180);
		if (!Display.root.getChildren().contains(coverArt)) {
			Display.root.getChildren().add(coverArt);
		}
		
	}

}
