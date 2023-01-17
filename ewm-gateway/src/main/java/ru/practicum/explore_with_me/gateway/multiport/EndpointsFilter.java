package ru.practicum.explore_with_me.gateway.multiport;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class EndpointsFilter implements Filter {
    private int statsPortNum = 0;
    private String hitPathPrefix;
    private String statsPathPrefix;
    private String actuatorPathPrefix;

    EndpointsFilter(String statsPort, String hitPathPrefix, String statsPathPrefix, String actuatorPathPrefix) {
        if (statsPort != null &&
                hitPathPrefix != null &&
                !"null".equals(hitPathPrefix) &&
                statsPathPrefix != null &&
                !"null".equals(statsPathPrefix) &&
                actuatorPathPrefix != null &&
                !"null".equals(actuatorPathPrefix)) {
            statsPortNum = Integer.parseInt(statsPort);
            this.hitPathPrefix = hitPathPrefix;
            this.statsPathPrefix = statsPathPrefix;
            this.actuatorPathPrefix = actuatorPathPrefix;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("Stats Service port filter init: " + statsPortNum + ":" + hitPathPrefix +
                "\nStats Service port filter init: " + statsPortNum + ":" + statsPathPrefix);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (statsPortNum != 0) {
            if (isRequestForActuatorEndpoint(request) && servletRequest.getLocalPort() == statsPortNum) {
                RequestDispatcher dispatcher;
                dispatcher = request.getRequestDispatcher("/github/actuator");
                dispatcher.forward(request, response);
                return;
            }

            if (!isRequestForStatsEndpoint(servletRequest) && servletRequest.getLocalPort() == statsPortNum) {
                RequestDispatcher dispatcher;
                dispatcher = request.getRequestDispatcher("/stats");
                dispatcher.forward(request, response);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isRequestForStatsEndpoint(ServletRequest servletRequest) {
        return ((RequestFacade) servletRequest).getRequestURI().startsWith(hitPathPrefix) ||
                ((RequestFacade) servletRequest).getRequestURI().startsWith(statsPathPrefix);
    }

    private boolean isRequestForActuatorEndpoint(ServletRequest servletRequest) {
        return ((RequestFacade) servletRequest).getRequestURI().startsWith(actuatorPathPrefix);
    }

    @Override
    public void destroy() {
    }
}
