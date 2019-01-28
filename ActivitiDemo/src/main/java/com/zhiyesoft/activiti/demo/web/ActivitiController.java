package com.zhiyesoft.activiti.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhiyesoft.activiti.demo.core.vo.BusinessType;
import com.zhiyesoft.activiti.demo.core.vo.Response;
import com.zhiyesoft.activiti.demo.core.vo.VacationForm;
import com.zhiyesoft.activiti.demo.core.vo.VacationType;
import com.zhiyesoft.activiti.demo.service.ActivityConsumerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("activiti")
public class ActivitiController {
	@Autowired
	private ActivityConsumerService activityConsumerService;

	@Autowired
	private HistoryService historyService;
	
	@ApiOperation(value = "流程部署", notes = "流程部署")
	@GetMapping(value = "deploy")
	@ResponseBody
	public Response deploy() {
		Response response = new Response();
		activityConsumerService.deploy();
//		int result = voteService.deleteByPrimaryKey(id);
//		response.setData(result);
		return response;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "启动流程", notes = "启动流程")
	@PostMapping(value = "startProcessInstanceByKey")
	@ResponseBody
	public Response startProcessInstanceByKey(
			@RequestParam(defaultValue = "10915bee4b814c018cdaad8bb9f68a1c") String userId,
			@RequestParam(defaultValue = "leave") String Key, @ModelAttribute VacationForm vacationForm) {
		Response response = new Response();
//		VacationForm vacationForm = new VacationForm();
//		vacationForm = VacationForm.builder().
		vacationForm.setUserName("张志成");
		vacationForm.setUserId(userId);
		vacationForm.setStartDate("2019-01-25 00:00:00");
		vacationForm.setEndDate("2019-01-26 00:00:00");
		vacationForm.setBusinessType(BusinessType.VACATION);
		vacationForm.setDays("1");
		vacationForm.setHours("24");
		vacationForm.setVacationType(VacationType.TYPE_SICK);
		vacationForm.setReason("感冒生病");
		activityConsumerService.startProcessInstanceByKey(Key, vacationForm);
//		int result = voteService.deleteByPrimaryKey(id);
//		response.setData(result);
		return response;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "执行任务节点", notes = "完成任务节点")
	@PostMapping(value = "executeTask")
	@ResponseBody
	public Response executeTask(@RequestParam String taskId, @RequestParam String replyContent,
			@RequestParam String handlerUserId, 
			@RequestParam boolean pass) {
		Response response = new Response();
		// 此处写死处理人ID
//		String handlerUserId = "f841cd8f7a3f4fbc84783b0578c3304e";
		activityConsumerService.executeTask(taskId, handlerUserId, replyContent, pass);
		return response;
	}

	@ApiOperation(value = "待审批任务节点", notes = "待审批任务节点")
	@GetMapping(value = "taskAssignee")
	@ResponseBody
	public Response taskAssignee(@RequestParam String assignee, @RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		Response response = new Response();
		List<Task> list = activityConsumerService.taskAssignee(assignee, pageNo, pageSize);

		for (Task task : list) {
			task.getId();
		}
		response.setData(list);
		return response;
	}
	
	@ApiOperation(value = "历史流程实例", notes = "待审批任务节点")
	@GetMapping(value = "hisProcessInstances")
	@ResponseBody
	public Response hisTasks(@RequestParam String userId, @RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		Response response = new Response();
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).list();
		
		List<HistoricTaskInstanceEntityImpl>processInstanceEntityImpls = new ArrayList<HistoricTaskInstanceEntityImpl>();
		for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
			HistoricProcessInstanceEntityImpl entityImpl = new HistoricProcessInstanceEntityImpl();
			entityImpl.setName(historicTaskInstance.getName());
		}
		/*List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list();
//		historyService.createNativeHistoricVariableInstanceQuery().;
		List<HistoricTaskInstanceEntityImpl>results = new ArrayList<HistoricTaskInstanceEntityImpl>();
		for (HistoricTaskInstance historicTaskInstance : list) {
			
			HistoricTaskInstanceEntityImpl entityImpl = new HistoricTaskInstanceEntityImpl();
			entityImpl.setAssignee(historicTaskInstance.getAssignee());
//			entityImpl.set;
			results.add(entityImpl);
		}*/
		
		response.setData(processInstanceEntityImpls);
		return response;
	}

	@ApiOperation(value = "列出所有流程实例", notes = "列出所有流程实例")
	@GetMapping(value = "listAllProcessDefinitions")
	@ResponseBody
	public Response listAllProcessDefinitions() {
		Response response = new Response();
		List<ProcessDefinition> list = activityConsumerService.listAllProcessDefinitions();
		response.setData(list);
		return response;
	}
}
