package com.nextyu.summer.framework;

import com.alibaba.fastjson.JSON;
import com.nextyu.summer.framework.annotation.RequestMethod;
import com.nextyu.summer.framework.annotation.ResponseBody;
import com.nextyu.summer.framework.annotation.RestController;
import com.nextyu.summer.framework.bean.Handler;
import com.nextyu.summer.framework.helper.BeanHelper;
import com.nextyu.summer.framework.helper.ConfigHelper;
import com.nextyu.summer.framework.helper.ControllerHelper;
import com.nextyu.summer.framework.util.CastUtil;
import com.nextyu.summer.framework.util.ReflectionUtil;
import com.nextyu.summer.framework.util.RequestMethodUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 请求转发器
 * created on 2017-06-23 13:29
 *
 * @author nextyu
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();
        // 注册处理 jsp 的 servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppViewPath() + "*");

        // 注册处理静态资源的默认 servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppStaticPath() + "*");


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 请求方法，请求路径
        String requestMethod = request.getMethod();
        String requestPath = request.getPathInfo();
        RequestMethod[] requestMethods = {RequestMethodUtil.getRequestMethodEnum(requestMethod)};
        Handler handler = ControllerHelper.getHandler(requestMethods, requestPath);
        if (null != handler) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // TODO 请求参数
            Method controllerMethod = handler.getControllerMethod();
            // Controller 返回值
            Object result = ReflectionUtil.invokeMethod(controllerBean, controllerMethod, null);

            if (controllerClass.isAnnotationPresent(RestController.class) || controllerMethod.isAnnotationPresent(ResponseBody.class)) {
                handleJSON(result, response);
            } else {
                handleView(result, request, response);
            }


        }
    }

    private void handleView(Object result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO 有问题
        final String path = CastUtil.castString(result);
        request.getRequestDispatcher(ConfigHelper.getAppViewPath() + path + ConfigHelper.getAppViewSuffix()).forward(request, response);
    }

    private void handleJSON(Object result, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = JSON.toJSONString(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
