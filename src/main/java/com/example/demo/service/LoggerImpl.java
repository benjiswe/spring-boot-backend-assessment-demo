package com.example.demo.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LoggerImpl implements LoggerInterface {

    Logger LOGGER = LoggerFactory.getLogger("LogImplementationServicesBookDemoApp");


    @Override
    public void displayRequest(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String,String> parameters = parseParameters(request);
        reqMessage.append(new Timestamp(new Date().getTime()));
        reqMessage.append(" | REQUEST |");
        reqMessage.append(" method = [").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("]");

        if(!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("]");
        }

        if(!Objects.isNull(body)) {
            reqMessage.append(" body = [").append(body).append("] ");
        }

        LOGGER.info("Log: {}", reqMessage);
        writeToLogFile(reqMessage);
    }

    @Override
    public void displayResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder respMessage = new StringBuilder();
        Map<String,String> headers = parseHeader(response);
        respMessage.append(new Timestamp(new Date().getTime()));
        respMessage.append(" | RESPONSE |");
        respMessage.append(" method = [").append(request.getMethod()).append("]");
        if(!headers.isEmpty()) {
            respMessage.append(" ResponseHeaders = [").append(headers).append("]");
        }
        respMessage.append(" responseBody = [").append(body).append("]");

        LOGGER.info("Logger: {}",respMessage);
        writeToLogFile(respMessage);
    }


    private Map<String,String> parseHeader(HttpServletResponse response) {
        Map<String,String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for(String str : headerMap) {
            headers.put(str,response.getHeader(str));
        }
        return headers;
    }

    private Map<String,String> parseParameters(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName,paramValue);
        }
        return parameters;
    }

    private void writeToLogFile(StringBuilder logString){
        FileWriter fw;
        try{
            fw = new FileWriter(FileService.logString,true);
            fw.write(logString+"\n");
            fw.close();
            System.out.println("Updated Log File");
        }catch (IOException io){
            io.printStackTrace();
        }
    }


}
