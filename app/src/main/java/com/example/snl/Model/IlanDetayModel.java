package com.example.snl.Model;

public class IlanDetayModel{
	private String price;
	private String description;
	private String state;
	private String title;
	private String category;
	private String memberId;

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

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
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
			"IlanDetayModel{" + 
			"price = '" + price + '\'' + 
			",description = '" + description + '\'' + 
			",state = '" + state + '\'' + 
			",title = '" + title + '\'' + 
			",category = '" + category + '\'' + 
			",memberId = '" + memberId + '\'' + 
			"}";
		}
}
