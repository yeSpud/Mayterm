package javacode;

import javacode.Audio.Track;
import org.sqlite.JDBC;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * TODO Documentation
 */
public class Database {


	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	private static Connection getDatabase() {
		Connection connection;

		// Check if file exists first
		File databaseFile = Database.getDatabaseFile();
		if (!databaseFile.exists()) {
			try {
				Debugger.d(Database.class, String.format("Successfully created new database file at the location %s: %s ",
						databaseFile.getAbsolutePath(), databaseFile.createNewFile()));
				Database.createTable();

			} catch (java.io.IOException | SecurityException e) {
				e.printStackTrace();
				return null;
			}

		}

		// Get the database connection based on the file
		try {
			Class.forName("org.sqlite.JDBC");
			connection = java.sql.DriverManager.getConnection(JDBC.PREFIX + databaseFile.getAbsolutePath());
		} catch (SQLException | ClassNotFoundException sqlError) {
			sqlError.printStackTrace();
			return null;
		}

		return connection;
	}

	/**
	 * Gets the file object of the database. This is dependent on OS.
	 *
	 * @return The file object of the database file.
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
	 * @param title
	 * @param artist
	 * @return
	 * @throws
	 */
	public static Track[] getTrackFromDatabase(String fileLocation, String title, String artist) throws IOException {
		Connection connection = Database.getDatabase();
		if (connection != null) {
			try {

				String SQL = "SELECT \"file location\", \"title\", \"artist\", \"genre\" FROM track WHERE \"file location\" = ? AND \"title\" = ? AND \"artist\" = ?";
				PreparedStatement statement = connection.prepareStatement(SQL);
				statement.setString(1, fileLocation);
				statement.setString(2, title);
				statement.setString(3, artist);
				Debugger.d(Database.class, "Executing query: " + SQL);

				if (!statement.execute()) {
					return null;
				} else {
					ResultSet result = statement.getResultSet();

					ArrayList<Track> tracks = new ArrayList<>();

					while (result.next()) {
						tracks.add(new Track(result.getString("file location"), result.getString("title"),
								result.getString("artist"), GenreColors.valueOf(result.getString("genre"))));
						Debugger.d(Database.class, "Found track: " + tracks.get(tracks.size() - 1).Title);
					}

					// Close the driver to free up resources
					statement.close();
					connection.close();

					// If the length is 0, return null
					if (tracks.size() == 0) {
						Debugger.d(Database.class, "No such entry");
						return null;
					} else {
						//noinspection ToArrayCallWithZeroLengthArrayArgument
						return tracks.toArray(new Track[tracks.size()]);
					}
				}

			} catch (SQLException malSQL) {
				return null;
			}

		} else {
			throw new IOException("Database connection is null!");
		}
	}

	/**
	 * TODO Documentation
	 *
	 * @param track
	 */
	public static void saveToDatabase(Track track) throws IOException {
		Connection connection = Database.getDatabase();

		if (connection != null) {

			try {
				String sql = "INSERT INTO track (\"file location\", \"title\", \"artist\", \"genre\") VALUES (?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, track.fileLocation);
				statement.setString(2, track.Title);
				statement.setString(3, track.Artist);
				statement.setString(4, track.Genre.name());
				Debugger.d(Database.class, "Executing query: " + sql);

				statement.executeUpdate();

				statement.close();
				connection.close();
			} catch (SQLException SQLError) {
				throw new IOException("Error while saving to database (SQL error)");
			}

		} else {
			throw new IOException("Error while saving to database (Connection error)");
		}
	}

	/**
	 * TODO Documentation
	 *
	 * @param track
	 * @param newGenre
	 */
	public static void updateGenre(Track track, GenreColors newGenre) {
		Connection connection = Database.getDatabase();

		if (connection != null) {

			try {

				Debugger.d(Database.class, String.format("Updating %s genre to %s", track.Title, newGenre.name()));

				String sql = "UPDATE track SET \"genre\" = ? WHERE \"file location\" = ? AND \"title\" = ? AND \"artist\" = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, newGenre.name());
				statement.setString(2, track.fileLocation);
				statement.setString(3, track.Title);
				statement.setString(4, track.Artist);
				Debugger.d(Database.class, "Executing query: " + sql);

				statement.executeUpdate();

				statement.close();
				connection.close();

			} catch (SQLException malSQL) {
				malSQL.printStackTrace();
			}

		}
	}

	/**
	 * Creates a new table in the database to store all the tracks.
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
