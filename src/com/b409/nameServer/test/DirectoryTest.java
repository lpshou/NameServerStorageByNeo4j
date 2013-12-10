package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.Directory;
import com.b409.nameServer.namespace.api.User;

public class DirectoryTest {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
//		
		Directory.createDirectory("lisi", "User", "华科风光");
		Directory.createDirectory("lisi", "User", "三峡风光");
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}

}
