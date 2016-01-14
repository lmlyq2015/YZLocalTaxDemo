package com.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.util.TaxUtil;
import com.vos.User;

public class SystemInterceptor extends HandlerInterceptorAdapter {
    private static final String[] IGNORE_URI = {"/login.jsp", "/css/","images/","jquery/","themes/","login"};
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURL().toString();
        System.out.println(">>>: " + url);
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //T_supplier_user user = LoginController.getLoginUser(request);
        	User user = (User) request.getSession().getAttribute("current_user");
            if (user == null) {
            	request.getRequestDispatcher("./jsp/login/login.jsp").forward(request, response);  
            	
            	
            } else {
            	
            	flag = true;
            }
        }
        
        return flag;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
