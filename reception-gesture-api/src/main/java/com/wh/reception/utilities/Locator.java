package com.wh.reception.utilities;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Locator {
	static Context context;
	private static Map<String, Object> cache = new HashMap<>();
	
	public static Object  lookup(String MODULE_NAME, String ejbName,Class<?> viewClass) {
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
