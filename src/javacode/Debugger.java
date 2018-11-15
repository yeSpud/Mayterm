package javacode;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Debugger {

	Main main = new Main();
	
	
	public void d(Class<?> c, String message) {
		if (main.debug) {
			System.out.println(String.format("%s: %s", c.getName(), message));
		} else {
			return;
		}
	}
	
	public void e(Class<?> c, String error, String trace, StackPane stack) {
		String errorHeader = String.format("%s: %s",c.getName(), error);
		
		System.out.println(errorHeader);
		
		Text errorHUD = new Text(String.format("%s\n%s", errorHeader, trace));
		
		errorHUD.setFont(new Font("Arial", 15));
		errorHUD.setWrappingWidth(500);
		errorHUD.setFill(Color.RED);
		errorHUD.setTranslateX(0);
		
		
		stack.getChildren().add(0, errorHUD);
		
	}
	
}
