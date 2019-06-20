package cn.ctcc.quartz.start01;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: zk
 * @Date: 2019/6/13 14:36
 * @Description: 自定义作业
 * @Modified:
 * @version: V1.0
 *
 *每次当scheduler执行job时，在调用其execute(…)方法之前会创建该类的一个新的实例；执行完毕，
 * 对该实例的引用就被丢弃了，实例会被垃圾回收；这种执行策略带来的一个后果是，
 * job必须有一个无参的构造函数（当使用默认的JobFactory时）；
 * 另一个后果是，在job类中，不应该定义有状态的数据属性，因为在job的多次执行中，这些属性的值不会保留。
 *
 * 那么如何给job实例增加属性或配置呢？如何在job的多次执行中，跟踪job的状态呢？
 * 答案就是:JobDataMap，JobDetail对象的一部分。
 * Job有一个StatefulJob子接口，代表有状态的任务，该接口是一个没有方法的标签接口，其目的是让Quartz知道任务的类型，
 * 以便采用不同的执行方案。无状态任务在执行时拥有自己的JobDataMap拷贝，对JobDataMap的更改不会影响下次的执行。
 * 而有状态任务共享共享同一个JobDataMap实例，每次任务执行对JobDataMap所做的更改会保存下来，后面的执行可以看到这个更改，
 * 也即每次执行任务后都会对后面的执行发生影响。
 * 正因为这个原因，无状态的Job可以并发执行，而有状态的StatefulJob不能并发执行，这意味着如果前次的StatefulJob还没有执行完毕，
 * 下一次的任务将阻塞等待，直到前次任务执行完毕。有状态任务比无状态任务需要考虑更多的因素，程序往往拥有更高的复杂度，因此除非必要，应该尽量使用无状态的Job。
 * 如果Quartz使用了数据库持久化任务调度信息，无状态的JobDataMap仅会在Scheduler注册任务时保持一次，而有状态任务对应的JobDataMap在每次执行任务后都会进行保存。
 * Trigger自身也可以拥有一个JobDataMap，其关联的Job可以通过JobExecutionContext#getTrigger().getJobDataMap()获取Trigger中的JobDataMap。
 * 不管是有状态还是无状态的任务，在任务执行期间对Trigger的JobDataMap所做的更改都不会进行持久，也即不会对下次的执行产生影响。
 *
 */
public class HelloJob01 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello word!");
    }
}
