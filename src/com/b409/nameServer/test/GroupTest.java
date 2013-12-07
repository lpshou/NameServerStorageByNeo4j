package com.b409.nameServer.test;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.namespace.api.Group;

public class GroupTest {

	public static void main(String[] args) {
		Group group = new Group();
//		group.listAllGroup();
//		group.createGroup("计算机1103");
//		group.createGroup("计算机1104");
//		group.deleteGroup("liu");
//		group.deleteGroup("计算机1104");
//		group.createGroup("计算机1105", "zhang");
		group.deleteGroup("计算机1105", "zhang");
		group.createGroup("计算机1105", "zhang");
//		group.addUserToGroup("计算机1105", "zhang");
		group.listAllUsersInGroup("计算机1105");
	}

}
