package cn.ctcc.quartz.start01;

import org.quartz.*;

/**
 * @Author: zk
 * @Date: 2019/6/13 15:46
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloJob02 implements Job {


    /**
     * @param context
     * @throws JobExecutionException
     *
     *JobDataMap中可以包含不限量的（序列化的）数据对象，在job实例执行的时候，可以使用其中的数据；
     *JobDataMap是Java Map接口的一个实现，额外增加了一些便于存取基本类型的数据的方法。
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey key = context.getJobDetail().getKey();

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        String jj = jobDataMap.getString("JJ");

        //Instance g2.h2 of DumbJob says: 18
        System.err.println("Instance " + key + " of DumbJob says: " + jj );
    }
}
