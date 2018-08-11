package com.cdvcloud.rochecloud.common;

import java.util.List;

public class Pages<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	

	/**每页显示多少条记录**/
	private Integer numPerPage=10;
	/** 总的记录数**/
	private Integer totalNum;
	/** 当前第几页**/
	private Integer currentPage=1;
//	/** 当前第几页的一共的的记录数**/
//	private Integer currentNum;
	/** 分页记录**/
	private  List<T> list;
	/** 查询条件**/
	private String condition;
	/** 排序字段*/
	private String order="createTime" ;
	/** 排序顺序*/
	private String orderBy = "desc";
	/**特殊的参数**/
	private  Object tempParam;
	
	/**特殊的参数2**/
	private String tempParamtwo;
	
	
	public String getTempParamtwo() {
		return tempParamtwo;
	}

	public void setTempParamtwo(String tempParamtwo) {
		this.tempParamtwo = tempParamtwo;
	}
	
	
	

	public Object getTempParam() {
		return tempParam;
	}

	public void setTempParam(Object tempParam) {
		this.tempParam = tempParam;
	}

	public Pages() {
		// 
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getCurrentPage() {
		if(totalNum!=null){
			int totalTruePage=(totalNum+numPerPage-1)/numPerPage;
			if(currentPage>totalTruePage){
				currentPage=totalTruePage;
			}
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageCount() {
		return (totalNum+numPerPage-1)/numPerPage;
	}
	public Integer getCurrentNum() {
		int totalTruePage=(totalNum+numPerPage-1)/numPerPage;
		if(currentPage>totalTruePage){
			currentPage=totalTruePage;
		}
		if(currentPage<1){
			currentPage=1;
		}
		return (currentPage-1)*numPerPage;
	}
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
