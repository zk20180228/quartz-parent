package cn.ctcc.quartzjobmanager.task.controller;


import cn.ctcc.quartzjobmanager.task.entity.Task;
import cn.ctcc.quartzjobmanager.task.service.TaskService;
import cn.ctcc.quartzjobmanager.util.BackResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangkui
 * @since 2019-06-18
 */
@Controller
@RequestMapping("/task")
public class TaskController {


    @Resource
    private TaskService taskService;


   /**
       * @Author: zk
       * @Date: 2019/6/20 9:28
       * @Param 实际上前台没做分页，默认查询前10条
       * @Return:
       * @Throws
       * @Description:
       */
    @RequestMapping("/toTaskUI")
    public String toTaskUI(String pageNum, String pageSize,HttpServletRequest request){

        BackResult result = this.listTask(pageNum, pageSize);

        Page<Task> page = (Page<Task>) result.getData();
        request.setAttribute("list",page.getRecords());


        return "task/taskList";
    }

    @RequestMapping("/listTask")
    @ResponseBody
    public BackResult listTask(String pageNum, String pageSize){

        try {
            long pn= 1L;
            long ps= 10L;

            if(StringUtils.isNotBlank(pageNum)){
                pn=Long.parseLong(pageNum);
            }

            if(StringUtils.isNotBlank(pageSize)){
                ps=Long.parseLong(pageSize);
            }

            Page<Task> page = (Page<Task>) taskService.page(new Page<>(pn, ps), null);

            return BackResult.ok(page);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        return BackResult.build(500, "网络故障，请稍后重试！");
    }



    /**
     * 添加任务
     * @param task
     * @return
     */
    @RequestMapping(value = "/addTask")
    @ResponseBody
    public BackResult addTask(Task task) {

//        task = new Task();
//        String id = UUID.randomUUID().toString().replace("-", "");
//        task.setId(id);
//        task.setJobCnName("测试任务");
//        task.setJobGroup("test");
//        task.setJobClassName("testJob");
//        task.setJobClassMethod("test01");
//        task.setJobParams(null);
//        task.setJobCronExpression("0/10 * * * * ?");
//        task.setJobMisfirePolicy(0);
//        task.setJobDescription("这是一个测试任务-----------");
//        task.setJobStatus(1);
//        task.setJobStartTime(new Date());
//        task.setCreateTime(new Date());

        try {
            if(StringUtils.isNotBlank(task.getId())){
                this.updateTask(task);
            }else{
                String id = UUID.randomUUID().toString().replace("-", "");
                task.setId(id);
                task.setCreateTime(new Date());
                taskService.addTask(task);
            }


            return BackResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500, "网络故障，请稍后重试！");
    }

    /**
     * 更新任务
     * @param task
     * @return
     */
    @RequestMapping("/updateTask")
    @ResponseBody
    public BackResult  updateTask(Task task){

        try {

            taskService.updateTask(task);
            return BackResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试!");
    }


    /**
     * 删除任务
     * @param id
     * @return
     */
    @RequestMapping("/deleteTask")
    @ResponseBody
    public BackResult deleteTask(String id){

        try {

            taskService.deleteTask(id);
            return BackResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试!");

    }


    /**
     * 暂停任务
     * @param id
     * @return
     */
    @RequestMapping("/pauseTask")
    @ResponseBody
    public BackResult pauseTask(String  id){

        try {

            taskService.pauseTask(id);
            return BackResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试!");

    }

    /**
     * 恢复任务
     * @param id
     * @return
     */
    @RequestMapping("/resumeTask")
    @ResponseBody
    public BackResult resumeTask(String  id){

        try {

            taskService.resumeTask(id);
            return BackResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试!");

    }


    @RequestMapping("/getTaskById")
    @ResponseBody
    public BackResult getTaskById(String id){

        try {

            Task task = taskService.getById(id);
            return BackResult.ok(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BackResult.build(500,"网络故障，请稍后重试!");

    }

}

