package com.zhiyesoft.activiti.demo.core.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 请假表单
 * 
 * @author 黄记新
 *
 */
public class VacationForm extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 开始日期
	private String startDate;
	// 结束日期
	private String endDate;
	// 天数
	private String days;
	// 小时数
	private String hours;
	// 类型 (默认事假)
	private String vacationType = VacationType.TYPE_MATTER;
	// 原因
	private String reason;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getVacationType(String vacationType) {
		if (VacationType.TYPE_MATTER.equals(vacationType)) {
			return "事假";
		}
		if (VacationType.TYPE_PAID.equals(vacationType)) {
			return "年假";
		}
		if (VacationType.TYPE_SICK.equals(vacationType)) {
			return "病假";
		}
		return vacationType;
	}

	public String getVacationType() {
		return vacationType;
	}

	public void setVacationType(String vacationType) {
		this.vacationType = vacationType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public void createFormFields(List<FormField> list) {
		list.add(getFormField("startDate", "开始日期", this.startDate));
		list.add(getFormField("endDate", "结束日期", this.endDate));
		list.add(getFormField("days", "天数", this.days));
		list.add(getFormField("hours", "小时数", this.hours));
		list.add(getFormField("title", "类型", this.getVacationType(this.vacationType)));
		list.add(getFormField("reason", "原因", this.reason));
	}

}
