package com.rickproject.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 1.讀配置文件: BeanDefinitionReader
 * 2.封裝配置信息: BeanDefinition, Map
 * 3.創建實例對象: ReflectionUtil
 * 4.存儲實例: Map<String,Object>
 * 5.提供查詢實例的方式
 */
//如果這樣寫，在父類BeanDefinitionReader初始化時，就會用到beanMap，而這時beanMap還沒初始化，會產生NullPointerException的錯誤
//public class DefaultBeanFactory extends BeanDefinitionReader implements BeanFactory {
public class DefaultBeanFactory implements BeanFactory {
	private Map<String,BeanDefinition> beanMap = new ConcurrentHashMap<>();
	private Map<String,Object> instanceMap = new ConcurrentHashMap<>();

	public DefaultBeanFactory(String cfgFile) {
		//super(cfgFile);
		//System.out.println(beanMap);
		//System.out.println(instanceMap);
		new BeanDefinitionReader(cfgFile){
			@Override
			void registerBean(BeanDefinition bd) {
				beanMap.put(bd.getId(), bd);	
			}		
		};
	}
	/*
	@Override
	void registerBean(BeanDefinition bd) {
		beanMap.put(bd.getId(), bd);
	}	
*/
	/**
	 * 獲取類的實例對象
	 */
	public <T> T getObject(String key, Class<T> cls) {
		//0.對key和class連行校驗
		//1)校驗key是否存在
		if(!beanMap.containsKey(key))
			throw new RuntimeException("No such key in bean map");
		//2)校驗類型對應的資源是否存在
		BeanDefinition bd = beanMap.get(key);
		if(!bd.getTargetClass().equals(cls.getName()))
			throw new RuntimeException("No such bean class");
		//1.從instanceMap中取
		Object obj = instanceMap.get(key);
		if(obj == null) {
			obj = ReflectUtils.newInstance(cls);
			instanceMap.put(key, obj);
		}
		//2.返回實例對象
		return (T)obj;
	}
}
