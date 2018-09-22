package model;

public class LibEntry {
	private String title;
	private String artist;
	private String album;
	private int year;
	
	public LibEntry(){
		this.title = null;
		this.artist = null;
		this.album = null;
		this.year = -1;
	}
	
	public LibEntry(String title, String artist, String album, int year){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getArtist(){
		return this.artist;
	}
	
	public void setArtist(String artist){
		this.artist = artist;
	}
	
	public String getAlbum(){
		return this.album;
	}
	
	public void setAlbum(String album){
		this.album = album;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public void setYear(int year){
		this.year = year;
	}
}
