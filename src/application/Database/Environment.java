package application.Database;

import java.io.File;
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
	
	public static File getFile() {
		String FileFolder = null;
		File file = null;
		if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
			FileFolder = System.getenv("APPDATA") + "\\Spud\\visulizer.json";
		} else if (Environment.getOS().equals(Environment.OS.MACOS)) {
			FileFolder = System.getProperty("user.home") + "/Library/Application Support/Spud/visulizer.json";
		} else if (Environment.getOS().equals(Environment.OS.LINUX)) {
			FileFolder = System.getProperty("user.dir") + ".Spud/visulizer.json";
		}
		file = new File(FileFolder);
		return file;
	}

}
