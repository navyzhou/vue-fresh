package com.yc.fresh.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 前台页面跳转拦截
 * 源辰信息
 * @author navy
 * @date 2019年10月1日
 */
public class FrontForwardInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession().getAttribute("loginMember") == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('请先登录...');localStorage.setItem('targetURL', 'front/cart.html');location.href='../login.html';</script>");
			out.flush();
			return false;
		}
		
		String path = request.getServletPath(); // fresh/page/weclome.html
		path = path.substring(path.lastIndexOf("/") + 1);
		request.getRequestDispatcher("/WEB-INF/front/" + path).forward(request, response);
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
