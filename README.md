# quartz-parent
使用springboot+quartz实现对定时任务的动态管理，需要注意以下事项
1 测试中发现，偶有在集群模式下，其中一台tomcat会卡死
2 自定义job是否支持注入spring容器中的对象未测试
3 多参数支持未测试
4 集群模式下是否会出现重复执行任务未测试
