package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.User;

public class UserTest {
	public static void main(String args[]){
		long start=System.currentTimeMillis();
		User.createUser("lpshou");
//		User.deleteUser("lisi");
//		User.addUserToGroup("摄影", "lpshou");
		User.deleteUserFromGroup("摄影", "lpshou");
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	}
}
