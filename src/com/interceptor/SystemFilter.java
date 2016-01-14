package com.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

import com.vos.User;

public class SystemFilter implements Filter {
    private static final String[] IGNORE_URI = {"/login.jsp", "/css/","images/","jquery/","themes/","login","util/","getContentByWebPage"};

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filter) throws IOException, ServletException {
		// TODO Auto-generated method stub
	       HttpServletRequest httpRequest = (HttpServletRequest) req;  
	       HttpServletResponse httpResponse = (HttpServletResponse) resp;  
	       HttpSession session = httpRequest.getSession(true);  
	       String url = httpRequest.getRequestURL().toString();
	       System.out.println(">>>: " + url);
	        for (String s : IGNORE_URI) {
	            if (url.contains(s)) {
	            	filter.doFilter(req, resp);  
	                  return;
	            }
	        }
	        User user = (User) httpRequest.getSession().getAttribute("current_user");
	        if (user == null) {
	        	boolean isAjaxRequest = isAjaxRequest(httpRequest);
	        	if(isAjaxRequest) {
	                httpResponse.setCharacterEncoding("UTF-8");  
	                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),  
	                        "您已经太长时间没有操作,请刷新页面");  
	        	}
		        httpResponse.sendRedirect(httpRequest.getScheme()+"://"+ httpRequest.getServerName()+":"+httpRequest.getServerPort()+httpRequest.getContextPath()+"/jsp/login/login.jsp");  
		        return;  
	        }

	        filter.doFilter(req, resp);  
	        return;  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

    public static boolean isAjaxRequest(HttpServletRequest request) {  
        return request.getRequestURI().startsWith("/api");  
//        String requestType = request.getHeader("X-Requested-With");  
//        return requestType != null && requestType.equals("XMLHttpRequest");  
    }  
}
