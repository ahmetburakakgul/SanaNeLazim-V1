package com.example.snl.Model;

public class AdvertisementResultModel{
	private String advertisementId;
	private boolean tf;
	private int memberId;

	public void setAdvertisementId(String advertisementId){
		this.advertisementId = advertisementId;
	}

	public String getAdvertisementId(){
		return advertisementId;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setMemberId(int memberId){
		this.memberId = memberId;
	}

	public int getMemberId(){
		return memberId;
	}

	@Override
	public String toString(){
		return
				"Response{" +
						"advertisementId = '" + advertisementId + '\'' +
						",tf = '" + tf + '\'' +
						",memberId = '" + memberId + '\'' +
						"}";
	}
}
