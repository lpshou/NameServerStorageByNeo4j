package com.b409.nameServer.experiment;

import com.b409.nameServer.namespace.api.File;
import com.b409.nameServer.namespace.api.User;

public class FileTest {

	//查询测试
	static void queryFileTest(String fileName,int num){
		long start=System.currentTimeMillis();
		
		for(int i=0;i<num;i++){
			File.queryFileProperty(fileName+i);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("查询运行耗时= "+lastTime+" 毫秒");
	}
	//更新测试
	static void updateFileTest(String fileName,String props,int num){
		long start=System.currentTimeMillis();
		for(int i=0;i<num;i++){
			File.updateFileProperty(fileName+i, props);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("更新运行耗时= "+lastTime+" 毫秒");
	}
	//添加测试
	static void addFileTest(int num){
		long start=System.currentTimeMillis();
		String userName="user";
		String fileName = "fileTest";
		for(int i=0;i<num;i++){
			File.createFile(userName+i, "User", fileName+i, "as you like");
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("添加运行耗时= "+lastTime+" 毫秒");
	}
	//删除测试
	static void deleteFileTest(int num){
		long start=System.currentTimeMillis();
		String userName="user";
		String fileName = "fileTest";
		for(int i=0;i<num;i++){
			File.deleteFile(userName+i, "User", fileName+i);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("删除运行耗时= "+lastTime+" 毫秒");
	}
	public static void main(String[] args) {
//		queryFileTest("file", 500);
//		updateFileTest("file", "", 500);
//		addFileTest(50);
//		deleteFileTest(50);

	}

}
