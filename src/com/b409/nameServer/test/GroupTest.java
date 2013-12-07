package com.b409.nameServer.test;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.namespace.api.Group;

public class GroupTest {

	public static void main(String[] args) {
		long start=System.currentTimeMillis();

		Group group = new Group();
		for(int i=0;i<100;i++)
			group.createGroup("计算机1101"+i, "zhangsan");
//		group.createGroup("labb409", "zhangsan");
//		group.listAllUsersInGroup("计算机1105");
//		group.addUserToGroup("计算机1101", "wangwu");
//		group.deleteUserFromGroup("计算机1101", "wangwu");
		
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}

}
