package application.Database;

import java.io.File;
import application.Errors.UnrecognizableOperatingSystem;

public class Environment {

	/**
	 * The three main types of operating systems: Windows, MacOS (Or Mac OS X), and
	 * Linux.
	 *
	 */
	public enum OS {
		WINDOWS, MACOS, LINUX;
	}

	/**
	 * 
	 * @return The current operating system.
	 * @throws UnrecognizableOperatingSystem
	 *             If the current operating system is not detected, or is not
	 *             Windows, MacOS, or Linux, a UnrecognizableOperatingSystem error
	 *             will be thrown.
	 */
	public static Enum<?> getOS() {
		String os = System.getProperty("os.name").toUpperCase();
		Enum<?> getOS = null;
		if (os.contains("WIN")) {
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

	/**
	 * 
	 * @return The file location for the database depending on the operating system.
	 */
	public static File getFile() {
		String FileFolder = null;
		File file = null;
		if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
			FileFolder = System.getenv("APPDATA") + "\\Spud\\vis.json";
		} else if (Environment.getOS().equals(Environment.OS.MACOS)) {
			FileFolder = System.getProperty("user.home") + "/Library/Application Support/Spud/vis.json";
		} else if (Environment.getOS().equals(Environment.OS.LINUX)) {
			FileFolder = System.getProperty("user.dir") + ".Spud/vis.json";
		}
		file = new File(FileFolder);
		return file;
	}

}
