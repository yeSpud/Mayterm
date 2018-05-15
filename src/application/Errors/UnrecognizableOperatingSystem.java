package application.Errors;

@SuppressWarnings("serial")
public class UnrecognizableOperatingSystem extends Exception {
	
	public UnrecognizableOperatingSystem() {
		super("The current operating system is neither Windows, macOS, or linux.");
	}

}
