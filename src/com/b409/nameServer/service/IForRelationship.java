package com.b409.nameServer.service;

import java.util.List;


public interface IForRelationship {
	//在两个节点之间建立关系，

	
	
	
	//得到两个节点之间的关系，参数为节点的name值
	public  List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,String nameNode2);

}
