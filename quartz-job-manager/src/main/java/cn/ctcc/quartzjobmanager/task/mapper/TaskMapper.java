package cn.ctcc.quartzjobmanager.task.mapper;

import cn.ctcc.quartzjobmanager.task.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangkui
 * @since 2019-06-18
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
