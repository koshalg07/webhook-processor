package com.koshal.webhook.filter;

import com.koshal.webhook.util.CachedBodyHttpServletRequest;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter to wrap HttpServletRequest with cached body for HMAC validation
 * This allows reading the request body multiple times
 */
@Component
public class CachedBodyRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            
            // Only wrap POST requests to webhook endpoints
            if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && 
                httpRequest.getRequestURI().contains("/webhooks/")) {
                
                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);
                chain.doFilter(cachedRequest, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
