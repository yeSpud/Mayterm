package javacode.UI;

import javacode.Debugger;

import javacode.Database.Environment;
import javacode.Errors.UnrecognizableOperatingSystem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;

public class Menu {

	private static MenuBar menuBar = new MenuBar();

	private static RadioMenuItem defaultAlgorithm = new RadioMenuItem("Default"),
			fftAlgorithm = new RadioMenuItem("FFT");

	public static void setup() {

		javafx.scene.control.Menu menu = new javafx.scene.control.Menu("Spectrum Algorithms");

		menu.getItems().addAll(defaultAlgorithm, fftAlgorithm);

		menuBar.getMenus().add(menu);
		try {
			if (Environment.getOS().equals(Environment.OS.MACOS)) {
				Debugger.d(Menu.class, "menuBar set to the system menu bar");
				menuBar.setUseSystemMenuBar(true);
			}
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
		}

		MainDisplay.root.getChildren().add(menuBar);

	}

	public static void setDefaultEnabled(int value) {
		if (value == 1) {
			Debugger.d(Menu.class, "Setting fftAltorithm to true");
			fftAlgorithm.setSelected(true);
			defaultAlgorithm.setSelected(false);
		} else {
			Debugger.d(Menu.class, "Setting defaultAlgorithm to true");
			fftAlgorithm.setSelected(false);
			defaultAlgorithm.setSelected(true);
		}
	}

}
