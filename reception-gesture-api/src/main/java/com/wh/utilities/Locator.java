package com.wh.utilities;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Locator {
	public static final String MODULE_NAME = "reception-gesture";
	static Context context;
	private static Map<String, Object> cache = new HashMap<>();
	
	public static Object  lookup(String ejbName,Class<?> viewClass) {
		String jndiName =  MODULE_NAME + "/" + ejbName + "!" + viewClass.getName();
		Object obj = cache.get(jndiName);
		if (obj == null) {
			try {
				context = new InitialContext();
				obj = context.lookup(jndiName);
				cache.put(jndiName, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}