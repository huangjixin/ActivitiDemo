package com.zhiyesoft.activiti.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyesoft.activiti.demo.core.vo.BaseForm;
import com.zhiyesoft.activiti.demo.service.ActivityConsumerService;

@Transactional
@Service("activityService")
public class ActivityConsumerServiceImpl implements ActivityConsumerService {

	private static Logger logger = LoggerFactory.getLogger(ActivityConsumerServiceImpl.class);

	private static final String BASE_MESSAGE = "ActivityConsumerServiceImpl流程操作";

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	IdentityService identityService;

	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
//	@Override
	public boolean startActivityDemo() {
//		System.out.println("method startActivityDemo begin....");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("apply", "zhangsan");
//		map.put("approve", "lisi");
////流程启动
//		ProcessInstance pe1 = runtimeService.startProcessInstanceByKey("leave", map);
//		
//		String processId = pi1.getId();
//		List<Task> tasks = taskService.createTaskQuery().processDefinitionId(pe1.getProcessDefinitionId())
//				.deploymentId(pe1.getDeploymentId()).list();
//		for(Task ta : tasks) {
//			   System.out.println(ta.getId());
//		}
//		System.out.println();
//		
//		
//		

//		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//		List<String>processIds = new ArrayList<String>();
//		processIds.add(processId);
//		taskService.createTaskQuery().processInstanceIdIn(processIds).singleResult();
//		String taskId2 = task.getId();
//		map.put("pass", false);
//		taskService.complete(taskId2, map);// 驳回申请
		System.out.println("method startActivityDemo end....");
		return false;
	}

	@Override
	public void deploy() {
		logger.info(BASE_MESSAGE + "部署流程开始");
		Long preCount = repositoryService.createProcessDefinitionQuery().count();
		repositoryService.createDeployment().addClasspathResource("processes/test.bpmn20.xml").deploy();
		Long currentCount = repositoryService.createProcessDefinitionQuery().count();
		if (currentCount > preCount)
			logger.info(BASE_MESSAGE + "部署流程成功");
		else
			logger.info(BASE_MESSAGE + "部署流程失败");
		logger.info(BASE_MESSAGE + "部署流程结束");
	}

	@Override
	public void startProcessInstanceByKey(String Key, BaseForm baseForm) {
		logger.info(BASE_MESSAGE + "开始启动一个流程实例");
		Map map = new HashMap();
		map.put("arg", baseForm);
		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(Key, map);
		// 查询全部的任务，得到相应的执行流，设置不同的参数
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		taskService.setOwner(task.getId(), baseForm.getUserId());
		if (task != null) {
			taskService.setAssignee(task.getId(), baseForm.getUserId());
			taskService.complete(task.getId(), map);
		}

		// 找到提交人的上级
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		if (task != null) {
			taskService.setAssignee(task.getId(),"f841cd8f7a3f4fbc84783b0578c3304e");
		}
//		if(tasks != null && !tasks.isEmpty()) {
//			Task task = tasks.get(0);
//			formService.saveFormData(task.getId(), map);
//			taskService.setOwner(task.getId(), "123");
//			executeTask(task.getId(),pi.getId(),"",new HashMap<>());
//		}
//		
//		
//		for (Task task : tasks) {
//			logger.info(task.getName());
//		}
		logger.info(BASE_MESSAGE + "结束启动一个流程实例");
	}

	@Override
	public void executeTask(String taskId,String handlerUserId,String replyContent,boolean pass) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), replyContent);
		Map map = new HashMap<>();
		map.put("pass", pass);
		
		if(pass) {
			taskService.complete(taskId,map);
			// 根据处理人ID，找到上级ID，再调用taskService.setAssignee(taskId, userId);
		}else {
			BaseForm baseForm = (BaseForm) taskService.getVariable(taskId, "arg");
			taskService.setVariable(taskId, "pass", pass);
			taskService.setAssignee(taskId,baseForm.getUserId());
			taskService.complete(taskId,map);
		}
//		
	}

	@Override
	public List<Task> taskAssignee(String assignee, int pageNo, int pageSize) {
		if (pageNo <= 1)
			pageNo = 1;
		if (pageSize <= 1)
			pageSize = 10;
		int start = (pageNo - 1) * pageSize;
		int limit = pageSize;
		logger.info(BASE_MESSAGE + "查询");
		List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).listPage(start, limit);
		return list;
	}

	@Override
	public List<ProcessDefinition> listAllProcessDefinitions() {
		List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().list();
		return definitions;
	}

}
