package application.UI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

// TODO: Finish this
public class Updater {
	// public static BorderPane root = new BorderPane();

	// public static Scene scene = new Scene(root, 400, 300, Color.WHITE);

	static String url = "https://raw.githubusercontent.com/jeffrypig23/Mayterm/master/src/version.txt";

	/*
	 * public static Button Update = new Button("Update"); public static Button
	 * DontUpdate = new Button("Not right now"); public static Button Cancel = new
	 * Button("Cancel");
	 */

	public static boolean updateAvalible() {
		URL update = null;
		try {
			update = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner s = null;
		try {
			s = new Scanner(update.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double version = (Double.parseDouble(s.nextLine()));
		s.close();
		double currentVersion = 0;
		try {
			currentVersion = Double.parseDouble(String.valueOf(Resources.toString(
					Updater.class.getClassLoader().getResource("version.txt").toURI().toURL(), Charsets.UTF_8)));
		} catch (NumberFormatException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("GitHub version: " + version);
		System.out.println("Current version: " + currentVersion);

		return (version > currentVersion);

	}

	/*
	 * public static void createUpdaterPrompt(Stage primaryStage) { try {
	 * 
	 * Main.mainStage = primaryStage;
	 * 
	 * Main.mainStage.setScene(scene);
	 * 
	 * Main.mainStage.setResizable(false); Main.mainStage.show();
	 * 
	 * root.getStyleClass().add("stage");
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}
