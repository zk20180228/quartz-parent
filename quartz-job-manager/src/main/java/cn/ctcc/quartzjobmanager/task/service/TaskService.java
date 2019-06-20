package cn.ctcc.quartzjobmanager.task.service;

import cn.ctcc.quartzjobmanager.task.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangkui
 * @since 2019-06-18
 */
public interface TaskService extends IService<Task> {

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(String id);

    void pauseTask(String id);

    void resumeTask(String id);
}
