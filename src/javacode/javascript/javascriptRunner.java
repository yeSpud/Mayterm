package javacode.javascript;

import javacode.Errors.JSExecutionError;

public class javascriptRunner {
	static javax.script.ScriptEngineManager factory = new javax.script.ScriptEngineManager();
	static javax.script.ScriptEngine engine = factory.getEngineByName("JavaScript");
	public static void vaidateJs() throws JSExecutionError {
		try {
			engine.eval("print('JavaScript works!')");
		} catch (javax.script.ScriptException script) {
			throw new JSExecutionError();
		}
	}
	
	public static void eval(String script) throws JSExecutionError {
		try {
			engine.eval(script);
		} catch (javax.script.ScriptException e) {
			throw new JSExecutionError();
		}
	}
}
