package com.example.snl.Model;

public class SifremiUnuttumModel{
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
			"SifremiUnuttumModel{" + 
			"tf = '" + tf + '\'' + 
			"}";
		}
}
