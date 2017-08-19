package com.fly.domain;

public class Hot {
	private int gid;
	private String name;
	private int hot;

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	@Override
	public String toString() {
		return "Hot [gid=" + gid + ", name=" + name + ", hot=" + hot + "]";
	}

}
