package cn.ctcc.quartzjobmanager.jobmanager;

import cn.ctcc.quartzjobmanager.jobmanager.jobs.ScheduleJob;
import cn.ctcc.quartzjobmanager.task.entity.Task;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zk
 * @Date: 2019/6/17 16:44
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class ScheduleUtils {


    private static final Logger log = LoggerFactory.getLogger(ScheduleUtils.class);

    /**
     * @Author: zk
     * @Date: 2019/6/17 16:46
     * @Param taskName 任务名称
     *        groupName 组名
     *        methodName 方法名
     * @Return:
     * @Throws
     * @Description: 根据任务名，任务组，方法创建一个TriggerKey
     */
    public static TriggerKey getTriggerKey(Task task) {

        return TriggerKey.triggerKey(task.getJobClassName()+"-"+task.getJobClassMethod(),task.getJobGroup());
    }

    /**
     * @Author: zk
     * @Date: 2019/6/17 16:46
     * @Param taskName 任务名称
     *        groupName 组名
     *        methodName 方法名
     * @Return:
     * @Throws
     * @Description: 根据任务名，任务组，方法创建一个JobKey
     */
    public static JobKey getJobKey(Task task) {

        return JobKey.jobKey(task.getJobClassName()+"-"+task.getJobClassMethod(),task.getJobGroup());
    }

    /**
     * @Author: zk
     * @Date: 2019/6/17 16:46
     * @Param taskName 任务名称
     *        groupName 组名
     *        methodName 方法名
     * @Return:
     * @Throws
     * @Description: 根据任务名，任务组，方法获取Scheduler中的CronTrigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, TriggerKey triggerKey) {
        try {
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            log.error("getCronTrigger 异常：", e);
        }

        return null;
    }

    /**
     * @Author: zk
     * @Date: 2019/6/17 17:21
     * @Param
     * @Return:
     * @Throws
     * @Description:  添加任务
     */
    public static boolean addTask(Scheduler scheduler, Task task) {
        try {
            if (checkExists(scheduler,task)) {
                new RuntimeException(task.getJobClassName()+"-"+task.getJobClassMethod()+"."+task.getJobGroup()+":任务已存在-------------------------------");
            }
            JobKey jobKey = getJobKey(task);
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(jobKey).build();

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getJobCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(task, cronScheduleBuilder);

            TriggerKey triggerKey = getTriggerKey(task);
            // 按新的cronExpression表达式构建一个新的trigger
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronScheduleBuilder);
            if(task.getJobStartTime()!=null){
                triggerBuilder=triggerBuilder.startAt(task.getJobStartTime());
            }
            if(task.getJobEndTime()!=null){
                triggerBuilder=triggerBuilder.endAt(task.getJobEndTime());
            }

            CronTrigger trigger = triggerBuilder.build();
            // 放入参数
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put("beanName",task.getJobClassName());
            jobDataMap.put("method",task.getJobClassMethod());
            jobDataMap.put("params",task.getJobParams()==null?"":task.getJobParams());

            scheduler.scheduleJob(jobDetail, trigger);
            // 暂停任务
            if (task.getJobStatus()==0) {
                pauseTask(scheduler, task);
            }

            return  true;
        } catch (Exception e) {
            log.error("addTask 异常：", e);
        }

        return false;
    }

    /**
     * 更新定时任务
     */
    public static boolean updateTask(Scheduler scheduler, Task task) {
        try {

            if (checkExists(scheduler,task)) {
                new RuntimeException(task.getJobClassName()+"-"+task.getJobClassMethod()+"."+task.getJobGroup()+":任务不存在-------------------------------");
            }

            TriggerKey triggerKey = getTriggerKey(task);

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getJobCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(task, cronScheduleBuilder);

            // 按新的cronExpression表达式构建一个新的trigger
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronScheduleBuilder);
            if(task.getJobStartTime()!=null){
                triggerBuilder=triggerBuilder.startAt(task.getJobStartTime());
            }
            if(task.getJobEndTime()!=null){
                triggerBuilder=triggerBuilder.endAt(task.getJobEndTime());
            }

            CronTrigger trigger = triggerBuilder.build();

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (task.getJobStatus()==0) {
                pauseTask(scheduler, task);
            }else{
                resumeTask(scheduler, task);
            }

            return true;
        } catch (Exception e) {
            log.error("updateTask 异常：", e);
        }

        return false;
    }


    /**
     * 暂停任务
     */
    public static boolean pauseTask(Scheduler scheduler, Task task) {
        try {
            if(checkExists(scheduler,task)) {
                scheduler.pauseJob(getJobKey(task));
            }
            return true;
        } catch (SchedulerException e) {
            log.error("pauseJob 异常：", e);
        }

        return false;
    }

    /**
     * 恢复任务
     */
    public static boolean resumeTask(Scheduler scheduler, Task task) {
        try {
            if(checkExists(scheduler,task)){
                 scheduler.resumeJob(getJobKey(task));
            }
            return true;
        } catch (SchedulerException e) {
            log.error("resumeJob 异常：", e);
        }

        return false;
    }

    /**
     * 删除定时任务
     */
    public static boolean deleteTask(Scheduler scheduler, Task task) {
        try {
            if(checkExists(scheduler,task)){
                scheduler.deleteJob(getJobKey(task));
            }
            return true;
        } catch (SchedulerException e) {
            log.error("deleteScheduleJob 异常：", e);
        }

        return false;
    }



    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(Task task, CronScheduleBuilder cb){
        switch (task.getJobMisfirePolicy()) {
            case 1:
                //所有misfire的任务会马上执行,打个比方，如果9点misfire了，在10：15系统恢复之后，9点，10点的misfire会马上执行
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case 2:
                //默认值:会合并部分的misfire,正常执行下一个周期的任务,假设9，10的任务都misfire了，系统在10：15分起来了。只会执行一次misfire，下次正点执行。
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case 3:
                //所有的misfire不管，执行下一个周期的任务
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                //默认好像是上边三种情况自动调节
                return cb;
        }
    }

    /**
     * 验证是否存在
     *
     * @param scheduler
     * @param task
     * @throws
     */
    private static boolean checkExists(Scheduler scheduler,Task task) throws SchedulerException {

        TriggerKey triggerKey =getTriggerKey(task);
        return scheduler.checkExists(triggerKey);
    }


}
