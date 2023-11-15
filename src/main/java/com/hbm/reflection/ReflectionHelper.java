package com.hbm.reflection;

import java.lang.reflect.Field;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class ReflectionHelper {
	
	private ReflectionHelper() {}
	
	public static boolean setField(Class<?> clazz, String fieldName, Object instance, Object value) {
		try {
			if (!instance.getClass().equals(clazz))
				throw new Exception();
			Field f = clazz.getField(fieldName);
			return ReflectionHelper.setField(clazz, f, instance, value);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean setField(Class<?> clazz, Field f, Object instance, Object value) {
		try {
			if (!instance.getClass().equals(clazz))
				throw new Exception();
			f.setAccessible(true);
			f.set(instance, value.getClass().cast(value));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}