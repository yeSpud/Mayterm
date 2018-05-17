package application.Database;

import application.Errors.UnrecognizableOperatingSystem;

public class Environment {

	public enum OS {
		WINDOWS,MACOS,LINUX;
	}
	
	public static Enum<?> getOS() {
		String os = System.getProperty("os.name").toUpperCase();
		Enum<?> getOS = null;
		if (os.contains("WIN")) {
			// FileFolder = System.getenv("APPDATA") + "\\" + "Launcher";
			getOS = OS.WINDOWS;
		} else if (os.contains("MAC")) {
			getOS = OS.MACOS;
		} else if (os.contains("NUX")) {
			getOS = OS.LINUX;
		} else {
			try {
				throw new UnrecognizableOperatingSystem();
			} catch (UnrecognizableOperatingSystem e) {
				return null;
			}
		}

		return getOS;
	}

}
