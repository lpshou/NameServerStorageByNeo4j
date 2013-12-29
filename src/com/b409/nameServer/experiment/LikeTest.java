package com.b409.nameServer.experiment;

import com.b409.nameServer.relationship.api.Friend;
import com.b409.nameServer.relationship.api.Like;

public class LikeTest {
	//查询测试
	static void queryLikeTest(int num){
		long start=System.currentTimeMillis();
		String userName = "user";
		String fileName = "file";
		for(int i=0;i<num-1;i++){
			int i1=(i+1)*10;
			Like.queryLike(userName+i, fileName+i1);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/(num);
		System.out.println("查询运行耗时= "+lastTime+" 毫秒");
	}
	
	//添加测试
	static void addLikeTest(int num){
		long start=System.currentTimeMillis();
		String userName = "user";
		String fileName = "file";
		for(int i=0;i<num-1;i++){
			int i1=(i+1)*10+1;
			Like.createLike(userName+i, fileName+i1);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("添加运行耗时= "+lastTime+" 毫秒");
	}
	//删除测试
	static void deleteFileTest(int num){
		long start=System.currentTimeMillis();
		String userName = "user";
		String fileName = "file";
		for(int i=0;i<num-1;i++){
			int i1=(i+1)*10+1;
			Like.deleteLike(userName+i, fileName+i1);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("删除运行耗时= "+lastTime+" 毫秒");
	}
	public static void main(String[] args) {
//		queryLikeTest(50);
//		addLikeTest(50);
//		deleteFileTest(50);
	}

}
