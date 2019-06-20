package cn.ctcc.quartzjobmanager.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @Author: zk
 * @Date: 2019/2/27 17:37
 * @Description: druid连接池访问的过滤器，主要过滤掉静态文件的请求
 * @Modified:
 * @version: V1.0
 */
@WebFilter(filterName = "druidStatFilter",urlPatterns = "/*",
        initParams = {
                //忽略资源
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        })
public class DruidStatFilter extends WebStatFilter {
}
