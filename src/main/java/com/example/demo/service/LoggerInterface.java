package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggerInterface {

    void displayRequest(HttpServletRequest request, Object body);

    void displayResponse(HttpServletRequest request, HttpServletResponse response, Object body);
}
