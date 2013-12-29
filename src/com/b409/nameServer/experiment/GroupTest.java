package com.b409.nameServer.experiment;

import com.b409.nameServer.namespace.api.Group;
import com.b409.nameServer.namespace.api.User;

public class GroupTest {
	//查询测试
	static void queryGroupTest(String groupName,int num){
		long start=System.currentTimeMillis();
		for(int i=0;i<num;i++){
			Group.queryGroupProperty(groupName);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("查询运行耗时= "+lastTime+" 毫秒");
	}
	//更新测试
	static void updateGroupTest(String groupName,String props,int num){
		long start=System.currentTimeMillis();
		for(int i=0;i<num;i++){
			Group.updateGroupProperty(groupName,props);
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("更新运行耗时= "+lastTime+" 毫秒");
	}
	//添加测试
	static void addGroupTest(int num){
		long start=System.currentTimeMillis();
		//群组名加上时间防止重名
		String groupName="groupTest";
		for(int i=0;i<num;i++){
			Group.createGroup(groupName+i, "user");
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("添加运行耗时= "+lastTime+" 毫秒");
	}
	//删除测试
	static void deleteGroupTest(int num){
		long start=System.currentTimeMillis();
		//群组名加上时间防止重名
		String groupName="groupTest";
		for(int i=0;i<num;i++){
			Group.deleteGroup(groupName+i, "user");
		}
		long time = System.currentTimeMillis() - start;
		long lastTime = time/num;
		System.out.println("删除运行耗时= "+lastTime+" 毫秒");
	}

	public static void main(String[] args) {
		//查询测试
//		queryGroupTest("group0",1000);

		//更新属性测试
//		updateGroupTest("group0","",1000);
		
		//添加测试
//		addGroupTest(200);
		
		//删除测试
//		deleteGroupTest(200);

	}

}
