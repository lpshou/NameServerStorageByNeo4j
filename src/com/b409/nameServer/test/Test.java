package com.b409.nameServer.test;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.b409.nameServer.serviceImpl.baseOperationImpl;
import com.b409.nameServer.serviceImpl.operationForRelationshipImpl;



public class Test {

	public static void main(String[] args) throws URISyntaxException {
//		System.out.println("hah");
		baseOperationImpl operation = new baseOperationImpl();

//		operation.beginAndCommitTransaction("create (n) return id(n)");
		operationForRelationshipImpl relationship = new operationForRelationshipImpl();
		relationship.getRelationshipTypeBetweenTwoNode("liupeng", "huangshaojian");
		
		//initialization.ini();
		
//		baseOperation operation = new baseOperation();
		
//		//数据库是否正常运行
//		System.out.println("DatabaseIsRunning: "+operation.databaseIsRunning());
//		
//		//增加节点
//		URI node = operation.createNode();
//		//为节点添加属性
//		operation.addPropertyToNode(node, "school", "华科");
		
//		URI node2 = operation.createNode();
//		operation.addPropertyToNode(node2, "lab.", "b409");
		
//		String nodeUriStart = "http://192.168.0.187:7474/db/data/node/0";
//		URI startNode1 = new URI(nodeUriStart);
//		//在两个节点之间建立关系,并为关系添加属性
//		URI rel1 = operation.addRelationshipBetweenTwoNodes(node, startNode1, "contains",
//				"{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		
//		//为关系添加属性（会覆盖之前的属性）
//		operation.addPropertyToRelationship(rel1, "like", "5");
		

		//查询与某个节点有关系的其他节点
//		String nodeUri = "http://192.168.0.187:7474/db/data/node/0";
//		URI startNode = new URI(nodeUri);
//		List<URI> uris = operation.findNodeWithRelationshipInDepth(startNode,"contains",1,"in");
//		
//		for(int i=0;i<uris.size();i++)
//			System.out.println(uris.get(i));

		
	}

}
