package com.rickproject.spring;

public interface BeanFactory {
	public <T> T getObject(String key, Class<T> cls);
}
