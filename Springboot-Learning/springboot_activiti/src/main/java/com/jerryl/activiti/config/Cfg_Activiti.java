package com.jerryl.activiti.config;

import com.jerryl.util.IdGen;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuruijie on 2017/2/20.
 * activiti工作流配置
 */
@Configuration
public class Cfg_Activiti {

    //流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager){
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        //是否自动部署数据库表 false
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setDatabaseType("mysql");

        processEngineConfiguration.setTransactionManager(transactionManager);
//        // 自定义用户权限
//        List<SessionFactory> customSessionFactories= Arrays.asList(
//                new ActUserEntityServiceFactory(),
//                new ActGroupEntityServiceFactory()
//                );
//        processEngineConfiguration.setCustomSessionFactories(customSessionFactories);
        //UUID作为主键生成策略
        processEngineConfiguration.setIdGenerator(new IdGen());
        //流程图字体
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");

        return processEngineConfiguration;
    }

    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    //八大接口
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine){
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine){
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine){
        return processEngine.getDynamicBpmnService();
    }

    //八大接口 end
}
