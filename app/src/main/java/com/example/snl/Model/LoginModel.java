package com.example.snl.Model;

public class LoginModel{
	private Object id;
	private Object email;

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"LoginModel{" + 
			"id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}
