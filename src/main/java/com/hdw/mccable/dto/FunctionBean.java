package com.hdw.mccable.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionBean extends DSTPUtilityBean{
	
	private Long id;
	private String functionName;
	@JsonProperty private boolean isView;
	@JsonProperty private boolean isAdd;
	@JsonProperty private boolean isEdit;
	@JsonProperty private boolean isDelete;
	@JsonProperty private boolean isCheck;
	
	private Long parent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public boolean isView() {
		return isView;
	}
	public void setView(boolean isView) {
		this.isView = isView;
	}
	public boolean isAdd() {
		return isAdd;
	}
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	@Override
	public String toString() {
		return "FunctionBean [id=" + id + ", functionName=" + functionName + ", isView=" + isView + ", isAdd=" + isAdd
				+ ", isEdit=" + isEdit + ", isDelete=" + isDelete + ", isCheck=" + isCheck + ", parent=" + parent
				+ ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate() + ", getCreateBy()="
				+ getCreateBy() + ", getUpdateBy()=" + getUpdateBy() + ", getCreateDateTh()=" + getCreateDateTh()
				+ ", getUpdateDateTh()=" + getUpdateDateTh() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
