package com.rickproject.spring;

/**
 * 值對像 VO: (Value Object)
 * 通過此對象封裝spring-configs.xml
 * 文件中定義的<bean>元素信息
 */
public class BeanDefinition {
	/**bean唯一標識*/
	private String id;
	/**包名+類名:java.lang.Object */
	private String targetClass;
	/**通過此對象確定對象是否要延遲加載*/
	private boolean lazy;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public boolean isLazy() {
		return lazy;
	}
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	@Override
	public String toString() {
		return "BeanDefinition [id=" + id + ", targetClass=" + targetClass + ", lazy=" + lazy + "]";
	}
}
