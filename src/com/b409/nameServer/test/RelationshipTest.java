package com.b409.nameServer.test;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class RelationshipTest {
	public static void main(String args[]){
		RelationshipImpl relationshipImpl = new RelationshipImpl();
//		String nodeUri1 = "http://192.168.0.187:7474/db/data/node/282";
		String nodeUri2 = "http://192.168.0.187:7474/db/data/node/282";
//		String relationshipType="home";
//		String relationshipData="{\"rel\" : \"very good\"}";
//		//String relationshipData="{}";
//		relationshipImpl.createRelationshipBetweenTwoNode(nodeUri1, nodeUri2, relationshipType, relationshipData);
//	
		List<String>labels = new ArrayList<String>();
		labels.add("contains");
		labels.add("friend");
//		relationshipImpl.getRelationshipIdFromGroupToUser("zhangsan", "计算机1101");
//		relationshipImpl.getNodeIdHaveRelationshipWithOneNode(10, "all", labels);
//		relationshipImpl.getRelationshipOfNode(282, "out", labels);
//		relationshipImpl.getAllRelationships();

//		relationshipImpl.getRelationshipTypeBetweenTwoNodes("计算机1105","zhangsan");
		relationshipImpl.getRelationshipIdBetweenTwoNodes(115, 118);
//		relationshipImpl.updatePropertiesOnRelationship(290, "{\"rel\":\"test\"}");
//		relationshipImpl.deletePropertiesOnRelationship(290);
//		relationshipImpl.deleteOnePropertyOnRelationship(291, "rel");
//		relationshipImpl.getRelationshipTypeFromUserToGroup("zhang", "计算机1104");
//		relationshipImpl.createRelationshipBetweenTwoNode(8, 11, "contains", "{}");
		
	
	
	
	
	}

}
