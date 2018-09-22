package model;

public class LibEntry {
	private String title;
	private String artist;
	private String album;
	private String year;
	
	public LibEntry(){
		this.title = null;
		this.artist = null;
		this.album = null;
		this.year = null;
	}
	
	public LibEntry(String title, String artist, String album, String year){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public LibEntry setTitle(String title){
		this.title = title;
		return this;
	}
	
	public String getArtist(){
		return this.artist;
	}
	
	public LibEntry setArtist(String artist){
		this.artist = artist;
		return this;
	}
	
	public String getAlbum(){
		return this.album;
	}
	
	public LibEntry setAlbum(String album){
		this.album = album;
		return this;
	}
	
	public String getYear(){
		return this.year;
	}
	
	public LibEntry setYear(String year){
		this.year = year;
		return this;
	}
	
	@Override
	public String toString(){
		return this.title + "," + this.artist + "," + this.album + "," + this.year;
	}
}
