package cn.ctcc.quartz.start01;

import org.quartz.*;

/**
 * @Author: zk
 * @Date: 2019/6/13 15:46
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class HelloJob03 implements Job {


    private String jj;

    /**
     * @param context
     * @throws JobExecutionException
     *
     *JobDataMap中可以包含不限量的（序列化的）数据对象，在job实例执行的时候，可以使用其中的数据；
     *JobDataMap是Java Map接口的一个实现，额外增加了一些便于存取基本类型的数据的方法。
     *
     * 如果你在job类中，为JobDataMap中存储的数据的key增加set方法（如在上面示例中，增加setJobSays(String val)方法），
     * 那么Quartz的默认JobFactory实现在job被实例化的时候会自动调用这些set方法，这样你就不需要在execute()方法中显式地从map中取数据了。
     * 在Job执行时，JobExecutionContext中的JobDataMap为我们提供了很多的便利。它是JobDetail中的JobDataMap和Trigger中的JobDataMap的并集，
     * 但是如果存在相同的数据，则后者会覆盖前者的值。
     *
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

          JobKey key = context.getJobDetail().getKey();

//        JobDataMap jobDataMap = context.getMergedJobDataMap();
//        String jj = jobDataMap.getString("jj");

        //Instance g2.h2 of DumbJob says: 21cm
        System.err.println("Instance " + key + " of DumbJob says: " + jj +"cm");
    }


    public void setJj(String jj) {
        this.jj = jj;
    }
}
