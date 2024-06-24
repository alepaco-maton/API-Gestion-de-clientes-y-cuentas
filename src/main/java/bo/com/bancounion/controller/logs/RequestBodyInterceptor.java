/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.logs;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@Component
@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {

    @Autowired
    HttpServletRequest request;

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        displayReq(request, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public void displayReq(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String, String> parameters = getParameters(request);
        Map<String, String> headers = getHeaders(request);

        reqMessage.append("-------------------REQUEST----------------------\n");
        reqMessage.append("RemoteAddr = [").append(request.getRemoteAddr()).append("]").append(", \n");
        reqMessage.append("RemoteHost = [").append(request.getRemoteHost()).append("]").append(", \n");
        reqMessage.append("RemoteUser= [").append(request.getRemoteUser()).append("]").append(", \n");
        reqMessage.append("method = [").append(request.getMethod()).append("]").append(", \n");
        reqMessage.append(" URI = [").append(request.getRequestURI()).append("?").append(request.getQueryString()).append("] ").append(", \n");

        if (!headers.isEmpty()) {
            reqMessage.append(" ResponseHeaders = [").append(headers).append("]").append(", \n");
        }

        if (!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ").append(", \n");
        }

        if (!Objects.isNull(body)) {
            reqMessage.append("DATA ").append(", \n");
            reqMessage.append(" body = [").append(body).append("]").append(", \n");
        }

        reqMessage.append("------------------------------------------------\n");

        log.info(reqMessage.toString());
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> headerMap = request.getHeaderNames();

        while (headerMap.hasMoreElements()) {
            String headerName = headerMap.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        return headers;
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }

}
