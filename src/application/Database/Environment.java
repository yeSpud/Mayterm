package application.Database;

import java.io.File;
import application.Errors.UnrecognizableOperatingSystem;

/**
 * Class responsible for getting the current operating system, and where to
 * store files based on the operating system.
 * 
 * @author Spud
 *
 */
public class Environment {
	/**
	 * The file name for the database.
	 */
	static final String databaseFile = "vis.json";
	/**
	 * The file name for the wav file.
	 */
	static final String wavFile = "wav.wav";

	/**
	 * The three main types of operating systems: Windows, macOS, and Linux.
	 *
	 */
	public enum OS {
		WINDOWS, MACOS, LINUX;
	}

	/**
	 * Gets the current operating system (Either macOS, Windows, or Linux).
	 * 
	 * @return The current operating system.
	 * @throws UnrecognizableOperatingSystem
	 *             If the current operating system is not detected, or is not
	 *             Windows, macOS, or Linux, a UnrecognizableOperatingSystem error
	 *             will be thrown.
	 */
	public static Enum<OS> getOS() throws UnrecognizableOperatingSystem {
		String os = System.getProperty("os.name").toUpperCase();
		Enum<OS> getOS = null;
		if (os.contains("WIN")) {
			getOS = OS.WINDOWS;
		} else if (os.contains("MAC")) {
			getOS = OS.MACOS;
		} else if (os.contains("NUX")) {
			getOS = OS.LINUX;
		} else {
			throw new UnrecognizableOperatingSystem();
		}
		return getOS;
	}

	/**
	 * Gets the database file location.
	 * 
	 * @return File - The database file.
	 * @throws UnrecognizableOperatingSystem
	 *             If the current operating system is not detected, or is not
	 *             Windows, macOS, or Linux, a UnrecognizableOperatingSystem error
	 *             will be thrown.
	 */
	public static File getDatabaseFile() throws UnrecognizableOperatingSystem {
		String FileFolder = null;
		if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
			FileFolder = System.getenv("APPDATA") + "\\Spud\\";
		} else if (Environment.getOS().equals(Environment.OS.MACOS)) {
			FileFolder = System.getProperty("user.home") + "/Library/Application Support/Spud/";
		} else if (Environment.getOS().equals(Environment.OS.LINUX)) {
			FileFolder = System.getProperty("user.dir") + ".Spud/";
		} else {
			throw new UnrecognizableOperatingSystem();
		}
		return new File(FileFolder + databaseFile);
	}

	/**
	 * Gets the wav file location.
	 * 
	 * @return File - The database file.
	 * @throws UnrecognizableOperatingSystem
	 *             If the current operating system is not detected, or is not
	 *             Windows, macOS, or Linux, a UnrecognizableOperatingSystem error
	 *             will be thrown.
	 */
	public static File getWavFile() throws UnrecognizableOperatingSystem {
		String FileFolder = null;
		if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
			FileFolder = System.getenv("APPDATA") + "\\Spud\\";
		} else if (Environment.getOS().equals(Environment.OS.MACOS)) {
			FileFolder = System.getProperty("user.home") + "/Library/Application Support/Spud/";
		} else if (Environment.getOS().equals(Environment.OS.LINUX)) {
			FileFolder = System.getProperty("user.dir") + ".Spud/";
		} else {
			throw new UnrecognizableOperatingSystem();
		}
		return new File(FileFolder + wavFile);
	}

}
