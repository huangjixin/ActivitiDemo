package com.zhiyesoft.activiti.demo.config;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.zhiyesoft.activiti.demo.service.impl.ActivityConsumerServiceImpl;

@Configuration
public class DataConfig {

	@Autowired
	private RepositoryService repositoryService;// ：提供一系列管理流程部署和流程定义的API。
	@Autowired
	private RuntimeService runtimeService;// ：在流程运行时对流程实例进行管理与控制。
	@Autowired
	private TaskService taskService;// ：对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
	@Autowired
	private IdentityService identityService;// ：提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。
	@Autowired
	private ManagementService managementService;// ：提供对流程引擎进行管理和维护的服务。
	@Autowired
	private HistoryService historyService;// ：对流程的历史数据进行操作，包括查询、删除这些历史数据。
	@Autowired
	private FormService formService;

	private static Logger logger = LoggerFactory.getLogger(DataConfig.class);
	
	@PostConstruct
	public void postConstruct() {
		logger.info("初始化数据开始");
		initActiviUserAndGroup();
		logger.info("初始化数据结束");
	}

	private void initActiviUserAndGroup() {
		creatGroup("employee", "员工组", "employee", UUID.randomUUID().toString().replaceAll("-", ""), "张志成", "123456");
		creatGroup("manager", "经理组", "employee", UUID.randomUUID().toString().replaceAll("-", ""), "黄记新", "123456");
		creatGroup("boss", "老板组", "boss", UUID.randomUUID().toString().replaceAll("-", ""), "蔡伟鸿", "123456");
	}

	private void creatGroup(String groupId, String groupName, String groupType, String userId, String userName,
			String password) {
		try {
			Group group = identityService.newGroup(groupId);
			group.setName(groupName);
			group.setType(groupType);
			identityService.saveGroup(group);
			
			User user = identityService.newUser(userId);
			user.setLastName(userName);
			user.setPassword(password);
			identityService.saveUser(user);
			
			// 绑定关系
			identityService.createMembership(user.getId(), group.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		

	}
}
