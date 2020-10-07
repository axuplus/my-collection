
package com.utils.util;

public class JVMUtils {

	/**
	 * 返回虚拟机使用内存量的一个估计值,内容为"xxM"
	 * @return
	 */
	public static String usedMemory() {
		Runtime runtime = Runtime.getRuntime();
		return (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + "M";
	}
	
	
	/**
	 * 增加JVM停止时要做处理事件
	 */
	public static void addShutdownHook( Runnable runnable ) {
		Runtime.getRuntime().addShutdownHook( new Thread( runnable ) );
	}
}
