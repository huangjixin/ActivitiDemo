/**
 * 
 */
package com.zhiyesoft.activiti.demo.core.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 黄记新
 *
 */
@Data
public class FormField implements Serializable {
	
	public FormField(String fieldText, String fieldValue) {
		super();
		this.fieldText = fieldText;
		this.fieldValue = fieldValue;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldText;
	private String fieldValue;

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}
