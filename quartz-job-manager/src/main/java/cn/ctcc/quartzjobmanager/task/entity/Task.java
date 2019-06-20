package cn.ctcc.quartzjobmanager.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangkui
 * @since 2019-06-18
 */
@TableName("t_task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 中文名
     */
    @TableField("job_CN_name")
    private String jobCnName;

    /**
     * 任务组
     */
    @TableField("job_group")
    private String jobGroup;

    /**
     * IOC中bean的名字
     */
    @TableField("job_class_name")
    private String jobClassName;

    /**
     * 对应的方法
     */
    @TableField("job_class_method")
    private String jobClassMethod;

    /**
     * 方法参数
     */
    @TableField("job_params")
    private String jobParams;

    /**
     * corn表达式
     */
    @TableField("job_cron_expression")
    private String jobCronExpression;

    /**
     * 错误执行的策略  0默认 1立即触发执行 2触发一次执行 3不触发立即执行
     */
    @TableField("job_misfire_policy")
    private Integer jobMisfirePolicy=0;

    /**
     * 任务描述
     */
    @TableField("job_description")
    private String jobDescription;

    /**
     * 任务状态 0暂停 1运行
     */
    @TableField("job_status")
    private Integer jobStatus;

    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("job_start_time")
    private Date jobStartTime;

    /**
     * 任务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("job_end_time")
    private Date jobEndTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobCnName() {
        return jobCnName;
    }

    public void setJobCnName(String jobCnName) {
        this.jobCnName = jobCnName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobClassMethod() {
        return jobClassMethod;
    }

    public void setJobClassMethod(String jobClassMethod) {
        this.jobClassMethod = jobClassMethod;
    }

    public String getJobParams() {
        return jobParams;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams;
    }

    public String getJobCronExpression() {
        return jobCronExpression;
    }

    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(Date jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public Date getJobEndTime() {
        return jobEndTime;
    }

    public void setJobEndTime(Date jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getJobMisfirePolicy() {
        return jobMisfirePolicy;
    }

    public void setJobMisfirePolicy(Integer jobMisfirePolicy) {
        this.jobMisfirePolicy = jobMisfirePolicy;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", jobCnName='" + jobCnName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobClassName='" + jobClassName + '\'' +
                ", jobClassMethod='" + jobClassMethod + '\'' +
                ", jobParams='" + jobParams + '\'' +
                ", jobCronExpression='" + jobCronExpression + '\'' +
                ", jobMisfirePolicy=" + jobMisfirePolicy +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobStatus=" + jobStatus +
                ", jobStartTime=" + jobStartTime +
                ", jobEndTime=" + jobEndTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
