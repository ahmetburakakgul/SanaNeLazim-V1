package com.example.snl.Model;

public class FavoriListelemeModel{
	private String advertisementId;
	private String image;
	private boolean tf;
	private String price;
	private String description;
	private int sayi;
	private String title;
	private String memberId;

	public void setAdvertisementId(String advertisementId){
		this.advertisementId = advertisementId;
	}

	public String getAdvertisementId(){
		return advertisementId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setSayi(int sayi){
		this.sayi = sayi;
	}

	public int getSayi(){
		return sayi;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	@Override
 	public String toString(){
		return 
			"FavoriListelemeModel{" + 
			"advertisementId = '" + advertisementId + '\'' + 
			",image = '" + image + '\'' + 
			",tf = '" + tf + '\'' + 
			",price = '" + price + '\'' + 
			",description = '" + description + '\'' + 
			",sayi = '" + sayi + '\'' + 
			",title = '" + title + '\'' + 
			",memberId = '" + memberId + '\'' + 
			"}";
		}
}
