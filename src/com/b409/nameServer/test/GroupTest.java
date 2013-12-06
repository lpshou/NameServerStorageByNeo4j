package com.b409.nameServer.test;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.namespace.api.Group;

public class GroupTest {

	public static void main(String[] args) {
		Group group = new Group();
//		group.listAllGroup();
		group.createGroup("计算机1103");
		group.createGroup("计算机1104");
	}

}
