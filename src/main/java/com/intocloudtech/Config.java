package com.intocloudtech;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by sumeet on 11/25/16.
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        // Turn off suffix-based content negotiation
        configurer.favorPathExtension(false);
    }
}
