package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.Directory;
import com.b409.nameServer.namespace.api.User;

public class DirectoryTest {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
//		Directory.createDirectory("wangwu", "user", "摄影");
//		Directory.createDirectory("wangwu", "user", "骑行");
//		Directory.deleteDirectory("wangwu", "user", "骑行");
//		Directory.createDirectory("摄影", "Directory", "三峡风景");
//		
//		Directory.listAllDirectories("摄影", "Directory");
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}

}
