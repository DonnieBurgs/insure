package com.juyou.app.model;

public class EmReceipt extends BaseModel {
	public long id;
	public long claimid;
	public String receiptnumber;
	public long hospitalid;
	public String visitdate;
	public String hospitaldate;
	public String dischargedate;
	public float fundpaid;
	public float cashpaid;
	public float total;
}
