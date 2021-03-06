package com.github.jobop.anylog.common.utils;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.jobop.anylog.common.javassist.ClassClassPath;
import com.github.jobop.anylog.common.javassist.ClassPool;

public class JavassistUtils {
	private static List<ClassClassPath> ccps = new ArrayList<ClassClassPath>();

	public static void initJavassistClassPath(Instrumentation inst) {
		Class<?>[] classes = inst.getAllLoadedClasses();
		Set<ClassLoader> classloader = ClassLoaderUtils.findAllClassLoadersByClasses(classes);
		for (ClassLoader classLoader : classloader) {
			if (null != classLoader) {

				addJavassistClassPath(classLoader.getClass());
			}
		}
	}

	public static void addJavassistClassPath(Class<?> clazz) {
		ClassClassPath ccp = new ClassClassPath(clazz);
		ccps.add(ccp);
		ClassPool.getDefault().insertClassPath(ccp);
	}

	public static void cleanJavassistClassPath() {
		for (ClassClassPath ccp : ccps) {
			ClassPool.getDefault().removeClassPath(ccp);
		}
		ccps.clear();
	}
}
