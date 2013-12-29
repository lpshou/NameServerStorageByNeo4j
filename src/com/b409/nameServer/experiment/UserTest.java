package com.b409.nameServer.experiment;

import com.b409.nameServer.namespace.api.Group;
import com.b409.nameServer.namespace.api.User;

public class UserTest {

	//查询测试
	static void queryUserTest(String userName,int num){
		long start=System.currentTimeMillis();
		
		for(int i=0;i<num;i++){
			User.queryUserProperty(userName+i);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("查询运行耗时= "+lastTime+" 毫秒");
	}
	//更新测试
	static void updateUserTest(String userName,String props,int num){
		long start=System.currentTimeMillis();
		for(int i=0;i<num;i++){
			User.updateUserProperty(userName, props);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("更新运行耗时= "+lastTime+" 毫秒");
	}
	//添加测试
	static void addUserTest(int num){
		long start=System.currentTimeMillis();
		String userName="userTest";
		for(int i=0;i<num;i++){
			User.createUser(userName+i);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("添加运行耗时= "+lastTime+" 毫秒");
	}
	//删除测试
	static void deleteUserTest(int num){
		long start=System.currentTimeMillis();
		//群组名加上时间防止重名
		String userName="userTest";
		for(int i=0;i<num;i++){
			User.deleteUser(userName+i);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("删除运行耗时= "+lastTime+" 毫秒");
	}
	public static void main(String[] args) {
		//查询测试
		queryUserTest("user", 50);

		//更新属性测试
//		updateUserTest("user","",50);
		
		//添加测试
//		addUserTest(10);
		
		//删除测试
//		deleteUserTest(10);
	}

}
