package cn.ctcc.quartzjobmanager.config.quartz;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author: zk
 * @Date: 2019/6/17 15:39
 * @Description:
 *          SpringBoot2.x会自动加载application.properties中关于SchedulerFactoryBean的配置，生成SchedulerFactoryBean对象，放入IOC容器中
 *          SchedulerFactoryBeanCustomizer可以实现对IOC容器中的SchedulerFactoryBean进行再定制化的配置
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class SchedulerFactoryBeanConfig implements SchedulerFactoryBeanCustomizer {


//    @Resource
//    private DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {

//        schedulerFactoryBean.setDataSource(dataSource);

        // 延时启动
        schedulerFactoryBean.setStartupDelay(2);
        // 设置自动启动，默认为true
        schedulerFactoryBean.setAutoStartup(true);
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        schedulerFactoryBean.setOverwriteExistingJobs(true);

        /**
         * 设置ApplicationContext引用的键以在SchedulerContext中公开，例如“applicationContext”。默认为none。仅适用于在Spring ApplicationContext中运行时。
         *对于QuartzJobBean，引用将作为bean属性应用于Job实例。 “applicationContext”属性将对应于该场景中的“setApplicationContext”方法。
         */
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");

    }
}
