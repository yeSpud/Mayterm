package javacode.Windows;

import javacode.Debugger;
import javacode.GenreColors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

import java.util.ArrayList;

public class DebugWindow extends javafx.application.Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Debugger.d(this.getClass(), "Creating debug window");

		// Set the debug panel parent to a VBox
		VBox parent = new VBox();
		parent.setSpacing(10);
		parent.setAlignment(Pos.CENTER);

		Scene scene = new Scene(parent);

		// Set the title
		primaryStage.setTitle("Debug window");

		// Add a way to change the background
		this.setupBackground(parent);

		// Add a way to change the loading bar properties
		this.setupLoadingBar(parent);

		// Add a way to change the album art properties
		this.setupAlbumArt(parent);

		// Show the debug window
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setWidth(300);
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
		container.setAlignment(Pos.CENTER);

		// Create a combo box to change the background color
		ComboBox<Color> color_picker = this.createComboBox(Color.BLACK, Color.BLACK, Color.WHITE);
		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
			Debugger.d(this.getClass(), "Changing color from " + oldValue.toString() + " to " + newValue.toString());

			// Change the background
			Window.setStageBackground(newValue);
		});

		// Add a title, and the combo box
		container.getChildren().addAll(this.createTitle("Background"), color_picker);

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
		container.setAlignment(Pos.CENTER);

		// Create a button to play the loading animation
		Button animate = new Button("Play animation");
		animate.setFont(new Font(15));
		animate.setOnAction((event -> {
			Window.playLoadingAnimation();
		}));


		// Create a combo box to change the background color
		ComboBox<GenreColors> color_picker = this.createComboBox(GenreColors.ELECTRONIC, GenreColors.values());
		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
			Debugger.d(this.getClass(), "Changing color from " + oldValue.toString() + " to " + newValue.toString());

			// Change the background
			Window.setLoadingColor(newValue.getColor());
		});


		// Create a checkbox to enable/disable the bar
		CheckBox showBar = new CheckBox("Show loading bar");
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


		// Add the title, and then all the other elements
		container.getChildren().addAll(this.createTitle("Loading bar"), animate, color_picker, showBar);

		parent.getChildren().add(container);
	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupAlbumArt(VBox parent) {
		VBox container = new VBox();
		container.setSpacing(5);
		container.setAlignment(Pos.CENTER);

		// Create a combo box to change the album art color
		ComboBox<GenreColors> color_picker = this.createComboBox(GenreColors.ELECTRONIC, GenreColors.values());
		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> {
			Debugger.d(this.getClass(), "Changing album art to: " + newValue.toString());
			Window.setAlbumArtColor(newValue);
		});

		// TODO Add button to play show Album art animation
		// TODO Add Button to play show stock art animation
		// TODO Add cat for stock art

		// Create a checkbox that will enable/disable the art
		CheckBox box = new CheckBox("Show album art");
		box.setSelected(true);
		box.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				Window.showAlbumArt();
			} else {
				Window.hideAlbumArt();
			}

			color_picker.setDisable(oldValue);
		});

		// Add the title, and the color picker
		container.getChildren().addAll(this.createTitle("Album art"), color_picker, box);

		parent.getChildren().add(container);
	}

	/**
	 * TODO Documentation
	 *
	 * @param initial
	 * @param options
	 * @return
	 */
	private ComboBox createComboBox(Object initial, Object... options) {
		ObservableList list = FXCollections.observableArrayList(options);
		ComboBox comboBox = new ComboBox(list);
		comboBox.setValue(initial);
		return comboBox;
	}

	/**
	 * TODO Documentation
	 *
	 * @param title
	 * @return
	 */
	private Text createTitle(String title) {
		Text t = new Text(title);
		t.setTextAlignment(TextAlignment.CENTER);
		t.setFont(new Font(15));
		return t;
	}
}
