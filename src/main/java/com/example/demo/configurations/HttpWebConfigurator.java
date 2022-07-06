package com.example.demo.configurations;

import com.example.demo.interceptor.BookItcp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// HttpWebConfigurator -> BookItcp


@Component
public class HttpWebConfigurator implements WebMvcConfigurer {


    @Autowired
    private BookItcp bookItcp;

    //register the interceptor into the registry
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bookItcp);
    }
}
