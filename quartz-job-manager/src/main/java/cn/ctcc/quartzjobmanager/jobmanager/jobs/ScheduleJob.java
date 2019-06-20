package cn.ctcc.quartzjobmanager.jobmanager.jobs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * @Author: zk
 * @Date: 2019/6/17 16:06
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {


    /**
     * 通过JobDataMap传入
     */
    private String beanName;

    /**
     * 通过JobDataMap传入
     */
    private String method;

    /**
     * 通过JobDataMap传入
     */
    private String params;

    /**
     * ApplicationContext,会自动放入SchedulerContext
     */
    private ApplicationContext applicationContext;

    /**
     * execute会调用executeInternal
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("任务名:"+beanName+"开始执行-------------------------------------------------------");
        Object target = applicationContext.getBean(beanName);
        try {
            Method method=null;
            if(StringUtils.isNotBlank(params)) {
                method = target.getClass().getDeclaredMethod(this.method, String[].class);
                //切割参数
                method.invoke(target,params.split(","));
            }else{
                method=target.getClass().getDeclaredMethod(this.method);
                method.invoke(target);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("任务名:"+beanName+"执行发生异常，执行失败-------------------------------------------------------");
            return;
        }
        log.info("任务名:"+beanName+"执行完毕-------------------------------------------------------");
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
