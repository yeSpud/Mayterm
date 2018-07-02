package application.Core.UI;

import java.io.File;

import application.Core.Audio.getMetadata;
import application.Core.Errors.UnrecognizableFileType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Title {

	private static Text title = new Text("Press \"O\" to select a file"/* "FRAME OF MIND" */);
	private static final double titleSize = 35;
	
	public static void setup() {
		title.setFont(Font.font("Arial", titleSize));
		title.setX(1012 - title.getLayoutBounds().getWidth());
		title.setY(244);
		title.setRotate(180);
		title.setFill(Color.WHITE);
	}
	
	public static void setTitle(String text) {
		title.setText(text.toUpperCase());
		title.setFont(Font.font("Arial", titleSize));
		title.setX(1012 - title.getLayoutBounds().getWidth());
		checkTextSpacing(title, null, titleSize);
	}
	
	public static void setTitle(File sourceFile) throws UnrecognizableFileType {
		if (sourceFile.getAbsolutePath().endsWith(".mp3")) {
			setTitle(getMetadata.getMp3(sourceFile)[1]);
		} else if (sourceFile.getAbsolutePath().endsWith(".mp4") || sourceFile.getAbsolutePath().endsWith(".m4a") || sourceFile.getAbsolutePath().endsWith(".m4v")) {
			setTitle(getMetadata.getMp4(sourceFile)[1]);
		} else {
			throw new UnrecognizableFileType();
		}
	}
	
	private static void checkTextSpacing(Text text, FontWeight weight, double fontSize) {
		double overSize = (text.getLayoutBounds().getWidth() + 20) - 1012;
		if (overSize > 0) {
			text.setFont(Font.font("Arial", weight, fontSize - 1));
			text.setX(1012 - text.getLayoutBounds().getWidth());
			checkTextSpacing(text, weight, fontSize - 1);
		}

	}
	
}