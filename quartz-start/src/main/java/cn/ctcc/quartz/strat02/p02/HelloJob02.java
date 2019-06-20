package cn.ctcc.quartz.strat02.p02;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zk
 * @Date: 2019/6/14 15:26
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloJob02 implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //打印当前的执行时间 例如 2017-11-23 00:00:00
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("现在的时间是："+ sf.format(date));
        //具体的业务逻辑
        System.out.println("开始生成任务报表 或 开始发送邮件");
    }
}
