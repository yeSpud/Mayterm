package javacode.Tests;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Filltransition extends Application {

	private static BorderPane root = new BorderPane();
	private static Scene scene = new Scene(root, 1280, 720, Color.BLACK);

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setScene(scene);
		primaryStage.show();

		Rectangle rect = new Rectangle(100, 40, 100, 100);
		rect.setArcHeight(50);
		rect.setArcWidth(50);
		rect.setFill(Color.VIOLET);

		root.getChildren().add(rect);

		RotateTransition rt = new RotateTransition(Duration.millis(3000), rect);
		rt.setByAngle(180);
		rt.setAxis(new Point3D(0,1,0));
		//rt.setAxis(rect.getRotationAxis());
		rt.setCycleCount(4);
		rt.setAutoReverse(true);

		rt.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
