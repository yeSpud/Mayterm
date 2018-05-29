package application.Errors;

/**
 * Database error. Either cannot write, or doesnt exist.
 * 
 * @author Spud
 *
 */
public class DatabaseError extends Exception {
	private static final long serialVersionUID = 1L;
	public DatabaseError(String message) {
		super(message);
	}
}
