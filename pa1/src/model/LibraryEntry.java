/*
    Javier Vidal
    Jimmy Wen
*/
package model;

public class LibraryEntry {

	private String title;
	private String artist;
	private String album;
	private String year;

	public LibraryEntry(final String title, final String artist, final String album, final String year){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(final String title){ this.title = title; }
	
	public String getArtist(){
		return this.artist;
	}
	
	public void setArtist(final String artist){ this.artist = artist; }
	
	public String getAlbum(){
		return this.album;
	}
	
	public void setAlbum(final String album){ this.album = album; }
	
	public String getYear(){
		return this.year;
	}
	
	public void setYear(final String year){ this.year = year; }
	
	public String toDetailedString(){
		return this.title + "\n" + this.artist + "\n" + this.album + "\n" + this.year + "\n";
	}
        
	@Override
	public String toString(){
		return this.title + "\n" + this.artist + "\n";
	}
}
