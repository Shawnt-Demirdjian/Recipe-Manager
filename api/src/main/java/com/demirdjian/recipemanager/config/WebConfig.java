package com.demirdjian.recipemanager.config;

import com.demirdjian.recipemanager.converter.CategoryConverter;
import com.demirdjian.recipemanager.converter.CookingMethodConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	@Override
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService f = super.mvcConversionService();
		f.addConverter(new CategoryConverter());
		f.addConverter(new CookingMethodConverter());
		return f;
	}
}
