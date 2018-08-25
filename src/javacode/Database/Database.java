package javacode.Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import javacode.Debugger;
import javacode.Errors.DatabaseError;
import javacode.Errors.UnrecognizableOperatingSystem;
import javacode.UI.Genre.genre;

/**
 * Class responsible for handling the creating, and handling the database.
 * 
 * @author Spud
 *
 */
public class Database {

	/**
	 * Check to see if the database directory exists, and if the program can write
	 * to that location.
	 * 
	 * @return Boolean based on whether or not the database exists on the system,
	 *         and if it can write to it. If there is an error, the application will
	 *         terminate.
	 */
	public static boolean databaseExist() {
		boolean r = false;
		try {
			r = (Environment.getDatabaseFile().exists() && Environment.getDatabaseFile().canWrite());
		} catch (UnrecognizableOperatingSystem e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return r;
	}

	/**
	 * Attempts to create the database if it does not already exist on the system.
	 */
	public static void createDatabase() {
		JsonArray settings = Json.createArrayBuilder().add(0).build();
		JsonObject songs = Json.createObjectBuilder().build();
		JsonObject json = Json.createObjectBuilder().add("Settings", settings).add("Songs", songs).build();
		String result = json.toString();
		try {
			Environment.getDatabaseFile().getParentFile().mkdirs();
			Environment.getDatabaseFile().createNewFile();
		} catch (IOException | UnrecognizableOperatingSystem e) {
			e.printStackTrace();
		}
		try {
			writeToDatabase(result);
		} catch (DatabaseError e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes data to the database. It should be noted that this function will
	 * <b><i>overwrite</i></b> existing data. <b>It does not append to the file</b>.
	 * 
	 * @param data - The data that is going to be written to the file.
	 * @throws DatabaseError Either Unable to write, invalid operating system, or
	 *                       issue closing writer.
	 */
	public static void writeToDatabase(String data) throws DatabaseError {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(Environment.getDatabaseFile());
		} catch (IOException e) {
			throw new DatabaseError("Cannot create writer for database.");
		} catch (UnrecognizableOperatingSystem e) {
			throw new DatabaseError("Cannot write database, as operating system is not valid.");
		}
		try {
			fileWriter.write(data);
		} catch (IOException e) {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				throw new DatabaseError("Cannot close writer for database!");
			}
			throw new DatabaseError("Cannot write to database.");
		}
		try {
			fileWriter.flush();
		} catch (IOException e) {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				throw new DatabaseError("Cannot close writer for database!");
			}
			throw new DatabaseError("Cannot flush writer for database.");
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			throw new DatabaseError("Cannot close writer for database.");
		}

	}

