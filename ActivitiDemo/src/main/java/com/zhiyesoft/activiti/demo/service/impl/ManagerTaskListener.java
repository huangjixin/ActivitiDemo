/**
 * 
 */
package com.zhiyesoft.activiti.demo.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.zhiyesoft.activiti.demo.core.vo.BaseForm;

/**
 * @author admin
 *
 */
public class ManagerTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		
//		String assignee = delegateTask.getAssignee();
//		if(assignee==null || "".equals(assignee)) {
//			return;
//		}
		Object  object = delegateTask.getVariable("arg");
		
		if(object instanceof BaseForm ) {
			BaseForm baseForm = (BaseForm) object;
			//通过baseForm.getUserId()找到上级，此处写死。
			delegateTask.setAssignee("f841cd8f7a3f4fbc84783b0578c3304e");
		}
		// 通过找到当前节点对于的上级，把任务给委托过去。
//		

	}

}
