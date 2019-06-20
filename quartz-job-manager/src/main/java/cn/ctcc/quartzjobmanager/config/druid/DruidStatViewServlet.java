package cn.ctcc.quartzjobmanager.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @Author: zk
 * @Date: 2019/2/27 17:43
 * @Description: druid连接池的监控Servlet ,访问地址：http://192.168.0.26:8890/druid/index.html
 * @Modified:
 * @version: V1.0
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name="allow",value="127.0.0.1"),// IP白名单(没有配置或者为空，则允许所有访问)
                @WebInitParam(name="deny",value="192.168.0.88"),// IP黑名单，不允许访问的机器 (存在共同时，deny优先于allow)
                @WebInitParam(name="loginUsername",value="admin"),// 用户名
                @WebInitParam(name="loginPassword",value="admin"),// 密码
                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
        }
    )
public class DruidStatViewServlet extends StatViewServlet {
}
