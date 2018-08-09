package javacode.UI;

import java.io.IOException;
import java.net.URI;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GitHubLink {
	private static Text github = new Text("GitHub");
	private static final URI link = URI.create("https://github.com/jeffrypig23/Mayterm");
	public static void setup() {
		github.setFont(Font.font(15));
		github.setUnderline(true);
		github.setX(20);
		github.setY(10 + github.getLayoutBounds().getHeight());
		github.setRotate(180);
		github.setFill(Color.WHITE);
		github.setCursor(Cursor.HAND);
		github.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					java.awt.Desktop.getDesktop().browse(link);
				} catch (IOException e) {
					return;
				}
			}

		});
		MainDisplay.root.getChildren().add(github);
	}
}
