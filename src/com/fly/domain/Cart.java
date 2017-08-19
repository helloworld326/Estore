package com.fly.domain;

public class Cart {
	private int uid;
	private int gid;
	private int buynum;

	private Good good;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getBuynum() {
		return buynum;
	}

	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	@Override
	public String toString() {
		return "Cart [uid=" + uid + ", gid=" + gid + ", buynum=" + buynum + ", good=" + good + "]";
	}
}
