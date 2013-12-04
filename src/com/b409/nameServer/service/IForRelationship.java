package com.b409.nameServer.service;

import java.util.List;


public interface IForRelationship {
	//在两个节点之间建立关系，
	public void createRelationshipBetweenTwoNode(String nodeUri1, String nodeUri2, 
			String relationshipType, String relationshipData);
	public void createRelationshipBetweenTwoNode(int nodeId1,int nodeId2, 
			String relationshipType, String relationshipData);
	
	//获取一个节点的关系(深度为1）
	//direction:关系方向，有in、out、all;
	//labels:具体关系
	public List<String> getRelationshipOfNode(String nodeUri,String direction,List<String>labels);
	
	//获得图中所有关系
	public List<String> getAllRelationships();
	
	
	//得到两个节点之间的关系，参数为节点的name值
	public  List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,String nameNode2);

}
