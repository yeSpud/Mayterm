package javacode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	public boolean debug = false;
	
	Pane stack;

	@Override
	public void start(Stage stage) {

		// Check for updates
		System.out.println("Checking for update");
		boolean newUpdate = Updater.updateAvalible();
		System.out.println("New update avalible: " + newUpdate);
		if (newUpdate) {
			Updater.showUpdatePrompt();
		}

		// Get the debug background image
		Image debugbackgroundimage = new Image(
				this.getClass().getClassLoader().getResourceAsStream("resources/debugbackground.jpg"));

		// Create a stack pane to add all the objects into
		stack = new Pane();

		// Setup the background
		stack.setBackground(new Background(new BackgroundImage(debugbackgroundimage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(
						debugbackgroundimage.getWidth(), debugbackgroundimage.getHeight(), false, false, true, true))));

		// Set the preferred and minimum sizes for the stack
		stack.setPrefSize(debugbackgroundimage.getWidth(), debugbackgroundimage.getHeight());
		stack.setMinSize(16, 9);

		// We need an anchor pane go help automatically constrain the maximum and
		// minimum sizes of things
		AnchorPane anchor = new AnchorPane();
		AnchorPane.setTopAnchor(stack, 0.0);
		AnchorPane.setBottomAnchor(stack, 0.0);
		AnchorPane.setLeftAnchor(stack, 0.0);
		AnchorPane.setRightAnchor(stack, 0.0);


		// Add the stack to the anchor pane
		anchor.getChildren().addAll(stack);

		// Set the anchor background to a light gray, that way we can check for overlap
		anchor.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		// Set the scene for the visualizer, use the anchor pane defined above
		Scene mCatScene = new Scene(anchor);

		// Set the contents of the window to that of the scene
		stage.setScene(mCatScene);

		// Show the window (stage)
		stage.show();
		
		// Add a test node to check if its size and position updates
		Text test = new Text(String.format("Test\nFoo bar"));
		
		test.setFont(new Font("Arial", 15));
		test.setWrappingWidth(500);
		test.setFill(Color.RED);
		test.setLayoutX(0);
		test.setLayoutY(50);
		
		Circle test2 = new Circle();
		test2.setLayoutX(50);
		test2.setLayoutY(120);
		test2.setFill(Color.GREEN);
		test2.setRadius(10);
		
		
		stack.getChildren().add(0, test);
		stack.getChildren().add(1, test2);

	}

	public static void main(String[] args) {

		Main This = new Main();

		// Check if debug mode is enabled (Basically check for console spam enabled)
		try {
			This.debug = Boolean.parseBoolean(args[0]);
		} catch (Exception e) {
			This.debug = false;
		}

		launch(args);

	}
}
