package cn.ctcc.quartzjobmanager.jobmanager.jobs;

import org.springframework.stereotype.Component;

/**
 * @Author: zk
 * @Date: 2019/6/18 14:49
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component("testJob")
public class TestJob  {

    public void test01(){

        System.out.println("-----------test01-------------------------------------");
    }

}
