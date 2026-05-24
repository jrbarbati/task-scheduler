package com.barbati.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

public class RequestLoggingFilter
{
    private static final Logger log = Logger.getLogger(RequestLoggingFilter.class);
    private static final String START_TIME_KEY = "request-start-time";

    @ServerRequestFilter
    public void onRequest(ContainerRequestContext ctx)
    {
        ctx.setProperty(START_TIME_KEY, System.nanoTime());
    }

    @ServerResponseFilter
    public void onResponse(ContainerRequestContext req, ContainerResponseContext res)
    {
        Long start = (Long) req.getProperty(START_TIME_KEY);
        Long elapsedNs = start != null ? System.nanoTime() - start : -1L;

        log.infof("%s %s -> %d (%d ns)", req.getMethod(), req.getUriInfo().getPath(), res.getStatus(), elapsedNs);
    }
}
