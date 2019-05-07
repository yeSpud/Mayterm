package javacode.UI.Text;

/**
 * Create the GitHub text that resides at the bottom right corner of the screen.
 * When clicked, it should open to the repository page on GitHub.
 *
 * @author Spud
 */
public class GitHubText extends javafx.scene.text.Text implements javacode.UI.RelativeNode {

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
	
	@Override
	public void updatePosition(double width, double height) {
		double x = width - (10 + this.getLayoutBounds().getWidth()), y = height - (20 + this.getLayoutBounds().getHeight());
		javacode.Debugger.d(this.getClass(), String.format("Updating position to: %f, %f", x, y));
		this.setX(x);
		this.setY(y);
	}

}
