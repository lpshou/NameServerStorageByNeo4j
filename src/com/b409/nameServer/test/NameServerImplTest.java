package com.b409.nameServer.test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;





import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NameServerImpl;

public class NameServerImplTest {
	public static void main(String[] args) throws URISyntaxException {

		NameServerImpl nameServerImpl = new NameServerImpl();
		//创建多个节点（包括一个）
		//nameServerImpl.createNodeWithProperties("user", "[{},{}]");
		//nameServerImpl.createNodeWithProperties("user", "{}");
		//nameServerImpl.setAllPropertiesOnNode(283, "{\"lab.\" : \" b409\", \"test\" : \"hh\"}");
		//nameServerImpl.setAllPropertiesOnNode("http://192.168.0.187:7474/db/data/node/283", "{\"foo\" : \" b4090\"}");
		//nameServerImpl.getMessageOfNode(280);
		//nameServerImpl.getPropertieOfNode(280);
		//nameServerImpl.getUriOfNode(280);
		//nameServerImpl.deleteNode(276);
//		String uriString = "http://192.168.0.187:7474/db/data/node/283";
//		nameServerImpl.updateOnePropertyOnNode(uriString, "test", "bar");
//		
		nameServerImpl.getPropertiesOfKeyOfNode(283);
	}
}
