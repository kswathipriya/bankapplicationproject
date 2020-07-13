package com.dxc.pojos;

public class bankusers {
	int accno;
	String password;
	int balance;
	
	public bankusers(int accno, String password, int balance) {
		
		this.accno = accno;
		this.password = password;
		this.balance = balance;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
}
