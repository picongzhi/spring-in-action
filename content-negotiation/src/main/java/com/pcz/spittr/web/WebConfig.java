package com.pcz.spittr.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author picongzhi
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.pcz.spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceView = new InternalResourceViewResolver();
        internalResourceView.setPrefix("/WEB-INF/views/");
        internalResourceView.setSuffix(".jsp");

        return internalResourceView;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Configuration
    public static class ContentNegotiationConfig extends WebMvcConfigurerAdapter {
        @Bean
        public ViewResolver cnViewResolver(ContentNegotiationManager contentNegotiationManager) {
            ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
            contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);

            return contentNegotiatingViewResolver;
        }

        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.defaultContentType(MediaType.TEXT_HTML);
        }

        @Bean
        public ViewResolver beanNameViewResolver() {
            return new BeanNameViewResolver();
        }

        @Bean
        public View spittles() {
            return new MappingJackson2JsonView();
        }
    }
}
