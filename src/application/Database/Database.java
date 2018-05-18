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

	public static String retrieveFromDatabase() {
		String data = null;
		FileReader reader = null;
		try {
			reader = new FileReader(Environment.getFile());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader bufferedReader = new BufferedReader(reader);

		try {
			data = bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}

	public static void addSong(String path, String genre, String title, String artist) {
		JsonObject song_info = Json.createObjectBuilder().add("Color", genre.toString()).add("Title", title)
				.add("Artist", artist).build();

		String data = retrieveFromDatabase();

		JsonReader read = Json.createReader(new StringReader(data));

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

	// TODO: Add a check to see if song is in datdabase
	
}
