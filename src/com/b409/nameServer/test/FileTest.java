package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.File;

public class FileTest {
	public static void main(String[] args) {
		long start=System.currentTimeMillis();

//		File.listAllFiles("lisi_华科风光", "Directory");
		File.createFile("lisi_华科风光", "Directory" , "绝望坡.ppt", "123456");
//		File.deleteFile("lisi_华科风光", "Directory" , "绝望坡.ppt");
		File.getFileLocation("lisi_华科风光_绝望坡.ppt");
		File.updateFileLocation("lisi_华科风光_绝望坡.ppt", "1234567890");
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}
}
