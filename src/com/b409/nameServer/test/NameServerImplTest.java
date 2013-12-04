package com.b409.nameServer.test;

import java.net.URISyntaxException;

import com.b409.nameServer.common.commonTool;
import com.b409.nameServer.serviceImpl.operationForNameServerImpl;

public class NameServerImplTest {
	public static void main(String[] args) throws URISyntaxException {
		operationForNameServerImpl nameServerImpl = new operationForNameServerImpl();
		//创建多个节点（包括一个）
		//nameServerImpl.createNodeWithProperties("user", "[{},{}]");
		//nameServerImpl.createNodeWithProperties("user", "{}");
		//nameServerImpl.setAllPropertiesOnNode(280, "{\"lab.\" : \" b409\"}");
		//nameServerImpl.setAllPropertiesOnNode("http://192.168.0.187:7474/db/data/node/280", "{\"labs\" : \" b4090\"}");
		//nameServerImpl.getMessageOfNode(280);
		//nameServerImpl.getPropertieOfNode(280);
		nameServerImpl.getUriOfNode(280);
		

	}
}
