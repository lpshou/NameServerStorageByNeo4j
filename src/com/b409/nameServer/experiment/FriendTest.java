package com.b409.nameServer.experiment;

import com.b409.nameServer.namespace.api.File;
import com.b409.nameServer.relationship.api.Friend;

public class FriendTest {
		//查询测试
		static void queryFriendTest(int num){
			long start=System.currentTimeMillis();
			String friendName = "user";
			for(int i=0;i<num-2;i++){
				int i1=i+1;
				int i2=i+2;
				Friend.queryFriends(friendName+i, friendName+i1);
				Friend.queryFriends(friendName+i, friendName+i2);
			}
			long time = System.currentTimeMillis() - start;
			long lastTime = time/(num*2);
			System.out.println("查询运行耗时= "+lastTime+" 毫秒");
		}
		
		//添加测试
		static void addFileTest(int num){
			long start=System.currentTimeMillis();
			String friendName = "user";
			for(int i=0;i<num-3;i++){
				int i3=i+3;
				Friend.makeFriends(friendName+i, friendName+i3);
			}
			long time = System.currentTimeMillis() - start;
			long lastTime = time/num;
			System.out.println("添加运行耗时= "+lastTime+" 毫秒");
		}
		//删除测试
		static void deleteFileTest(int num){
			long start=System.currentTimeMillis();
			String friendName = "user";
			for(int i=0;i<num-3;i++){
				int i3=i+3;
				Friend.unfriend(friendName+i, friendName+i3);
			}
			long time = System.currentTimeMillis() - start;
			long lastTime = time/num;
			System.out.println("删除运行耗时= "+lastTime+" 毫秒");
		}
	public static void main(String[] args) {
		//相当于100次查询，
//		queryFriendTest(50);
//		addFileTest(50);
//		deleteFileTest(50);
	}

}
