package com.b409.nameServer.test;

import java.net.URISyntaxException;

import com.b409.nameServer.serviceImpl.operationForNameServerImpl;

public class NameServerImplTest {
	public static void main(String[] args) throws URISyntaxException {
		operationForNameServerImpl nameServerImpl = new operationForNameServerImpl();
		//创建多个节点（包括一个）
		//nameServerImpl.createNodeWithProperties("user", "[{},{}]");
		//nameServerImpl.createNodeWithProperties("user", "{}");
		nameServerImpl.setAllPropertiesOnNode(280, "{\"lab.\" : \" b409\"}");
	}
}
