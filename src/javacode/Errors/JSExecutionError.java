package javacode.Errors;

public class JSExecutionError extends Exception {
	private static final long serialVersionUID = 1L;
	public JSExecutionError() {
		super("Cannot execute JavaScript code");
	}
}
