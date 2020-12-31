package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //声明UserService业务对象
    private UserService service=new UserServiceImpl();


    //注册
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkCode_server=(String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkCode_server==null || !checkCode_server.equalsIgnoreCase(check)){
            ResultInfo info=new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper=new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
       // UserService service=new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info=new ResultInfo();

        if (flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        ObjectMapper mapper=new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // UserService service=new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info=new ResultInfo();
        if (u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名密码错误");
        }else {
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }
    //查找一个
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    //退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().invalidate();//销毁session
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

}
