package application.Errors;

/**
 * Error for when the current operating system is neither Windows, macOS, or Linux.
 * 
 * @author Spud
 *
 */
public class UnrecognizableOperatingSystem extends Exception {
	private static final long serialVersionUID = 1L;
	public UnrecognizableOperatingSystem() {
		super("The current operating system is neither Windows, macOS, or Linux.");
	}
}
