package cn.ctcc.quartz.start01;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @Author: zk
 * @Date: 2019/6/14 11:04
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class MyTriggerListener implements TriggerListener {


    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    /**
     * trigger被触发
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("trigger被触发-------------------------");
    }

    /**
     *返回true，能触发triggerFired但是job的execute方法不会执行
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {

        //返回true，能触发triggerFired但是job的execute方法不会执行
        return false;
    }

    /**
     * 错过触发
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

        System.out.println("错过触发---------------------------");
    }

    /**
     * trigger触发器触发完毕， 没触发完毕(vetoJobExecution返回true)不会执行
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("trigger触发器触发完毕------------------------------");
    }
}
