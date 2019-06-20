package cn.ctcc.quartzjobmanager.task.service.impl;

import cn.ctcc.quartzjobmanager.jobmanager.ScheduleUtils;
import cn.ctcc.quartzjobmanager.task.entity.Task;
import cn.ctcc.quartzjobmanager.task.mapper.TaskMapper;
import cn.ctcc.quartzjobmanager.task.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangkui
 * @since 2019-06-18
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private Scheduler scheduler;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTask(Task task) {

        this.save(task);

        boolean flag = ScheduleUtils.addTask(scheduler, task);
        if(!flag){
            throw new RuntimeException("添加任务失败！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Task task) {

        this.updateById(task);
        boolean flag = ScheduleUtils.updateTask(scheduler, task);
        if(!flag){
            throw new RuntimeException("更新任务失败！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTask(String id) {

        Task task = this.getById(id);
        this.removeById(id);
        if(task!=null) {
            boolean flag = ScheduleUtils.deleteTask(scheduler, task);
            if (!flag) {
                throw new RuntimeException("删除任务失败！");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pauseTask(String id) {

        Task task = this.getById(id);
        task.setJobStatus(0);
        task.setModifyTime(new Date());
        this.updateById(task);
        if(task!=null) {
            boolean flag = ScheduleUtils.pauseTask(scheduler, task);
            if(!flag){
                throw new RuntimeException("暂停任务失败！");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resumeTask(String id) {

        Task task = this.getById(id);
        task.setJobStatus(1);
        task.setModifyTime(new Date());
        this.updateById(task);
        if(task!=null) {
            boolean flag = ScheduleUtils.resumeTask(scheduler, task);
            if (!flag) {
                throw new RuntimeException("恢复任务失败！");
            }
        }
    }
}
