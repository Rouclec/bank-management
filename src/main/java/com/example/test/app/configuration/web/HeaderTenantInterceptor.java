package com.example.test.app.configuration.web;

import com.example.test.app.configuration.datasource.TenantConnectionProvider;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.test.app.configuration.datasource.TenantConnectionProvider.DEFAULT_TENANT;

@Component
public class HeaderTenantInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response, Object object) throws Exception {
            System.out.println("In preHandle we are Intercepting the Request");
            System.out.println("____________________________________________");
            String requestURI = request.getRequestURI();
            String tenantID = request.getHeader("X-TenantID");
            System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: " + tenantID);
            System.out.println("____________________________________________");

            if (tenantID == null){
//                response.getWriter().write("X-TenantID not present in the Request Header");
                ThreadTenantStorage.setTenantId(DEFAULT_TENANT);
                return true;
            }
            ThreadTenantStorage.setTenantId(tenantID);
            return true;
        }

        @Override
        public void postHandle(
                HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
                throws Exception {
            ThreadTenantStorage.clear();
        }


}
