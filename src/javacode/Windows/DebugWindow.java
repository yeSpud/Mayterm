package javacode.Windows;

import javacode.Debugger;
import javacode.GenreColors;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DebugWindow extends javafx.application.Application {

	private Window window = new Window();

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

		// Add a wau to change the main window properties
		this.setupMainWindow(parent);

		// Add a way to change the loading bar properties
		this.setupLoadingBar(parent);

		// Add a way to change the album art properties
		this.setupAlbumArt(parent);

		// Add a way to change the track info
		this.setupTrackInfo(parent);

		// Show the debug window
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setWidth(300);
		primaryStage.show();

		// Launch the main window as well
		window.start(new Stage());
	}

	private void setupMainWindow(VBox parent) {

		// Create a slider for modifying the opacity
		Slider opacitySlider = new Slider();
		opacitySlider.setMax(1);
		opacitySlider.setMin(0);
		opacitySlider.setValue(1);
		opacitySlider.valueProperty().addListener((observable, oldValue, newValue) -> this.window.updateOpacity(newValue.doubleValue()));
		opacitySlider.setMaxWidth(150);

		// Create a CheckBox in order to hide the GitHub link
		CheckBox hideGitHubLink = new CheckBox("Show GitHub link");
		hideGitHubLink.setSelected(true);
		hideGitHubLink.selectedProperty().addListener((observable, oldValue, newValue) -> this.window.hideElement(this.window.gitHubText, oldValue));

		this.addToWindow(parent, this.createTitle("Main window"), opacitySlider, hideGitHubLink);

	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupLoadingBar(VBox parent) {

		// Create a button to play the loading animation
		Button animate = new Button("Play animation");
		animate.setFont(new Font(15));
		animate.setOnAction((event -> this.window.loadingBar.playAnimation()));


		// Create a combo box to change the loading bar color
		ComboBox<GenreColors> color_picker = this.createComboBox(GenreColors.ELECTRONIC, GenreColors.values());
		// Change the loading bar colors
		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> this.window.loadingBar.setColor(newValue.getColor()));


		// Create a checkbox to enable/disable the bar
		CheckBox showBar = new CheckBox("Show loading bar");
		showBar.setSelected(true);
		showBar.selectedProperty().addListener((observable, oldValue, newValue) -> {
			this.window.hideElement(this.window.loadingBar, oldValue);
			animate.setDisable(oldValue);
			color_picker.setDisable(oldValue);

		});


		// Add all the nodes to the window
		this.addToWindow(parent, this.createTitle("Loading bar"), animate, color_picker, showBar);
	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupAlbumArt(VBox parent) {

		// Create a combo box to change the album art color
		ComboBox<GenreColors> color_picker = this.createComboBox(GenreColors.ELECTRONIC, GenreColors.values());
		color_picker.valueProperty().addListener((observable, oldValue, newValue) -> this.window.albumArt.setColor(newValue));


		// Create a checkbox that will enable/disable the art
		CheckBox box = new CheckBox("Show album art");
		box.setSelected(true);
		box.selectedProperty().addListener((observable, oldValue, newValue) -> {
			this.window.hideElement(this.window.albumArt, oldValue);
			color_picker.setDisable(oldValue);
		});


		// Create a checkbox for inverting the cat color
		CheckBox invertCat = new CheckBox("Invert cat");
		invertCat.setSelected(false);
		invertCat.selectedProperty().addListener((observable, oldValue, newValue) -> this.window.invertCat(newValue));


		// Create a checkbox to show / hide the cat
		CheckBox hideCat = new CheckBox("Show cat");
		hideCat.setSelected(true);
		hideCat.selectedProperty().addListener((observable, oldValue, newValue) -> {
			this.window.hideElement(this.window.cat, oldValue);
			invertCat.setDisable(oldValue);
		});

		// TODO Add button to play show Album art animation
		// TODO Add Button to play show stock art animation


		// Add all the nodes to the window
		this.addToWindow(parent, this.createTitle("Album art"), color_picker, box, invertCat, hideCat);
	}

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 */
	private void setupTrackInfo(VBox parent) {

		// Create a TextField to edit the title
		TextField titleText = new TextField();
		titleText.setMaxWidth(150);
		titleText.textProperty().addListener((observable, oldValue, newValue) -> this.window.title.setTitle(newValue, this.window.stage));
		titleText.setPromptText("Title text");

		// Create a CheckBox to hide the title
		CheckBox hideTitle = new CheckBox();
		hideTitle.setText("Show title");
		hideTitle.setSelected(true);
		hideTitle.selectedProperty().addListener((observable, oldValue, newValue) -> {
			this.window.hideElement(this.window.title, oldValue);
			titleText.setDisable(oldValue);
		});

		// Create a TextField to edit the title
		TextField artistText = new TextField();
		artistText.setMaxWidth(150);
		artistText.textProperty().addListener((observable, oldValue, newValue) -> this.window.artist.setArtist(newValue, this.window.stage));
		artistText.setPromptText("Artist text");

		// Create a CheckBox to hide the title
		CheckBox hideArtist = new CheckBox();
		hideArtist.setText("Show artist");
		hideArtist.setSelected(true);
		hideArtist.selectedProperty().addListener((observable, oldValue, newValue) -> {
			this.window.hideElement(this.window.artist, oldValue);
			artistText.setDisable(oldValue);
		});

		// TODO Art from track

		// Add all the nodes to the window
		this.addToWindow(parent, this.createTitle("Track info"), titleText, hideTitle, artistText, hideArtist);
	}

	/**
	 * TODO Documentation
	 *
	 * @param initial
	 * @param genreColors
	 * @return
	 */
	private ComboBox<GenreColors> createComboBox(GenreColors initial, GenreColors[] genreColors) {
		ComboBox<GenreColors> comboBox = new ComboBox<>(FXCollections.observableArrayList(genreColors));
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

	/**
	 * TODO Documentation
	 *
	 * @param parent
	 * @param nodes
	 */
	private void addToWindow(VBox parent, Node... nodes) {
		// Create a container to help space out and center all the nodes
		VBox container = new VBox();
		container.setSpacing(5);
		container.setAlignment(Pos.CENTER);

		// Add all the nodes to the container
		container.getChildren().addAll(nodes);

		// Add the container to the parent
		parent.getChildren().add(container);
	}

}
