package ru.practicum.explore_with_me.gateway.multiport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.statsPort:null}")
    private String statsPort;

    @Value("${server.hitPathPrefix:null}")
    private String hitPathPrefix;

    @Value("${server.statsPathPrefix:null}")
    private String statsPathPrefix;

    @Value("/actuator/health")
    private String actuatorPathPrefix;

    @Bean
    public FilterRegistrationBean<EndpointsFilter> statsServiceEndpointsFilter() {
        return new FilterRegistrationBean<>(new EndpointsFilter(statsPort, hitPathPrefix, statsPathPrefix,
                actuatorPathPrefix));
    }
}