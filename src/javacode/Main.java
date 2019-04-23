package javacode;

public class Main {

	public static boolean debug = false;


	public static void main(String[] args) {

		// Variable for showing the debug panel
		boolean debugPanel = false;

		// Check for debugging arguments
		for (String argument : args) {
			if (argument.contains("debug=")) {
				String[] debugArg = argument.split("=");
				Main.debug = Boolean.parseBoolean(debugArg[1]);
				Debugger.d(Main.class, "Debugging enabled");
			} else if (argument.contains("showDebugPanel=")) {
				String[] debugArg = argument.split("=");
				debugPanel = Boolean.parseBoolean(debugArg[1]);
				Debugger.d(Main.class, "Showing debug panel: " + debugPanel);
			}
		}

		javafx.application.Application.launch(debugPanel ? javacode.Windows.DebugWindow.class : javacode.Windows.Window.class, args);

	}

}
