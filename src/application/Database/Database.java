package application.Database;

import java.io.File;

public class Database {

	public static boolean databaseExist() {
		String FileFolder = null;
		if (Environment.getOS().equals(Environment.OS.WINDOWS)) {
			FileFolder = System.getenv("APPDATA") + "\\Spud\\visulizer.db";
		} else if (Environment.getOS().equals(Environment.OS.MACOS)) {
			FileFolder = System.getProperty("user.home") + "/Library/Application\\ Support/Spud/visulizer.db";
		} else if (Environment.getOS().equals(Environment.OS.LINUX)) {
			FileFolder = System.getProperty("user.dir") + ".Spud/visulizer.db";
		}
		return (new File(FileFolder).exists() && new File(FileFolder).canWrite());
	}

}
