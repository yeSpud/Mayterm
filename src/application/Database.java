package application;

public class Database {
	
	public static String getOS() {
		String os = System.getProperty("os.name").toUpperCase();
		String getOS = null;
		if (os.contains("WIN")) {
		    //FileFolder = System.getenv("APPDATA") + "\\" + "Launcher";
		    getOS = "Windows";
		} else if (os.contains("MAC")) {
		    //FileFolder = System.getProperty("user.home") + "/Library/Application " + "Support"
		            //+ "Launcher";
		    getOS = "macOS";
		} else if (os.contains("NUX")) {
		    //FileFolder = System.getProperty("user.dir") + ".Launcher";
		    getOS = "Linux";
		} else {
			try {
				throw new UnrecognizableOperatingSystem();
			} catch (UnrecognizableOperatingSystem e) {
				return null;
			}
		}
		
		return getOS;
	}
	
	public static boolean databaseExist() {
		String FileFolder = null;
		if (getOS().equals("Windows")) {
			FileFolder = System.getenv("APPDATA") + "\\" + "Visulizer";
		}
		return false;
	}

}
