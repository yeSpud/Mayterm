package javacode;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TODO Documentation
 */
public class Database {


	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	public static Connection getDatabase() {
		Connection connection;

		// Check if file exists first
		File databaseFile = Database.getDatabaseFile();
		if (!databaseFile.exists()) {
			// TODO Check for correct headers
			try {
				Debugger.d(Database.class, String.format("Successfully created new database file at the location %s: %s ", databaseFile.getAbsolutePath(), databaseFile.createNewFile()));

				Database.createTable();

			} catch (java.io.IOException | SecurityException e) {
				e.printStackTrace();
				return null;
			}

		}

		// Get the database connection based on the file
		try {
			Class.forName("org.sqlite.JDBC");
			connection = java.sql.DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		return connection;
	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	private static File getDatabaseFile() {
		File file;

		// Detect the OS to get the database location
		String OS = System.getProperty("os.name").toUpperCase();
		Debugger.d(Database.class, "OS: " + OS);
		if (OS.contains("WIN")) {
			// Windows
			file = new File(String.format("%s\\Spud\\database.db", System.getenv("APPDATA")));
		} else if (OS.contains("MAC")) {
			// macOS
			file = new File(String.format("%s/Library/Application Support/Spud/database.db", System.getProperty("user.home")));
		} else {
			// Linux or Other
			file = new File(String.format("%s/.Spud/database.db", System.getProperty("user.dir")));
		}

		return file;
	}

	/**
	 * TODO Documentation
	 *
	 * @param fileLocation
	 * @return
	 */
	public static Track[] getTrackByLocation(String fileLocation) {
		// TODO Query the database for any tracks that have the same file location

		return null;
	}

	/**
	 * TODO Documentation
	 *
	 * @param artist
	 * @return
	 */
	public static Track[] getTrackByArtist(String artist) {
		// TODO Query the database for any tracks that have the same artist

		return null;
	}

	/**
	 * TODO Documentation
	 *
	 * @param title
	 * @return
	 */
	public static Track[] getTrackByTitle(String title) {
		// TODO Query the database for any tracks that have the same title

		return null;
	}

	/**
	 * TODO Documentation
	 *
	 * @param genreColors
	 * @return
	 */
	public static Track[] getTrackByGenre(GenreColors genreColors) {
		// TODO Query the database for any tracks that have the same genre

		return null;
	}

	/**
	 * TODO Documentation
	 *
	 * @param track
	 */
	public static void saveToDatabase(Track track) {
		// TODO Write track to database
	}

	/**
	 * TODO Documentation
	 *
	 * @param track
	 * @param newGenre
	 */
	public static void updateGenre(Track track, GenreColors newGenre) {
		// TODO Update the genre for the given track
	}

	/**
	 * TODO Documentation
	 */
	private static void createTable() {
		Connection connection = Database.getDatabase();

		if (connection != null) {

			String query = ("CREATE TABLE IF NOT EXISTS track ('file location' TEXT NOT NULL UNIQUE PRIMARY KEY, 'title' TEXT, 'artist' TEXT, 'genre' TEXT)");

			try {
				Statement statement = connection.createStatement();
				statement.execute(query);

				statement.close();
				connection.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}

		}

	}

}
