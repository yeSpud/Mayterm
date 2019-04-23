package javacode.Windows;

import javacode.Debugger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DebugWindow extends javafx.application.Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Creating debug window");

		// Set the debug panel parent to a VBox
		VBox parent = new VBox();

		Scene scene = new Scene(parent);

		// Set the title
		primaryStage.setTitle("Debug window");

		// Add a way to change the background
		this.changeBackgroundColor(parent);

		// Show the debug window
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();

		// Launch the main window as well
		new Window().start(new Stage());
	}

	/**
	 * TODO Documentation
	 */
	private void changeBackgroundColor(VBox parent) {
		// Create a title for the element
		Text title = new Text("Set window background");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(15));
		parent.getChildren().add(title);

		// Create a combo box to change the background color
		ObservableList<Color> colors = FXCollections.observableArrayList(Color.WHITE, Color.BLACK);
		ComboBox<Color> color_picker = new ComboBox<>(colors);

		// Set the default value
		color_picker.setValue(Color.WHITE);

		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
			Debugger.d(this.getClass(), "Changing color from " + oldValue.toString() + " to " + newValue.toString());

			// Change the background
			Window.setStageBackground(newValue);
		});

		parent.getChildren().add(color_picker);
	}
}
