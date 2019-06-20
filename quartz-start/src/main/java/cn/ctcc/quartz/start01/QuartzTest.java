package cn.ctcc.quartz.start01;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;


/**
 * @Author: zk
 * @Date: 2019/6/13 14:26
 * @Description: quartz学习
 * @Modified:
 * @version: V1.0
 *
 * 1.Job 表示一个工作，要执行的具体内容。此接口中只有一个方法，如下：
 *
 * void execute(JobExecutionContext context)
 *
 * 2.JobDetail 表示一个具体的可执行的调度程序，Job 是这个可执行程调度程序所要执行的内容，另外 JobDetail 还包含了这个任务调度的方案和策略。
 * 3.Trigger 代表一个调度参数的配置，什么时候去调。
 * 4.Scheduler 代表一个调度容器，一个调度容器中可以注册多个 JobDetail 和 Trigger。当 Trigger 与 JobDetail 组合，就可以被 Scheduler 容器调度了。
 */
public class QuartzTest {


    @Test
    public void test01() throws Exception{

        //创建一个调度容器,需要quartz.properties的支持
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //执行调度容器中的job
        scheduler.start();

        //调度逻辑。。。。。。。



        Thread.sleep(5000);

        //关闭一个调度容器,在调用scheduler.shutdown()之前，scheduler不会终止，因为还有活跃的线程在执行。
        scheduler.shutdown();

    }

    @Test
    public void test02() throws Exception{

        //创建一个调度容器,需要quartz.properties的支持,getDefaultScheduler等价于new StdSchedulerFactory().getScheduler()
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //执行调度容器中的job
        scheduler.start();

        // define the jobs and tie it to our HelloJob class
        JobDetail job = JobBuilder.newJob(HelloJob01.class)
                                     ////任务名，和任务组
                                    .withIdentity("job1","group1")
                                    .build();

        // Trigger the jobs to run now, and then repeat every 10 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        // Tell quartz to schedule the jobs using our trigger
        scheduler.scheduleJob(job,trigger);

        //先执行5次
        Thread.sleep(5000);
        //暂停
        scheduler.standby();
        //暂停5秒
        Thread.sleep(5000);
        //重新开始执行
        scheduler.start();

        //关闭一个调度容器,在调用scheduler.shutdown()之前，scheduler不会终止，因为还有活跃的线程在执行。
        //scheduler.shutdown();

        //防止主线程退出，调度失败
        System.in.read();
    }


