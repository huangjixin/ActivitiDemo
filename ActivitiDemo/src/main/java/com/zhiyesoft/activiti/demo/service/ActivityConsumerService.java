package com.zhiyesoft.activiti.demo.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhiyesoft.activiti.demo.core.vo.BaseForm;

public interface ActivityConsumerService {

	/**
	 * 部署流程
	 * 
	 * @return
	 */
	void deploy();

	/**
	 * 启动流程
	 * 
	 * @param Key
	 * @param map
	 */
	void startProcessInstanceByKey(String Key, BaseForm baseForm);

	/**
	 * 执行节点任务
	 * 
	 * @param taskId
	 * @param map
	 */
	void executeTask(String taskId,String handlerUserId,String replyContent,boolean pass);

	/**
	 * 我的审批
	 * 
	 * @param taskId
	 * @param map
	 */
	List<Task> taskAssignee(String assignee, int pageNo, int pageSize);

	/**
	 * 列出所有的流程。
	 * 
	 * @return
	 */
	List<ProcessDefinition> listAllProcessDefinitions();
}
