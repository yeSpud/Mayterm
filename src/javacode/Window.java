package javacode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Starting application");

		Scene scene = new Scene(new BorderPane(), 1280, 720);
		primaryStage.setScene(scene);

		// Setup the stage, so it can be accessed by other classes statically
		Window.stage = primaryStage;

		primaryStage.show();
	}

	public static void setStageBackground(Color color) {
		Debugger.d(Window.class, "Changing color to " + color.toString());

		Scene scene = Window.stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
