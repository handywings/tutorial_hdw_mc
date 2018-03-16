package com.hdw.mccable.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hdw.mccable.utils.ConstantUtils;

@EnableWebMvc
@ComponentScan(basePackages = {"com.hdw.mccable"})
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
//		registry.addResourceHandler("/resources/**").addResourceLocations("resources/");
		registry.addResourceHandler(ConstantUtils.RESOURCE_HANDLER_IMAGES).addResourceLocations(ConstantUtils.RESOURCE_LOCATIONS_IMAGES);

	}
	
}