    /**
     *Quartz API的关键接口是：
     *     Scheduler 调度的容器，包含调度的具体API。
     *     Job - 作业动作。
     *     JobDetail - 作业实例。
     *     Trigger（即触发器） 。
     *     JobBuilder - 作业动作构建器。
     *     TriggerBuilder - 触发器构建器。
     *
     * Scheduler被创建后，可以增加、删除和列举Job和Trigger，以及执行其它与调度相关的操作（如暂停Trigger）。
     * 但是，Scheduler只有在调用start()方法后，才会真正地触发trigger（即执行job）
     *
     * 领域特定语言（DSL）：级联的API
     *
     * Job接口
     * public interface Job {
     *
     *     public void execute(JobExecutionContext context)
     *       throws JobExecutionException;
     *   }
     * 当Job的一个trigger被触发（稍后会讲到）时，execute（）方法由调度程序的一个工作线程调用。
     *  execute()方法的JobExecutionContext对象中保存着该job运行时的一些信息 ，执行job的scheduler的引用，
     * 触发job的trigger的引用，JobDetail对象引用，以及一些其它信息。
     *
     * JobDetail对象是在将job加入scheduler时，由客户端程序（你的程序）创建的。它包含job的各种属性设置，以及用于存储job实例状态信息的JobDataMap。
     *
     *Trigger用于触发Job的执行。Trigger也有一个相关联的JobDataMap，用于给Job传递一些触发相关的参数。
     *Quartz自带了各种不同类型的Trigger，最常用的主要是SimpleTrigger和CronTrigger。
     *SimpleTrigger主要用于一次性执行的Job（只在某个特定的时间点执行一次），或者Job在特定的时间点执行，重复执行N次，每次执行间隔T个时间单位。
     *CronTrigger在基于日历的调度上非常有用，如“每个星期五的正午”，或者“每月的第十天的上午10:15”等。
     *
     *Key:
     *将Job和Trigger注册到Scheduler时，可以为它们设置key，配置其身份属性。Job和Trigger的key（JobKey和TriggerKey）可以用于将Job和Trigger放到不同的分组（group）里，
     *然后基于分组进行操作。同一个分组下的Job或Trigger的名称必须唯一，即一个Job或Trigger的key由名称（name）和分组（group）组成
     *
     *每次当scheduler执行job时，在调用其execute(…)方法之前会创建该类的一个新的实例；执行完毕，
     * 对该实例的引用就被丢弃了，实例会被垃圾回收；这种执行策略带来的一个后果是，
     * job必须有一个无参的构造函数（当使用默认的JobFactory时）；
     * 另一个后果是，在job类中，不应该定义有状态的数据属性，因为在job的多次执行中，这些属性的值不会保留。
     * 那么如何给job实例增加属性或配置呢？如何在job的多次执行中，跟踪job的状态呢？
     * 答案就是:JobDataMap，JobDetail对象的一部分。
     * JobDataMap中可以包含不限量的（序列化的）数据对象，在job实例执行的时候，可以使用其中的数据；
     * JobDataMap是Java Map接口的一个实现，额外增加了一些便于存取基本类型的数据的方法。
     *
     * 如果你在job类中，为JobDataMap中存储的数据的key增加set方法（如在上面示例中，增加setJobSays(String val)方法），
     * 那么Quartz的默认JobFactory实现在job被实例化的时候会自动调用这些set方法，这样你就不需要在execute()方法中显式地从map中取数据了。
     * 在Job执行时，JobExecutionContext中的JobDataMap为我们提供了很多的便利。它是JobDetail中的JobDataMap和Trigger中的JobDataMap的并集，
     * 但是如果存在相同的数据，则后者会覆盖前者的值。
     *
     *
     */
    @Test
    public void test03() throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob02.class)
                .withIdentity("h2", "g2")
                .usingJobData("JJ", "18")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t2", "g2")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail,trigger);


        System.in.read();
    }


    /**
     * 在Quartz的描述语言中，我们将保存后的JobDetail称为“job定义”或者“JobDetail实例”,
     * 将一个正在执行的job称为“job实例”或者“job定义的实例”。当我们使用“jobs”时，一般指代的是job定义，
     * 或者JobDetail；当我们提到实现Job接口的类时，通常使用“job类”。
     *
     *
     *job的状态数据（即JobDataMap）和并发性
     *@DisallowConcurrentExecution：将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义的多个实例(JobDetail)
     *@DisallowConcurrentExecution注解，因为当同一个job（JobDetail）的两个实例被并发执行时，由于竞争，JobDataMap中存储的数据很可能是不确定的。
     *
     *@PersistJobDataAfterExecution：将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），
     *更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据。
     *
     * 通过JobDetail对象，可以给job实例配置的其它属性有：
     *
     *  Durability：如果一个job是非持久的，当没有活跃的trigger与之关联的时候，会被自动地从scheduler中删除。
     *  也就是说，非持久的job的生命期是由trigger的存在与否决定的；
     *  RequestsRecovery：如果一个job是可恢复的，并且在其执行的时候，scheduler发生硬关闭（hard shutdown)（比如运行的进程崩溃了，或者关机了），
     *  则当scheduler重新启动的时候，该job会被重新执行。此时，该job的JobExecutionContext.isRecovering() 返回true。
     *
     */
    @Test
    public void test04() throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob03.class)
                .withIdentity("h2", "g2")
                .usingJobData("jj", "18")
                //.storeDurably()//默认true
                //.requestRecovery()//默认true
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t2", "g2")
                .usingJobData("jj","21")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail,trigger);


        System.in.read();
    }


    /**
     *SimpleTrigger Misfire策略:错过触发的策略
     * 在使用SimpleTrigger构造trigger时，misfire策略作为基本调度(simple schedule)的一部分进行配置(通过SimpleSchedulerBuilder设置)：
     */
    @Test
    public void test05()throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob03.class)
                .withIdentity("h3", "g3")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t2", "g2")
                .usingJobData("jj","21")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                //优先级(priority),只有同时触发的trigger之间才会比较优先级。10:59触发的trigger总是在11:00触发的trigger之前执行。
                //.withPriority(10)
                //错过触发(misfire Instructions)
                //.forJob("h3")
                //.forJob(jobDetail.getKey())
                .build();

        scheduler.scheduleJob(jobDetail,trigger);


        System.in.read();

    }


    /**
     *SimpleTrigger实例通过TriggerBuilder设置主要的属性，
     *通过SimpleScheduleBuilder设置与SimpleTrigger相关的属性。
     *
     *
     *
     */
    @Test
    public void test06()throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob03.class)
                .withIdentity("h3", "g3")
                .build();

        //指定时间开始触发，不重复，只执行一次
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("t2", "g2")
//                .startAt(new Date())
//                .usingJobData("jj","21")
//                .forJob("h3", "g3")
//                .build();
        //指定时间触发，每隔10秒执行一次，重复10次
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("t2", "g2")
//                .startAt(new Date())
//                .usingJobData("jj","21")
//                .forJob("h3", "g3")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(10))
//                .build();

        //3秒以后开始触发，仅执行一次：
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("t2", "g2")
//                .startAt(DateBuilder.futureDate(3, DateBuilder.IntervalUnit.SECOND))
//                .usingJobData("jj","21")
//                .forJob("h3", "g3")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(10))
//                .build();
        //立即触发，每个1秒执行一次，直到22:00
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("t2", "g2")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
//                .endAt(DateBuilder.dateOf(22,0,0))
//                .usingJobData("jj","21")
//                .forJob("h3", "g3")
//                .build();

        //建立一个触发器，将在下一个小时的整点触发，然后每2小时重复一次
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger8") // because group is not specified, "trigger8" will be in the default group
                .startAt(DateBuilder.evenHourDate(null)) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(2)
                        .repeatForever())
                // note that in this example, 'forJob(..)' is not called which is valid
                // if the trigger is passed to the scheduler along with the jobs
                .build();

        scheduler.scheduleJob(jobDetail,trigger);


        System.in.read();

    }


    /**
     * CronTrigger
     *
     * Cron-Expressions:秒 分 时 日 月 周 年  注意一个cron表达式至少包含六个
     *
     * CronTrigger示例1 - 创建一个触发器的表达式，每5秒就会触发一次
     *
     * “0/5 * * * * ?”
     *
     * CronTrigger示例2 - 创建触发器的表达式，每5分钟触发一次，分钟后10秒（即上午10时10分，上午10:05:10等）。
     *
     * “10 0/5 * * * ?”
     *
     * CronTrigger示例3 - 在每个星期三和星期五的10:30，11:30，12:30和13:30创建触发器的表达式。
     *
     * “0 30 10-13 ? * WED，FRI“
     *
     * CronTrigger示例4 - 创建触发器的表达式，每个月5日和20日上午8点至10点之间每半小时触发一次。请注意，触发器将不会在上午10点开始，仅在8:00，8:30，9:00和9:30
     *
     * “0 0/30 8-9 5,20 * ?”
     */
    @Test
    public void test07()throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob03.class)
                .withIdentity("j3", "g3")
                .usingJobData("jj", "21")
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t3", "g3")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?").withMisfireHandlingInstructionFireAndProceed())
                .build();

        scheduler.scheduleJob(jobDetail,trigger);

        System.in.read();
    }


    /**
     * Listeners是您创建的对象，用于根据调度程序中发生的事件执行操作
     * 您可能猜到，TriggerListeners接收到与触发器（trigger）相关的事件，JobListeners 接收与jobs相关的事件。
     */
    @Test
    public void test08()throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(HelloJob03.class)
                .withIdentity("j3", "g3")
                .usingJobData("jj", "21")
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t3", "g3")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?").withMisfireHandlingInstructionFireAndProceed())
                .build();

        //scheduler.getListenerManager().addJobListener(new MyJobListener(), KeyMatcher.keyEquals(new JobKey("j3","g3")));
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(), KeyMatcher.keyEquals(new TriggerKey("t3","g3")));
        //添加对特定组的所有job感兴趣的JobListener：
        //scheduler.getListenerManager().addJobListener(myJobListener, jobGroupEquals("myJobGroup"));
        //添加对两个特定组的所有job感兴趣的JobListener：
        //scheduler.getListenerManager().addJobListener(myJobListener, or(jobGroupEquals("myJobGroup"), jobGroupEquals("yourGroup")));
        //添加对所有job感兴趣的JobListener：
        //scheduler.getListenerManager().addJobListener(myJobListener, allJobs());
        //添加SchedulerListener
        //scheduler.getListenerManager().addSchedulerListener(mySchedListener);
        //删除SchedulerListener：
        //scheduler.getListenerManager().removeSchedulerListener(mySchedListener);

        scheduler.scheduleJob(jobDetail,trigger);

        System.in.read();
    }


    /**
     *StdSchedulerFactory
     *
     * StdSchedulerFactory是org.quartz.SchedulerFactory接口的一个实现。它使用一组属性（java.util.Properties）来创建和初始化Quartz Scheduler。
     * 属性通常存储在文件中并从文件中加载，但也可以由程序创建并直接传递到工厂。
     * 简单地调用工厂中的getScheduler（）将生成调度程序，并初始化它（和它的ThreadPool，JobStore和DataSources）并返回一个句柄到它的公共接口。
     * 在Quartz发行版的“docs / config”目录中有一些示例配置（包括属性的描述）。您可以在Quartz文档的“参考”部分的“配置”手册中找到完整的文档。
     * DirectSchedulerFactory
     *
     * DirectSchedulerFactory是另一个SchedulerFactory实现。对于希望以更加程序化的方式创建其Scheduler实例的用户是有用的。
     * 通常不鼓励使用它的用法，原因如下：（1）要求用户更好地了解他们正在做什么，（2）它不允许声明性配置 - 换句话说，你最终会硬 - 编辑所有调度程序的设置。
     *
     * Scheduler拥有一个SchedulerContext，它类似于ServletContext，保存着Scheduler上下文信息，Job和Trigger都可以访问SchedulerContext内的信息。
     * SchedulerContext内部通过一个Map，以键值对的方式维护这些上下文数据，SchedulerContext为保存和获取数据提供了多个put()和getXxx()的方法。
     * 可以通过Scheduler# getContext()获取对应的SchedulerContext实例；
     * ThreadPool：Scheduler使用一个线程池作为任务运行的基础设施，任务通过共享线程池中的线程提高运行效率。
     *
     *
     * Clustering:集群模式运行quartz
     *
     *
     */
    public void test09(){


        /**
         * CalendarIntervalScheduleBuilder
         *      .calendarIntervalSchedule()
         *      .withIntervalInDays(1)  //每天执行一次
         *    //.withIntervalInHours(1)
         *    //.withIntervalInMinutes(1)
         *    //.withIntervalInMonths(1)
         *    //.withIntervalInSeconds(1)
         *    //.withIntervalInWeeks(1)
         *    //.withIntervalInHours(1)
         *      .build()
         */

        /**
         * DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
         *                                 .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(8, 0)) //每天8：00开始
         *                                 .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(17, 0)) //17：00 结束
         *                                 .onDaysOfTheWeek(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) //周一至周五执行
         *                                 .withIntervalInHours(1) //每间隔1小时执行一次
         *                                 .withRepeatCount(100) //最多重复100次（实际执行100+1次）
         */


    }



}
