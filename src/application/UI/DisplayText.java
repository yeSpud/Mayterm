package application.UI;

import javafx.scene.text.Text;

public class DisplayText {

	Text author = new Text("No file currently selected"/* "TRISTAM & BRAKEN" */),
			title = new Text("Press \"O\" to select a file"/* "FRAME OF MIND" */),
			volumeHUD = new Text(240, 700, "Volume: 75%");
	// TODO Add "Press p to pause, and github link"
	final double authorSize = 68.75, titleSize = 35, volumeSize = 20;
	
	public static void setupText() {
		
	}
	
	public static void setAuthor(String author) {
		
	}
	
	public static void setTitle(String title) {
		
	}

}
