package javacode.Tests;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RotateRecrtangleY extends Application {

	private static BorderPane root = new BorderPane();
	private static Scene scene = new Scene(root, 1280, 720, Color.BLACK);
	
	private static Rectangle rect0 = new Rectangle(100, 40, 100, 100), rect1 = new Rectangle(100, 40, 100, 100);
	
	private static RotateTransition rt1;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setScene(scene);
		primaryStage.show();

		
		rect0.setArcHeight(50);
		rect0.setArcWidth(50);
		rect0.setFill(Color.VIOLET);
		
		
		rect1.setArcHeight(50);
		rect1.setArcWidth(50);
		rect1.setFill(Color.ORANGE);

		root.getChildren().add(rect0);
		
		RotateTransition rt = new RotateTransition(Duration.millis(500), rect0);
		rt.setByAngle(90);
		rt.setAxis(new Point3D(0,1,0));
		//rt.setAxis(rect.getRotationAxis());
		rt.setCycleCount(1);
		rt.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(rect0);
				root.getChildren().add(rect1);
			
				rt1.play();
				
			}});
		
		rt1 = new RotateTransition(Duration.millis(500), rect1);
		rt1.setByAngle(90);
		rect1.setRotationAxis(new Point3D(0,1,0));
		rect1.setRotate(-90);
		rt1.setAxis(new Point3D(0,1,0));
		//rt.setAxis(rect.getRotationAxis());
		rt1.setCycleCount(1);

		rt.play();
		

		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
