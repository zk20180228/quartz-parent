package cn.ctcc.quartz.strat02.p03;

import org.quartz.*;

/**
 * @Author: zk
 * @Date: 2019/6/14 15:19
 * @Description:  先实例化job,然后调用JobDataMap中与之相关的set属性
 * @Modified:
 * @version: V1.0
 */
public class HelloJob03 implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //具体的业务逻辑
        System.out.println("开始生成任务报表 或 开始发送邮件....");
        JobKey key = context.getJobDetail().getKey();
        System.out.println("key--->"+key);
        //打印jobDetail 的name
        System.out.println("jobDetail 的name ： "+key.getName());
        //打印jobDetail 的group
        System.out.println("jobDetail 的group ： "+key.getGroup());

        JobDataMap jobDetailDataMap = context.getJobDetail().getJobDataMap();
        String message = jobDetailDataMap.getString("message");
        float floatJobValue = jobDetailDataMap.getFloat("FloatJobValue");
        //打印jobDataMap定义的message的值
        System.out.println("jobDataMap定义的message的值 : "+message );
        //jobDataMap定义的floatJobValue的值
        System.out.println("jobDataMap定义的floatJobValue的值 : "+floatJobValue );

        /**
         * 开始生成任务报表 或 开始发送邮件....
         * key--->group1.myJob
         * jobDetail 的name ： myJob
         * jobDetail 的group ： group1
         * jobDataMap定义的message的值 : hello myJob1
         * jobDataMap定义的floatJobValue的值 : 8.88
         */

    }
}
