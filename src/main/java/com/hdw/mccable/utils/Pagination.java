//License HDW
package com.hdw.mccable.utils;

public class Pagination {
	int totalItem; //count all item
	int currentPage;
	int pageSize; //(totalItem / itemPerPage) 
	int itemPerPage;
	int nextPage;
	int prevPage;
	Object dataList; //list form database
	Object dataListBean; //list dto
	String url; // 
	int limitStart;
	int limitEnd;
	
	//total item
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	
	//current item
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}	
	
	public int getPageSize() {
		int divided = totalItem / itemPerPage;
		int fraction = totalItem % itemPerPage;
		if(totalItem <= itemPerPage){
			pageSize = 1;
		}else{
			pageSize = divided;
			
			if(fraction > 0) {
				pageSize = pageSize + 1;
			}
		}
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getItemPerPage() {
		return itemPerPage;
	}
	public void setItemPerPage(int itemPerPage) {
		this.itemPerPage = itemPerPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public Object getDataList() {
		return dataList;
	}
	public void setDataList(Object dataList) {
		this.dataList = dataList;
	}
	public Object getDataListBean() {
		return dataListBean;
	}
	public void setDataListBean(Object dataListBean) {
		this.dataListBean = dataListBean;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLimitStart() {
		if(currentPage <= 1){
			limitStart = 0;
		}else{
			limitStart = ((currentPage * itemPerPage)-1) - (itemPerPage - 1);
		}
		return limitStart;
	}
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public int getLimitEnd() {
		limitEnd = itemPerPage;
		return limitEnd;
	}
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}