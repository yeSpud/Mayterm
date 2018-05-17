package application.Database;

import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class Database {

	public static boolean databaseExist() {
		return (Environment.getFile().exists() && Environment.getFile().canWrite());
	}

	public static void createDatabase() {

		JsonArray settings = Json.createArrayBuilder().build();
		// JsonObject settings = Json.createObjectBuilder().add("Settings", "").build();
		/*
		JsonObject song_info = Json.createObjectBuilder().add("Color", genre.ELECTRONIC.toString()).add("Title", "foo")
				.add("Artist", "bar").add("Cover art", "").build();
				*/
		//JsonObject song_info = Json.createObjectBuilder().build();
		//JsonObject songs = Json.createObjectBuilder().add("path", song_info).build();
		JsonObject songs = Json.createObjectBuilder().build();
		JsonObject json = Json.createObjectBuilder().add("Settings", settings).add("Songs", songs).build();
		String result = json.toString();

		System.out.println(result);

		try {
			Environment.getFile().createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writeToDatabase(result);

	}

	public static void writeToDatabase(String data) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(Environment.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fileWriter.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fileWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addSong(String path, Enum<?> genre, String title, String artist) {
		JsonObject song_info = Json.createObjectBuilder()
				.add("Color", genre.toString())
				.add("Title", title)
				.add("Artist", artist)
				.build();
		JsonObject song = Json.createObjectBuilder().add(path, song_info).build();
		
		// TODO: Add song to database, in object called "Songs"
	}
	
}
