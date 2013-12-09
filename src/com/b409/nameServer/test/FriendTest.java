package com.b409.nameServer.test;

import com.b409.nameServer.relationship.api.Friend;

public class FriendTest {

	public static void main(String[] args) {
		Friend.makeFriends("wangwu", "lisi");
//		Friend.makeFriends("zhangsan", "wangwu");
//		Friend.unfriend("zhangsan", "wangwu");
		Friend.listAllFriends("zhangsan");
	}

}
