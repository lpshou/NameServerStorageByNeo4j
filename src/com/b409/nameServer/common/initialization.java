package com.b409.nameServer.common;

import java.net.URI;
import java.net.URISyntaxException;

import com.b409.nameServer.serviceImpl.baseOperation;

public class initialization {
	public static void ini() throws URISyntaxException
	{
		baseOperation operation = new baseOperation();

		//数据库是否正常运行
		System.out.println("DatabaseIsRunning: "+operation.databaseIsRunning());
		
		//添加节点
		URI node1 = operation.createNode();
		URI node2 = operation.createNode();
		URI node3 = operation.createNode();
		URI node4 = operation.createNode();
		URI node5 = operation.createNode();
		URI node6 = operation.createNode();
		URI node7 = operation.createNode();
		URI node8 = operation.createNode();
		URI node9 = operation.createNode();
		URI node10 = operation.createNode();
		URI node11 = operation.createNode();
		URI node12 = operation.createNode();
		URI node13 = operation.createNode();
		URI node14 = operation.createNode();
		URI node15 = operation.createNode();
		
		//为节点添加属性
		operation.addPropertyToNode(node1, "name", "b409");
		operation.addPropertyToNode(node1, "type", "group");
		operation.addPropertyToNode(node2, "name", "liupeng");
		operation.addPropertyToNode(node2, "type", "user");
		operation.addPropertyToNode(node3, "name", "huang");
		operation.addPropertyToNode(node3, "type", "user");
		operation.addPropertyToNode(node4, "name", "zhang");
		operation.addPropertyToNode(node4, "type", "user");
		operation.addPropertyToNode(node5, "name", "找工作");
		operation.addPropertyToNode(node5, "type", "directory");
		operation.addPropertyToNode(node6, "name", "电影");
		operation.addPropertyToNode(node6, "type", "directory");
		operation.addPropertyToNode(node7, "name", "游戏");
		operation.addPropertyToNode(node7, "type", "directory");
		operation.addPropertyToNode(node8, "name", "看电影");
		operation.addPropertyToNode(node8, "type", "directory");
		operation.addPropertyToNode(node9, "name", "摄影");
		operation.addPropertyToNode(node9, "type", "directory");
		operation.addPropertyToNode(node10, "name", "个人简历.doc");
		operation.addPropertyToNode(node10, "type", "file");
		operation.addPropertyToNode(node10, "id", "");
		operation.addPropertyToNode(node11, "name", "tab笔试题.pdf");
		operation.addPropertyToNode(node11, "type", "file");
		operation.addPropertyToNode(node11, "id", "");
		operation.addPropertyToNode(node12, "name", "让子弹飞.rmvb");
		operation.addPropertyToNode(node12, "type", "file");
		operation.addPropertyToNode(node12, "id", "");
		operation.addPropertyToNode(node13, "name", "罗马假日.mkv");
		operation.addPropertyToNode(node13, "type", "file");
		operation.addPropertyToNode(node13, "id", "");
		operation.addPropertyToNode(node14, "name", "三峡风景.ppt");
		operation.addPropertyToNode(node14, "type", "file");
		operation.addPropertyToNode(node14, "id", "");
		operation.addPropertyToNode(node15, "name", "华科最美照片.jpg");
		operation.addPropertyToNode(node15, "type", "file");
		operation.addPropertyToNode(node15, "id", "");
		
		//在节点之间建立关系
		URI rel1 = operation.addRelationshipBetweenTwoNodes(node1, node2, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel2 = operation.addRelationshipBetweenTwoNodes(node1, node3, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel3 = operation.addRelationshipBetweenTwoNodes(node1, node4, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel4 = operation.addRelationshipBetweenTwoNodes(node2, node5, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel5 = operation.addRelationshipBetweenTwoNodes(node2, node6, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel6 = operation.addRelationshipBetweenTwoNodes(node2, node7, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel7 = operation.addRelationshipBetweenTwoNodes(node4, node8, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel8 = operation.addRelationshipBetweenTwoNodes(node4, node9, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel9 = operation.addRelationshipBetweenTwoNodes(node5, node10, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel10 = operation.addRelationshipBetweenTwoNodes(node5, node11, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel11 = operation.addRelationshipBetweenTwoNodes(node8, node12, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel12 = operation.addRelationshipBetweenTwoNodes(node8, node13, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel13 = operation.addRelationshipBetweenTwoNodes(node9, node14, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
		URI rel14 = operation.addRelationshipBetweenTwoNodes(node9, node15, "contains","{ \"from\" : \"2011\", \"until\" : \"2014\" }" );
	}

}
