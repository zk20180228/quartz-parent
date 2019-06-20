package cn.ctcc.quartz.strat02.p01;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author: zk
 * @Date: 2019/6/14 15:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloScheduler01 {

    public static void main(String[] args)throws Exception {

        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob01.class).withIdentity("myJob").build();
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        scheduler.scheduleJob(jobDetail,trigger);

        //调度不属于守护线程，main方法结束，调度还在执行

    }
}
