package application.Database;

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
import javax.json.JsonValue;

import application.UI.Genre.genre;

public class Database {

	public static boolean databaseExist() {
		return (Environment.getFile().exists() && Environment.getFile().canWrite());
	}

	public static void createDatabase() {

		JsonArray settings = Json.createArrayBuilder().build();
		JsonObject songs = Json.createObjectBuilder().build();
		JsonObject json = Json.createObjectBuilder().add("Settings", settings).add("Songs", songs).build();
		String result = json.toString();

		System.out.println(result);

		try {
			Environment.getFile().getParentFile().mkdirs();
			Environment.getFile().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		writeToDatabase(result);

	}

	public static void writeToDatabase(String data) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(Environment.getFile());
			fileWriter.write(data);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public static String retrieveFromDatabase() {
		String data = null;
		FileReader reader = null;
		try {
			reader = new FileReader(Environment.getFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		BufferedReader bufferedReader = new BufferedReader(reader);

		try {
			data = bufferedReader.readLine();
			bufferedReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return data;

	}

	public static void addSong(String path, genre genre, String title, String artist) {
		JsonObject song_info = Json.createObjectBuilder().add("Color", genre.toString()).add("Title", title)
				.add("Artist", artist).build();

		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));

		JsonObject full = read.readObject();
		read.close();

		JsonObject songs = full.getJsonObject("Songs");

		String songsArray = songs.toString();
		if (songsArray.isEmpty() || songsArray.equals("{}")) {
			songsArray = String.format("{\"%s\":%s}", path, song_info.toString());
		} else {
			songsArray = String.format("%s,\"%s\":%s}", songsArray.replace("}}", "}"), path, song_info.toString());
		}

		JsonReader newread = Json.createReader(new StringReader(songsArray));
		JsonObject newsong = Json.createObjectBuilder().add("Settings", full.get("Settings"))
				.add("Songs", newread.read()).build();
		newread.close();
		writeToDatabase(newsong.toString());

	}

	public static boolean isInDatabase(String path) {
		
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		
		return songs.containsKey(path);
		
	}
	
	public static String getArtist(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Artist").toString().replace("\"", "");
	}
	
	public static String getTitle(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Title").toString().replace("\"", "");
	}
	
	public static String getGenre(String path) {
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		return specific_song.get("Color").toString().replace("\"", "");
	}
	
	public static void editArtist(String path, String newArtist) {
		// TODO: Finish this
	}
	
	public static void editTitle(String path, String newTitle) {
		// TODO: Finish this
	}
	
	public static void editGenre(String path, genre newGenre) {
		// TODO: Finish this
		JsonReader read = Json.createReader(new StringReader(retrieveFromDatabase()));
		JsonObject fill = read.readObject();
		read.close();
		JsonObject songs = fill.getJsonObject("Songs");
		JsonObject specific_song = songs.getJsonObject(path);
		JsonValue oldGenre = specific_song.get("Color");
		System.out.println(oldGenre.toString().replace("\"", ""));
		
		
	}

}
