package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.File;

public class FileTest {
	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		File.listAllFiles("摄影", "Directory");
		File.createFile("摄影", "Directory", "瀑布风光.jpg", "123456");
		File.listAllFiles("摄影", "Directory");
		
		
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}
}
