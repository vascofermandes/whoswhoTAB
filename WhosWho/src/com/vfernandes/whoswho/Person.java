package com.vfernandes.whoswho;

import android.graphics.Bitmap;

public class Person {

	private String name;
	private String position;
	private String biography;
	private String imageLink;
	private Bitmap photo;
	
	
	public Person(String name, String position, String biography,
			String imageLink) {
		super();
		this.name = name;
		this.position = position;
		this.biography = biography;
		this.imageLink = imageLink;
		this.photo =null;
	}


	public Person() {
		this.name = null;
		this.position = null;
		this.biography = null;
		this.imageLink = null;
		this.photo = null;
		
	
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


	public Bitmap getPhoto() {
		return photo;
	}


	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
	
}