	/**
	 * Reads and returns the contents of the database.
	 * 
	 * @return The data from the database.
	 */
	public static String retrieveFromDatabase() {
		String data = null;
		FileReader reader = null;
		try {
			reader = new FileReader(Environment.getDatabaseFile());
		} catch (FileNotFoundException | UnrecognizableOperatingSystem e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			data = bufferedReader.readLine();
			bufferedReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Adds a track to the database.
	 * 
	 * @param path   - The file path of the song.
	 * @param genre  - The genre.
	 * @param title  - The title.
	 * @param artist - The artist.
	 */
	public static void addTrack(String path, genre genre, String title, String artist) {
		try {
			Debugger.d(Database.class,
					String.format("Path: %s\nGenre: %s\nTitle: %s\nArtist: %s", path, genre.toString(), title, artist));

			try {
			if (title.isEmpty()) {
				title = "No Title";
			}
			} catch (NullPointerException nothing) {
				title = "No Title";
			}
			try {
			if (artist.isEmpty() ) {
				artist = "No Artist";
			}
			} catch (NullPointerException nothing) {
				title = "No Artist";
			}
			
			JsonObject track_info = Json.createObjectBuilder().add("Color", genre.toString()).add("Title", title)
					.add("Artist", artist).build();
			Debugger.d(Database.class, "Track info: " + track_info.toString());

			JsonReader jsonIn = Json.createReader(new StringReader(retrieveFromDatabase()));
			JsonObject fullJson = jsonIn.readObject();
			jsonIn.close();
			Debugger.d(Database.class, "Full jSon: " + fullJson.toString());

			JsonObject track_list = fullJson.getJsonObject("Songs");
			String trackArray = track_list.toString();
			Debugger.d(Database.class, "Track array: " + trackArray);

			if (trackArray.isEmpty() || trackArray.equals("{}")) {
				Debugger.d(Database.class, "Is track array empty? True");
				trackArray = String.format("{\"%s\":%s}", path.replace("\\", "\\\\"), track_info.toString());
			} else {
				Debugger.d(Database.class, "Is track array empty? False");
				trackArray = String.format("%s,\"%s\":%s}", trackArray.replace("}}", "}"), path.replace("\\", "\\\\"),
						track_info.toString());
			}

			Debugger.d(Database.class, "Updated track array: " + trackArray);

			JsonReader jsonOut = Json.createReader(new StringReader(trackArray));
			JsonObject newsong = Json.createObjectBuilder().add("Settings", fullJson.get("Settings"))
					.add("Songs", jsonOut.read()).build();
			jsonOut.close();
			writeToDatabase(newsong.toString());
		} catch (Exception e) {
			Debugger.d(Database.class, "Cannot add: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Returns if the provided path is in the database.
	 * 
	 * @param path - The path of the track.
	 * @return Boolean based on whether or not the path is in the database.
	 */
	public static boolean isInDatabase(String path) {
		Debugger.d(Database.class, "Path: " + path);

		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		return songs.containsKey(path);
	}

	/**
	 * Gets the artist of a specific file from the database.
	 * 
	 * @param path - Path of the file that is in the database.
	 * @return The artist.
	 */
	public static String getArtist(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Artist").toString().replace("\"", "");
	}

	/**
	 * Gets the title of a specific file from the database.
	 * 
	 * @param path - Path of the file that is in the database.
	 * @return The title.
	 */
	public static String getTitle(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Title").toString().replace("\"", "");
	}

	/**
	 * Gets the genre of a specific file from the database.
	 * 
	 * @param path - Path of the file that is in the database.
	 * @return The genre.
	 */
	public static String getGenre(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Color").toString().replace("\"", "");
	}

	/**
	 * Overrides and edits the artist in the database.
	 * 
	 * @param path      - Path of the file that is in the database.
	 * @param newArtist - The new artist name.
	 */
	public static void editArtist(String path, String newArtist) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject full = read.readObject();
		read.close();
		JsonObject songs = full.getJsonObject("Songs");
		JsonObject song_info = Json.createObjectBuilder().add("Color", getGenre(path)).add("Title", getTitle(path))
				.add("Artist", newArtist.toString()).build();
		String songsArray = songs.toString();
		if (songsArray.isEmpty() || songsArray.equals("{}")) {
			songsArray = String.format("{\"%s\":%s}", path, song_info.toString());
		} else {
			songsArray = String.format("%s,\"%s\":%s}", songsArray.replace("}}", "}"), path, song_info.toString());
		}
		JsonReader newread = Json.createReader(new StringReader(songsArray));
		JsonObject newValue = Json.createObjectBuilder().add("Settings", full.get("Settings"))
				.add("Songs", newread.read()).build();
		newread.close();
		try {
			writeToDatabase(newValue.toString());
		} catch (DatabaseError e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overrides and edits the title in the database.
	 * 
	 * @param path     - Path of the file that is in the database.
	 * @param newTitle - The new title name.
	 */
	public static void editTitle(String path, String newTitle) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject full = read.readObject();
		read.close();
		JsonObject songs = full.getJsonObject("Songs");
		JsonObject song_info = Json.createObjectBuilder().add("Color", getGenre(path)).add("Title", newTitle.toString())
				.add("Artist", getArtist(path)).build();
		String songsArray = songs.toString();
		if (songsArray.isEmpty() || songsArray.equals("{}")) {
			songsArray = String.format("{\"%s\":%s}", path, song_info.toString());
		} else {
			songsArray = String.format("%s,\"%s\":%s}", songsArray.replace("}}", "}"), path, song_info.toString());
		}
		JsonReader newread = Json.createReader(new StringReader(songsArray));
		JsonObject newValue = Json.createObjectBuilder().add("Settings", full.get("Settings"))
				.add("Songs", newread.read()).build();
		newread.close();
		try {
			writeToDatabase(newValue.toString());
		} catch (DatabaseError e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overrides and edits the genre in the database.
	 * 
	 * @param path     - Path of the file that is in the database.
	 * @param newGenre - The new genre.
	 */
	public static void editGenre(String path, genre newGenre) {
		Debugger.d(Database.class, String.format("Path: %s\nGenre: %s", path, newGenre.toString()));

		JsonReader jsonIn = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fullJson = jsonIn.readObject();
		jsonIn.close();
		Debugger.d(Database.class, "Full json: " + fullJson.toString());

		JsonObject tracks = fullJson.getJsonObject("Songs");
		Debugger.d(Database.class, "Tracks: " + tracks.toString());

		JsonObject track_info = Json.createObjectBuilder().add("Color", newGenre.toString())
				.add("Title", getTitle(path)).add("Artist", getArtist(path)).build();
		Debugger.d(Database.class, "Track info: " + track_info.toString());

		String trackArray = tracks.toString();
		Debugger.d(Database.class, "Track array: " + trackArray);

		if (trackArray.isEmpty() || trackArray.equals("{}")) {
			Debugger.d(Database.class, "Is track array empty? True");
			trackArray = String.format("{\"%s\":%s}", path.replace("\\", "\\\\"), track_info.toString());
		} else {
			Debugger.d(Database.class, "Is track array empty? False");
			trackArray = String.format("%s,\"%s\":%s}", trackArray.replace("}}", "}"), path.replace("\\", "\\\\"),
					track_info.toString());
		}
		Debugger.d(Database.class, "Updated track array: " + trackArray);

		JsonReader newread = Json.createReader(new StringReader(trackArray));
		JsonObject newValue = Json.createObjectBuilder().add("Settings", fullJson.get("Settings"))
				.add("Songs", newread.read()).build();
		newread.close();

		try {
			writeToDatabase(newValue.toString());
		} catch (DatabaseError e) {
			e.printStackTrace();
		}
	}

	public static void editSettings(int value) {
		Debugger.d(Database.class, "Value: " + value);
		
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject full = read.readObject();
		read.close();
		Debugger.d(Database.class, "jSon output: " + full.toString());
		
		JsonArray in = getSettings();
		Debugger.d(Database.class, "Old settings: " + in);
		
		try {
			in.set(0, Json.createValue(value));
		} catch (UnsupportedOperationException NothingEntered) {
			in = Json.createArrayBuilder().add(value).build();
		}
		Debugger.d(Database.class, "New settings: " + in);
		
		JsonObject newJson = Json.createObjectBuilder().add("Settings", in).add("Songs", full.get("Songs")).build();
		Debugger.d(Database.class, "New database: " + newJson.toString());
		
		try {
			writeToDatabase(newJson.toString());
		} catch (DatabaseError e) {
			e.printStackTrace();
		}
		
	}
	
	public static JsonArray getSettings() {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject full = read.readObject();
		read.close();
		Debugger.d(Database.class, "jSon output: " + full.toString());
		
		JsonArray settings = full.getJsonArray("Settings");
		Debugger.d(Database.class, "Settigns array: " + settings);
		
		return settings;
	}
	
}
