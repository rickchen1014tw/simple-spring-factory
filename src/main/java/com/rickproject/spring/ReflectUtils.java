package com.rickproject.spring;

import java.lang.reflect.Constructor;

public abstract class ReflectUtils {	//這裡加abstract，不希望外界能夠創建ReflectUtils對象

	/**
	 * 創建類的實例對象
	 */
	public static Object newInstance(Class<?> cls) {	//希望通過類名直接訪問
		Constructor<?> con;
		try {
			//1.獲取構造方法對象
			con = cls.getDeclaredConstructor();
			//2.設置構造方法可訪問
			if(!con.isAccessible())
				con.setAccessible(true);
			//3.創建對象並返回
			return con.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
