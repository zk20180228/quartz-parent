package cn.ctcc.quartz.strat02.p04;

import org.quartz.*;

import java.text.SimpleDateFormat;

/**
 * @Author: zk
 * @Date: 2019/6/14 16:07
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloJob04 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        //打印当前的执行时间 例如 2017-11-22 00:00:00
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //具体的业务逻辑
        System.out.println("具体执行的业务...");
        JobKey key = context.getJobDetail().getKey();
        Trigger trigger = context.getTrigger();
        //打印开始时间
        System.out.println("开始的时间： "+sf.format(trigger.getStartTime()));
        //打印结束时间
        System.out.println("结束的事件： "+sf.format(trigger.getEndTime()));

    }
}
