package com.digitalchina.webapp.prog.vo;

import java.io.Serializable;

public class PageVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer totalPage;// 总页数
	private Integer totalRow;// 总记录数
	private Integer pageIndex;// 当前页数
	private Integer pageSize;// 每页记录数

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
