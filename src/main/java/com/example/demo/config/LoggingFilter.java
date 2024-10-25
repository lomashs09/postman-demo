package com.example.demo.config;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
@WebFilter(urlPatterns = "/*") // Applies to all URL patterns
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Wrap the request to make the body available for reading multiple times
        CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper((HttpServletRequest) request);

        // Log the request body
        logger.info("Request Body: {}", requestWrapper.getRequestBody());

        // Continue with the request chain using the wrapped request
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {}
}
