package com.example.snl.Model;

public class FavoriListeSliderModel{
	private Object advertisementId;
	private boolean tf;
	private Object imagepath;

	public void setAdvertisementId(Object advertisementId){
		this.advertisementId = advertisementId;
	}

	public Object getAdvertisementId(){
		return advertisementId;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setImagepath(Object imagepath){
		this.imagepath = imagepath;
	}

	public Object getImagepath(){
		return imagepath;
	}

	@Override
 	public String toString(){
		return 
			"FavoriListeSliderModel{" + 
			"advertisementId = '" + advertisementId + '\'' + 
			",tf = '" + tf + '\'' + 
			",imagepath = '" + imagepath + '\'' + 
			"}";
		}
}
