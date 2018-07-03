package application.Core;

public class Debugger {

	public static void d(Class<?> c, String message) {
		if (Main.debug) {
			System.out.println(String.format("\n%s: %s", c.getName(), message));
		} else {
			return;
		}
	}
	
}
