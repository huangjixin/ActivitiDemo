package com.zhiyesoft.activiti.demo.core.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseForm {
	// 申请日期
	private String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	// 申请人ID
	private String userId;
	// 申请人名称
	private String userName;
	// 申请类型
	private String businessType;
	// 表单标题
	private String title;
	// 表单域列表
	private List<FormField> formFields = new ArrayList<FormField>();
	//
	private Map<String, FormField> fileMap = new HashMap<String, FormField>();

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FormField> getFormFields() {
		this.formFields.add(getFormField("createDate", "申请日期", this.createDate));
		this.formFields.add(getFormField("userId", "申请人ID", this.userId));
		this.formFields.add(getFormField("userName", "申请人名称", this.userName));
		this.formFields.add(getFormField("businessType", "申请类型", this.businessType));
		this.formFields.add(getFormField("title", "表单标题", this.title));
		createFormFields(this.formFields);
		return formFields;
	}

	protected FormField getFormField(String key, String text, String value) {
		if (fileMap.get(key) == null) {
			FormField formField = new FormField(text, value);
			formField.setFieldText(text);
			formField.setFieldValue(value);
			fileMap.put(key, formField);
		}
		return fileMap.get(key);
	}

	public abstract void createFormFields(List<FormField> list);
}
