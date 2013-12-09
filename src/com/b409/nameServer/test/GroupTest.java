package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.Group;

public class GroupTest {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();

//		Group group = new Group();
//		for(int i=0;i<100;i++)
//			group.createGroup("计算机1101"+i, "huang");
//		group.createGroup("计算机1105", "zhangsan");
//		group.createGroup("摄影组", "zhangsan");
//		group.createGroup("篮球社", "zhangsan");
		Group.listAllUsersInGroup("计算机1105");
//		group.addUserToGroup("计算机1105", "wangwu");
//		group.addUserToGroup("计算机1105", "lisi");
//		group.deleteUserFromGroup("计算机1101", "wangwu");
		
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}

}
