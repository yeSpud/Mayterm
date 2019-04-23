package javacode.Windows;

import javacode.Debugger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
		parent.setSpacing(10);

		Scene scene = new Scene(parent);

		// Set the title
		primaryStage.setTitle("Debug window");

		// Add a way to change the background
		this.setupBackground(parent);

		// Add a way to change the loading bar property
		this.setupLoadingBar(parent);

		// Show the debug window
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();

		// Launch the main window as well
		new Window().start(new Stage());
	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupBackground(VBox parent) {
		VBox container = new VBox();
		container.setSpacing(5);

		// Create a title for the element
		Text title = new Text("Background");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(15));
		container.getChildren().add(title);

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

		container.getChildren().add(color_picker);

		// Add the container
		parent.getChildren().add(container);

	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupLoadingBar(VBox parent) {
		VBox container = new VBox();
		container.setSpacing(5);

		Text title = new Text("Loading bar");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(15));
		container.getChildren().add(title);

		Button animate = new Button("Play animation");
		animate.setFont(new Font(15));
		animate.setOnAction((event -> {
			Window.playLoadingAnimation();
		}));
		container.getChildren().add(animate);

		// Create a combo box to change the background color
		ObservableList<Color> colors = FXCollections.observableArrayList(Color.WHITE, Color.BLACK);
		ComboBox<Color> color_picker = new ComboBox<>(colors);

		// Set the default value
		color_picker.setValue(Color.BLACK);

		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
			Debugger.d(this.getClass(), "Changing color from " + oldValue.toString() + " to " + newValue.toString());

			// Change the background
			Window.setLoadingColor(newValue);
		});

		container.getChildren().add(color_picker);

		CheckBox showBar = new CheckBox();
		showBar.setText("Show loading bar");
		showBar.setSelected(true);
		showBar.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				Window.showLoadingBar();
			} else {
				Window.hideLoadingBar();
			}

			animate.setDisable(oldValue);
			color_picker.setDisable(oldValue);

		});

		container.getChildren().add(showBar);

		parent.getChildren().add(container);
	}
}
