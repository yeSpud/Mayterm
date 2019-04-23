package javacode.UI;


import javacode.Debugger;
import javacode.Windows.Window;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
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

		ParallelTransition stage2 = this.stage2Animation(), stage3 = this.stage3Animation();
		stage2.stop();
		stage3.stop();

		stage3.setOnFinished((e) -> Debugger.d(this.getClass(), "Stage 3 done"));

		stage2.setOnFinished(e -> {
			Debugger.d(this.getClass(), "Stage 2 done");
			stage3.play();
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
	private ParallelTransition stage2Animation() {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000));
		scaleTransition.setByX(-(1 - (6 / this.getWidth())));
		scaleTransition.setAutoReverse(false);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
		translateTransition.setFromX(0);
		translateTransition.setToX(this.getWidth() / 2);
		translateTransition.setAutoReverse(false);

		return new ParallelTransition(this, scaleTransition, translateTransition);
	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	private ParallelTransition stage3Animation() {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000));
		scaleTransition.setByX(1 - (6 / this.getWidth()));
		scaleTransition.setAutoReverse(false);

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
		translateTransition.setFromX(this.getWidth() / 2);
		translateTransition.setToX(this.getWidth() + (this.getX() * 2));
		translateTransition.setAutoReverse(false);

		return new ParallelTransition(this, scaleTransition, translateTransition);
	}
}
