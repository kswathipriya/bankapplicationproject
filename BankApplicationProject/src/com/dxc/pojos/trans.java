package com.dxc.pojos;
public class trans {
int accno,timestamp,amount;
String type;
public int getAccno() {
	return accno;
}
public void setAccno(int accno) {
	this.accno = accno;
}
public int getTimestamp() {
	return timestamp;
}
public void setTimestamp(int timestamp) {
	this.timestamp = timestamp;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public String getType() {
	System.out.println(type);
	return type;
}
public void setType(String type) {
	this.type = type;
}
public trans(int accno, int timestamp, int amount, String type) {
	
	this.accno = accno;
	this.timestamp = timestamp;
	this.amount = amount;
	this.type = type;
}
public trans() {
	
}

}
