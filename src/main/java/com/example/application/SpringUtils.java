package com.example.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public <T> T getBean(Class<T> requiredType) {
		return getApplicationContext().getBean(requiredType);
	}

	private static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		SpringUtils.applicationContext =applicationContext;
    }
}
