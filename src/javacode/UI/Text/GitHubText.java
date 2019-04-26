package javacode.UI.Text;

public class GitHubText extends javafx.scene.text.Text {

	public GitHubText() {
		this.setText("Source code");
		this.setFont(javafx.scene.text.Font.font(15));
		this.setUnderline(true);
		this.setFill(javafx.scene.paint.Color.WHITE);
		this.setCursor(javafx.scene.Cursor.HAND);
		this.setOnMouseClicked(event -> {
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/jeffrypig23/Mayterm"));
			} catch (java.io.IOException | java.net.URISyntaxException e) {
				e.printStackTrace();
			}
		});
	}

	public void setPosition(javacode.Windows.Window window) {
		this.setX(window.stage.getWidth() - (10 + this.getLayoutBounds().getWidth()));
		this.setY(window.stage.getHeight() - (20 + this.getLayoutBounds().getHeight()));
	}

}
