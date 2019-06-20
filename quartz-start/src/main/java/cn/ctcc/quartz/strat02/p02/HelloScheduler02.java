package cn.ctcc.quartz.strat02.p02;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author: zk
 * @Date: 2019/6/14 15:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloScheduler02 {

    public static void main(String[] args)throws Exception {
        //jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob02.class).withIdentity("cronJob").build();
        //cronTrigger
        //每日的9点40触发任务
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 40 9 * * ? ")).build();
        //1.每日10点15分触发      0 15 10 ？* *
        //2.每天下午的2点到2点59分（正点开始，隔5分触发）       0 0/5 14 * * ?
        //3.从周一到周五每天的上午10点15触发      0 15 10 ? MON-FRI
        //4.每月的第三周的星期五上午10点15触发     0 15 10 ? * 6#3
        //5.2016到2017年每月最后一周的星期五的10点15分触发   0 15 10 ? * 6L 2016-2017
        //Scheduler实例
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        //Scheduler拥有一个SchedulerContext，它类似于ServletContext，保存着Scheduler上下文信息，Job和Trigger都可以访问SchedulerContext内的信息。SchedulerContext内部通过一个Map，以键值对的方式维护这些上下文数据，SchedulerContext为保存和获取数据提供了多个put()和getXxx()的方法。
        // 可以通过Scheduler# getContext()获取对应的SchedulerContext实例；
        //SchedulerContext context = scheduler.getContext();
        //调度不属于守护线程，main方法结束，调度还在执行

    }
}
