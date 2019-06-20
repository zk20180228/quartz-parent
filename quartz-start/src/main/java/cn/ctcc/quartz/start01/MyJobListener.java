package cn.ctcc.quartz.start01;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @Author: zk
 * @Date: 2019/6/14 10:54
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class MyJobListener implements JobListener {


    @Override
    public String getName() {

        return "MyJobListener";
    }

    /**
     * trigger被触发，job即将被执行
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("trigger被触发，job即将被执行..............");
    }

    /**
     * trigger被触发，job即将被执行，但是scheduler终止了trigger
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("trigger被触发，job即将被执行，但是scheduler终止了trigger...............");
    }

    /**
     * trigger被触发，job执行完毕,没执行完毕时不会触发该方法
     * @param context
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        System.out.println("trigger被触发，job执行完毕.................");
    }
}
