package com.fly.domain;

import java.util.List;

public class PageBean {
	private Integer rows; // 单页显示行数
	private Integer page; // 当前 页码
	private Integer totalPage; // 总页数
	private Integer end;

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	private List<Good> list;

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<Good> getList() {
		return list;
	}

	public void setList(List<Good> list) {
		this.list = list;
	}

}
