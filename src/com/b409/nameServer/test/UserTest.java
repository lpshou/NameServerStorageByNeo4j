package com.b409.nameServer.test;

import com.b409.nameServer.namespace.api.User;

public class UserTest {
	public static void main(String args[]){
		User user = new User();
//		user.createUser("刘鹏");
//		user.createUser("huang");
//		user.createUser("zhang");
		user.deleteUser("huang");
		
	}

}
