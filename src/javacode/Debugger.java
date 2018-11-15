package javacode;

public class Debugger {

	Main main = new Main();
	
	
	public void d(Class<?> c, String message) {
		if (main.debug) {
			System.out.println(String.format("%s: %s", c.getName(), message));
		} else {
			return;
		}
	}
	
}
