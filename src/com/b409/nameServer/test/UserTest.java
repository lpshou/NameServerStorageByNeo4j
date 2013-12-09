package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.User;

public class UserTest {
	public static void main(String args[]){
		User user = new User();
		user.createUser("zhangsan");
		user.createUser("lisi");
		user.createUser("wangwu");
//		user.deleteUser("zhangsan");
//		user.deleteUser("lisi");
	}
}
