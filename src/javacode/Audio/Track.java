package javacode.Audio;

import javacode.GenreColors;

import java.util.ArrayList;

/**
 * TODO Documentation
 */
public class Track {

	public String fileLocation, Title, Artist;
	public GenreColors Genre;

	public Track(String fileLocation, String Title, String Artist, GenreColors Genre) {

		this.fileLocation = fileLocation;
		this.Title = Title;
		this.Artist = Artist;
		this.Genre = Genre;

	}

	/**
	 * TODO Documentation
	 *
	 * @return
	 */
	public static Track[] tracksInCommon(Track[] locations, Track[] titles, Track[] artists, Track[] genres) {
		ArrayList<Track> tracksInCommon = new ArrayList<>();

		// Systematically sort though all the tracks in the variables, and add them to the in common list if they are the same
		for (Track track0 : locations) {
			for (Track track1 : titles) {
				for (Track track2 : artists) {
					for (Track track3 : genres) {
						if (track0.equals(track1) && track0.equals(track2) && track0.equals(track3)) {
							tracksInCommon.add(track0);
						}
					}
				}
			}
		}

		//noinspection ToArrayCallWithZeroLengthArrayArgument
		return tracksInCommon.toArray(new Track[tracksInCommon.size()]);
	}

}
