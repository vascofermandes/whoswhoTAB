package com.vfernandes.whoswho;

public class Person {

	private String name;
	private String position;
	private String biography;
	private String imageLink;
	
	
	public Person(String name, String position, String biography,
			String imageLink) {
		super();
		this.name = name;
		this.position = position;
		this.biography = biography;
		this.imageLink = imageLink;
		
	}


	public Person() {
		this.name = null;
		this.position = null;
		this.biography = null;
		this.imageLink = null;
		
	
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getBiography() {
		return biography;
	}


	public void setBiography(String biography) {
		this.biography = biography;
	}


	public String getImageLink() {
		return imageLink;
	}


	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	
}
