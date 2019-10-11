package com.eg.egsc.scp.simulator.util;

/**
 * 包号.
 */
public class PackageNumSingleton {
	private int packgeNum = 1; // 单例对象
	private static PackageNumSingleton packageNumSingleton;

	private PackageNumSingleton() {
	}

	public static synchronized PackageNumSingleton getInstance() {
		if (null == packageNumSingleton) {
			packageNumSingleton = new PackageNumSingleton();
		}
		return packageNumSingleton;
	}

	/**
	 * 实现包号自增方法.
	 * 
	 * @return int
	 */
	public synchronized int incrementPackgeNum() {
		if (packgeNum < Integer.MAX_VALUE) {
			packgeNum++;
		} else {
			packgeNum = 1;
		}
		return packgeNum;
	}
}
