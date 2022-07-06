package com.example.demo.interceptor;


import com.example.demo.service.LoggerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BookItcp implements HandlerInterceptor {

    @Autowired
    LoggerInterface loggerInterface;

    // for conditions where payload is empty in GET, DELETE, PUT requests
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals(HttpMethod.GET.name())
                || request.getMethod().equals(HttpMethod.DELETE.name())
                || request.getMethod().equals(HttpMethod.PUT.name()))    {
            loggerInterface.displayRequest(request,null);
        }
        return true;
    }
}




