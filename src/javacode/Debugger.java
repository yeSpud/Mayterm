package javacode;

import javacode.Windows.Window;

import java.io.StringWriter;

public class Debugger {

	/**
	 * TODO Documentation
	 *
	 * @param c
	 * @param message
	 */
	public static void d(Class<?> c, String message) {
		if (Main.debug) {
			System.out.println(String.format("%s: %s", c.getName(), message));
		}
	}

	/**
	 * TODO Documentation
	 *
	 * @param e
	 */
	public static void e(Throwable e) {
		// TODO Display the error on the main window
		if (Window.errorPrompt != null) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new java.io.PrintWriter(sw));
			Window.errorPrompt.logError(sw.toString());
		}
	}
}
