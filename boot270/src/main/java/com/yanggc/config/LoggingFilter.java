package com.yanggc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

/**
 * Description:
 * 对请求响应参数进行日志记录
 * @author: YangGC
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/actuator/health".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Instant start = Instant.now();
        filterChain.doFilter(requestWrapper, responseWrapper);
        Instant end = Instant.now();

        String requestBody = readRequest(requestWrapper);


        String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        responseWrapper.copyBodyToResponse();

        logger.info("{} {} {}\n<<<<<<\ntoke={}\nreq= {}\nrt= {} ms\nres= {}\n>>>>>>\n",
                request.getMethod(),
                request.getRequestURI(),
                responseWrapper.getStatus(),
                request.getHeader("Authorization"),
                requestBody,
                Duration.between(start, end).toMillis(),
                responseBody);
    }

    private String readRequest(ContentCachingRequestWrapper request) {
        if ("GET".equals(request.getMethod())) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {

                sb.append(e.getKey()).append(":").append(Arrays.toString(e.getValue())).append(", ");
            }

            return sb.toString();
        } else if ("POST".equals(request.getMethod())) {
            return new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        } else {
            return request.getMethod();
        }
    }

}
