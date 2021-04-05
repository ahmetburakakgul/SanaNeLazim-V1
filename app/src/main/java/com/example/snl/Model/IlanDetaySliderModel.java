package com.example.snl.Model;

public class IlanDetaySliderModel{
	private String image;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	@Override
 	public String toString(){
		return 
			"IlanDetaySliderModel{" + 
			"image = '" + image + '\'' + 
			"}";
		}
}
