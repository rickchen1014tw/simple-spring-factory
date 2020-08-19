package com.rickproject.spring;

import java.util.Date;

public class TestBeanFactory {
	public static void main(String[] args) {
		BeanFactory factory = 
				new DefaultBeanFactory("spring-configs.xml");
			  //new AnnotationBeanFactory(SpringConfig.class); //讀註解
		Object obj1 = factory.getObject("obj", Object.class);
		Object obj2 = factory.getObject("obj", Object.class);
		System.out.println(obj1 == obj2);
		Date date = factory.getObject("date", Date.class);
		System.out.println(date.getTime());
	}	
}
