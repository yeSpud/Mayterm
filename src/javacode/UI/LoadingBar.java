package javacode.UI;


import javacode.Debugger;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class LoadingBar extends Rectangle {

	public LoadingBar() {

	}

	private LoadingBar(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	public LoadingBar createLoadingBar() {
		LoadingBar bar = new LoadingBar(115, 356, 1046, 2);
		bar.setStrokeType(StrokeType.CENTERED);
		bar.setStrokeWidth(2);
		bar.fillProperty();
		return bar;
	}

	/**
	 * TODO Documentation
	 */
	public void playAnimation() {

		// Creating Translate Transition
		TranslateTransition stage1 = this.stage1Animation();

		ScaleTransition stage2 = this.stage2Animation();
		stage2.stop();

		stage2.setOnFinished(e -> {
			Debugger.d(this.getClass(), "Stage 2 done");
		});

		stage1.setOnFinished(e -> {
			Debugger.d(this.getClass(), "Stage 1 done");
			stage2.play();
		});

		//Playing the animation
		stage1.playFromStart();

	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	private TranslateTransition stage1Animation() {

		// https://www.tutorialspoint.com/javafx/javafx_animations.htm

		// Creating Translate Transition
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), this);

		// Setting the value of the transition along the x axis.
		translateTransition.setFromX(-this.getWidth() - this.getX());
		translateTransition.setToX(0);

		// Setting auto reverse value to false
		translateTransition.setAutoReverse(false);

		return translateTransition;
	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	private ScaleTransition stage2Animation() {
		ScaleTransition scaleTransition = new ScaleTransition();

		scaleTransition.setDuration(Duration.millis(1000));

		scaleTransition.setNode(this);

		

		scaleTransition.setAutoReverse(false);

		return scaleTransition;
	}
}
