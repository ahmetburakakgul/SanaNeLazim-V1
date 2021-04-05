package com.example.snl.Model;

public class AccountUpdateModel{
	private boolean tf;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	@Override
 	public String toString(){
		return 
			"AccountUpdateModel{" + 
			"tf = '" + tf + '\'' + 
			"}";
		}
}
