package com.zhiyesoft.activiti.demo.core.vo;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

@Data
public class Response implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String code = "200";
	private String message = "success";
	private Object data;
	private List dataList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

}
