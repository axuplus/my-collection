
package com.utils.util;


import org.apache.commons.lang3.StringUtils;

public class PathUtils {

	public static final String DOT = ".";

	/**
	 * 获取java maven项目下的resource中的文件
	 * @param path
	 * @return
	 */
	public static String getResourcePath(String path) {
		return Thread.currentThread().getContextClassLoader().getResource(path).getPath();
	}

	/**
	 * 获取扩展名
	 *
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT))
			return StringUtils.EMPTY;
		String ext = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, DOT));
		return StringUtils.trimToEmpty(ext);
	}
	
	/**
	 * 当前的路径
	 * @param clazz
	 * @return
	 */
	public static String getCurrentPath(Class<?> clazz) {
		String path = "";
		try {
			path = clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (Exception e) {
			path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
		}
		path = path.replaceFirst("file:/", "");
		path = path.replaceAll("!/", "");
		path = path.substring(0, path.lastIndexOf("/"));
		if (path.substring(0, 1).equalsIgnoreCase("/")) {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("window") >= 0) {
				path = path.substring(1);
			}
		}
		return path;
	}
	
	/**
	 * ClassPath (通常properties 目录)
	 * @return
	 */
	public static String classPath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	/**
	 * Web跟路径 (通常为样式文件/静态页面 目录) (也可以根据request获取:request.getRealPath("/") )
	 * @return
	 */
	public static String webPath() {
		String realPath = classPath();
		int wei = realPath.lastIndexOf("WEB-INF/classes/");
		if (wei > -1) {
			realPath = realPath.substring(0, wei);
		}
		return realPath;
	}

	/**
	 * WEB-INF目录 (通常为template文件 目录)
	 * @return
	 */
	public static String webInfPath() {
		String realPath = classPath();
		int wei = realPath.lastIndexOf("WEB-INF/classes/");
		if (wei > -1) {
			realPath = realPath.substring(0, wei);
		}
		return realPath + "WEB-INF/";
	}

}
