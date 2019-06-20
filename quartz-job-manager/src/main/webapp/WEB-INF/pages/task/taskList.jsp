<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
<head>
    <title>
        任务管理
    </title>
    <%--引入静态文件，webjars--%>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="/webjars/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
    <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/static/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
    <div class="container" style="width: 100%">
        <div class="row clearfix">
            <div class="col-md-12" style="margin-top: 15px;">
                <div class="row" >
                    <div class="col-md-1">
                        <button class="btn btn-primary" type="button" data-toggle="modal" onclick="addTaskBefore()">添加任务</button>
                    </div>
                </div>
                <table class="table table-bordered table-hover table-condensed text-center" style="margin-top: 15px;">
                    <thead >
                        <tr >
                            <th class="text-center">
                                任务名
                            </th>
                            <th class="text-center">
                                任务组
                            </th>
                            <th class="text-center">
                                任务BeanName
                            </th>
                            <th class="text-center">
                                任务BeanMethod
                            </th>
                            <th class="text-center">
                                任务参数
                            </th>
                            <th class="text-center">
                                corn表达式
                            </th>
                            <th class="text-center">
                                错过策略
                            </th>
                            <th class="text-center">
                                任务描述
                            </th>
                            <th class="text-center">
                                任务状态
                            </th>
                            <th class="text-center">
                                任务开始时间
                            </th>
                            <th class="text-center">
                                任务结束时间
                            </th>
                            <th class="text-center">
                                创建时间
                            </th>
                            <th class="text-center">
                                修改时间
                            </th>
                            <th class="text-center">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty list}">
                            <c:forEach items="${list}" var="task" varStatus="index">
                                <tr >
                                    <td class="hidden">${task.id}</td>
                                    <td>
                                        ${task.jobCnName}
                                    </td>
                                    <td>
                                       ${task.jobGroup}
                                    </td>
                                    <td>
                                        ${task.jobClassName}
                                    </td>
                                    <td>
                                        ${task.jobClassMethod}
                                    </td>
                                    <td>
                                        ${task.jobParams}
                                    </td>
                                    <td>
                                        ${task.jobCronExpression}
                                    </td>
                                    <td>
                                        <%--
                                                <option value="0">默认</option>
                                                <option value="1">立即触发执行</option>
                                                <option value="2">立即触发一次执行</option>
                                                <option value="3">错过不执行</option>
                                        --%>
                                        <c:choose>
                                            <c:when test="${task.jobMisfirePolicy==0}">
                                                默认(只会执行一次misfire，下次正点执行)
                                            </c:when>
                                            <c:when test="${task.jobMisfirePolicy==1}">
                                                所有misfire的任务会马上执行,下次正点执行
                                            </c:when>
                                            <c:when test="${task.jobMisfirePolicy==2}">
                                                只会执行一次misfire，下次正点执行
                                            </c:when>
                                            <c:when test="${task.jobMisfirePolicy==3}">
                                                错过不执行,下次正点执行
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        ${task.jobDescription}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${task.jobStatus==0}">
                                                暂停
                                            </c:when>
                                            <c:when test="${task.jobStatus==1}">
                                                运行
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${task.jobStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${task.jobEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${task.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <div>
                                            <a type="button" href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="pauseOrResumeTask('${task.id}','${task.jobStatus}')"  >
                                                <c:choose>
                                                    <c:when test="${task.jobStatus==0}">
                                                        激活
                                                    </c:when>
                                                    <c:when test="${task.jobStatus==1}">
                                                        暂停
                                                    </c:when>
                                                </c:choose>
                                            </a>
                                            <a type="button" href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="updateTask('${task.id}')" >编辑</a>
                                            <a type="button" href="javascript:void(0)" class="btn btn-primary btn-sm" onclick="deleteTask('${task.id}')" >删除</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${list == null || fn:length(list) == 0}">
                            <tr >
                                <td colspan="14" style="text-align: center;font-size: 15px">无满足要求的查询结果</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <!-- 添加任务模态框（Modal） -->
    <div class="modal fade" id="task-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 720px;">
                <div class="modal-header">
                    <button type="button" class="close closeModal"  aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">添加任务</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form" id="task-form">

                        <input type="hidden" class="form-control" name="id" id="taskId">
                        <div class="form-group">
                            <label for="jobCnName" class="col-sm-3 control-label">任务名</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="jobCnName" id="jobCnName" placeholder="尽量输入中文名，以便好区分任务" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="jobGroup" class="col-sm-3 control-label">任务组</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control noEdit" name="jobGroup" id="jobGroup" placeholder="尽量输入英文" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobClassName" class="col-sm-3 control-label">任务BeanName</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control noEdit" name="jobClassName" id="jobClassName" placeholder="IOC中bean的名字">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobClassMethod" class="col-sm-3 control-label">任务BeanMethod</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control noEdit" name="jobClassMethod" id="jobClassMethod" placeholder="IOC中bean的方法">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobParams" class="col-sm-3 control-label">任务参数值</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="jobParams" id="jobParams" placeholder="多个参数有序','分隔">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobCronExpression" class="col-sm-3 control-label">cron表达式</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="jobCronExpression" id="jobCronExpression" placeholder="请输入cron表达式">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobMisfirePolicy" class="col-sm-3 control-label">错过策略</label>
                            <div class="col-sm-8">
                                <%--0默认 1立即触发执行 2触发一次执行 3不触发立即执行--%>
                                <select class="form-control"  name="jobMisfirePolicy" id="jobMisfirePolicy">
                                    <option value="0">默认(只会执行一次misfire，下次正点执行)</option>
                                    <option value="1">所有misfire的任务会马上执行,下次正点执行</option>
                                    <option value="2">只会执行一次misfire，下次正点执行</option>
                                    <option value="3">错过不执行,错过不执行,下次正点执行</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobStatus" class="col-sm-3 control-label">任务状态</label>
                            <div class="col-sm-8">
                                <%--0暂停 1运行--%>
                                <select class="form-control" name="jobStatus" id="jobStatus">
                                    <option value="0">暂停</option>
                                    <option value="1">运行</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobStartTime" class="col-sm-3 control-label">任务开始时间</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control date-picker" name="jobStartTime" id="jobStartTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobEndTime" class="col-sm-3 control-label">任务结束时间</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control date-picker" name="jobEndTime" id="jobEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="jobDescription" class="col-sm-3 control-label">任务描述</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" rows="3" name="jobDescription" id="jobDescription"></textarea>                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default closeModal" >关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addTask()">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


</body>
</html>

<script type="text/javascript">

    
    $(function(){
        $(".closeModal").on("click",function () {
            $("#task-form")[0].reset();
            $("#task-modal").modal("hide");
        });
    });

  function addTask() {

      $(".noEdit").attr("disabled",false);
      $.ajax({
          url:"${pageContext.request.contextPath}/task/addTask",
          type:"post",
          data:$("#task-form").serialize(),
          success:function(backData){
                if(backData.status==200){
                     window.location.reload();
                }
          }
      });
  }

  function pauseOrResumeTask(taskId,taskStatus){

      var url="";
      if(taskStatus==0){
         url="${pageContext.request.contextPath}/task/resumeTask";
      }else{
          url="${pageContext.request.contextPath}/task/pauseTask";
      }

      $.ajax({
          url:url,
          type:"get",
          data:"id="+taskId,
          success:function (backData) {
              if(backData.status==200){
                  window.location.reload();
              }
          }
      });

  }

  function updateTask(taskId) {

      $(".noEdit").attr("disabled",true);
      $.ajax({
          url:"${pageContext.request.contextPath}/task/getTaskById",
          type:"get",
          data:"id="+taskId,
          success:function (backData) {
              if(backData.status==200){

                  var task=backData.data;
                  $("#taskId").val(task.id);
                  $("#jobCnName").val(task.jobCnName);
                  $("#jobGroup").val(task.jobGroup);
                  $("#jobClassName").val(task.jobClassName);
                  $("#jobClassMethod").val(task.jobClassMethod);
                  $("#jobParams").val(task.jobParams);
                  $("#jobCronExpression").val(task.jobCronExpression);
                  $("#jobMisfirePolicy").val(task.jobMisfirePolicy);
                  $("#jobDescription").val(task.jobDescription);
                  $("#jobStatus").val(task.jobStatus);
                  $("#jobStartTime").val(task.jobStartTime);
                  $("#jobEndTime").val(task.jobEndTime);

                  $("#task-modal").modal("show");
              }
          }
      });
  }

  function deleteTask(taskId){

      $.ajax({
          url:"${pageContext.request.contextPath}/task/deleteTask",
          type:"get",
          data:"id="+taskId,
          success:function (backData) {
              if(backData.status==200){
                  window.location.reload();
              }
          }
      });
  }

function addTaskBefore(){

    $(".noEdit").attr("disabled",false);
    $("#taskId").val("");
    $("#task-modal").modal("show");
}


</script>