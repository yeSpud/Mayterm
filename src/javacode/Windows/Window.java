package javacode.Windows;

import javacode.Debugger;
import javacode.UI.LoadingBar;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Window extends Application {

	private static Stage stage;

	private static LoadingBar loadingBar;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Starting application");

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 1280, 720);

		// Setup the stage, so it can be accessed by other classes statically
		Window.stage = primaryStage;

		// Add the loading bar
		Window.loadingBar = new LoadingBar().createLoadingBar();
		root.getChildren().add(Window.loadingBar);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void setStageBackground(Color color) {
		Debugger.d(Window.class, "Changing color to " + color.toString());
		Scene scene = Window.stage.getScene();
		BorderPane root = (BorderPane) scene.getRoot();
		root.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public static void setLoadingColor(Color color) {
		Debugger.d(Window.class, "Changing color to " + color.toString());
		Window.loadingBar.setFill(color);
	}

	public static void playLoadingAnimation() {
		Debugger.d(Window.class, "Playing animation");
		Window.loadingBar.playAnimation();
	}

}
