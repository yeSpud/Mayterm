package javacode;

public class Main {

	public static boolean debug = false;


	public static void main(String[] args) {

		// Check for debugging mode
		for (String argument : args) {
			if (argument.contains("debug=")) {
				String[] debugArg = argument.split("=");
				Main.debug = Boolean.parseBoolean(debugArg[1]);
				Debugger.d(Main.class, "Debugging enabled");
				break;
			}
		}

		javafx.application.Application.launch(Main.debug ? DebugWindow.class : Window.class, args);

	}

}
