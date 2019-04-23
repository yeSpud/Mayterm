package javacode;

public class Debugger {

	public static void d(Class<?> c, String message) {
		if (Main.debug) {
			System.out.println(String.format("%s: %s", c.getName(), message));
		}
	}
}
