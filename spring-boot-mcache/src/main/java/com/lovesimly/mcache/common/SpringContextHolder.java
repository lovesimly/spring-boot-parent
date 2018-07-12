package com.lovesimly.mcache.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@Component
public class SpringContextHolder implements BeanFactoryPostProcessor{
	
	
	
	/**
	 * 根据Bean命名从BeanFactory静态变量中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) beanFactory.getBean(name);
	}
	
	/**
	 * 根据Bean类型从BeanFactory静态变量中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) beanFactory.getBean(clazz);
	}
	
	
	public static BeanFactory getBeanFactory(){
		checkApplicationContext();
		return beanFactory;
	}

	
	
	private static void checkApplicationContext() {
		if (beanFactory == null) {
			throw new IllegalStateException(
					"BeanFactory not found.");
		}
	}
	

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		
		beanFactory = configurableListableBeanFactory;
		
	}
	
	/**
	 * BeanFactory静态变量.
	 */
	private static BeanFactory beanFactory;

}
