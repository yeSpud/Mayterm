package javacode.UI;

import java.io.IOException;

import javacode.Debugger;
import javacode.Audio.getMetadata;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Class responsible for the cover art of the visualizer.
 * 
 * @author Spud
 *
 */
public class CoverArt {

	private static Rectangle art = new Rectangle(1034, 198, 126, 126);

	private static ImageView coverArt = new ImageView(), cat = new ImageView();

	private static Image catLogo, catLogoBlack, artwork;
	
	private static RotateTransition backArtRotation = new RotateTransition(Duration.millis(500), art),
			catRotation = new RotateTransition(Duration.millis(500), cat), 
			coverArtRotation = new RotateTransition(Duration.millis(500), coverArt);
	
	private static Point3D pivotAxis = new Point3D(0,1,0), normalAxis = new Point3D(0,0,0);

	/**
	 * Creates the cover arts section for the display, and adds the respective cat.
	 * <br>
	 * :3
	 */
	public static void setup() {
		MainDisplay.root.getChildren().add(art);
		
		// Setup rotation animations
		backArtRotation.setByAngle(90);
		catRotation.setByAngle(90);
		coverArtRotation.setByAngle(-90);
		
		backArtRotation.setAxis(pivotAxis);
		art.setRotationAxis(pivotAxis);
		catRotation.setAxis(pivotAxis);
		coverArtRotation.setAxis(pivotAxis);
		
		backArtRotation.setCycleCount(1);
		catRotation.setCycleCount(1);
		coverArtRotation.setCycleCount(1);
		
		
		// Set the end rotation of the back art to trigger the cover art rotation
		backArtRotation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				coverArt.setImage(artwork);
				coverArtRotation.play();
			}	
		});
		
		catRotation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cat.setRotationAxis(normalAxis);
				cat.getStyleClass().remove("cat");
			}
		});
		
		coverArtRotation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				coverArt.setRotationAxis(normalAxis);
				coverArt.getStyleClass().remove("coverart");
			}
		});
		
		cat.setX(1034);
		cat.setY(198);
		cat.setFitHeight(126);
		cat.setFitWidth(126);
		cat.setRotate(180);

		catLogo = null;
		catLogoBlack = null;

		try {
			catLogo = new Image(
					CoverArt.class.getClassLoader().getResource("resources/mcatTransparent.png").openStream());
			catLogoBlack = new Image(
					CoverArt.class.getClassLoader().getResource("resources/mcatTransparentBlack.png").openStream());
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
			//coverArt.setImage(getMetadata.getImage(filePath));
			
			cat.getStyleClass().add("cat");
			coverArt.getStyleClass().add("coverart");
			
			coverArt.setRotationAxis(pivotAxis);
			cat.setRotationAxis(pivotAxis);
			coverArt.setRotate(-90);
			
			artwork = getMetadata.getImage(filePath);
			backArtRotation.play();
			catRotation.play();
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

	public static void changeColor(Color color) {
		Debugger.d(CoverArt.class, String.format("Changing genre to %s, %s, %s", color.getRed(), color.getGreen(), color.getBlue()));
		art.setFill(color);
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
