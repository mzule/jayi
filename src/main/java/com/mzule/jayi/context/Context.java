package com.mzule.jayi.context;

public class Context {

	private static String baseDir;

	public static void setBaseDir(String baseDir) {
		Context.baseDir = baseDir;
	}

	public static String getBaseDir() {
		return baseDir;
	}

}
