package com.b409.nameServer.common;

import java.net.URI;

public class GenerateJson {
	
	//为一个label创建一个index
	public static String generateJsonForCreateIndexOnLabel(String indexName){
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"property_keys\" : [ \"");
		sb.append(indexName);
		sb.append("\"] }");

		
		return sb.toString();	
	}
	// 建立两个节点之间关系的json串
	public static String generateJsonForCreateRelationshipBetweenTwoNodes(String nodeUri, String relationshipType,String relationshipData){
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"to\" : \"");
		sb.append(nodeUri);
		sb.append("\", \"type\" : \"");
		sb.append(relationshipType);
		sb.append("\", \"data\" : ");
		sb.append(relationshipData);
		sb.append("}");
		
		return sb.toString();	
		
	}

	// 获取节点属性的json串
	public static String generateJsonForGetNodeProperties(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" :\"");
		sb.append("start n = node( {id} ) return n\", ");
		sb.append("\"params\" : {");
		sb.append("\"id\" : ");
		sb.append(id);
		sb.append("}}");

		return sb.toString();
	}

	public static String jenerateJsonForSetProperties(int id, String props) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" :\"");
		sb.append("start n=node( { id } ) set n= { props } return n \",");
		sb.append("\"params\" : {");
		sb.append("\"id\" : ");
		sb.append(id);
		sb.append(",");
		sb.append("\"props\" : ");
		sb.append(props);
		sb.append("}}");
		return sb.toString();
	}

	public static String jenerateJsonForCreateNodeWithProperties(String type,
			String props) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" :\"");
		switch (type.toUpperCase()) {
		case "GROUP": {
			sb.append("CREATE (n:Group { props } ) RETURN n\", ");
			break;
		}
		case "USER": {
			sb.append("CREATE (n:User { props } ) RETURN n\", ");
			break;
		}
		case "DIRECTORY": {
			sb.append("CREATE (n:Directory { props } ) RETURN n\", ");
			break;
		}
		case "FILE": {
			sb.append("CREATE (n:File { props } ) RETURN n\", ");
			break;
		}
		}
		sb.append("\"params\" : {");
		sb.append("\"props\" : ");
		sb.append(props);
		sb.append("}}");
		return sb.toString();
	}

	//获得两个节点之间关系
	public static String generateJsonCypherForgetRelationshipTypeBetweenTwoNode(String cypherString,
			String nameNode1, String nameNode2) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" : \"");
		sb.append(cypherString);
		sb.append("\", \"params\" : { \"name1\" : \"");
		sb.append(nameNode1);
		sb.append("\", \"name2\" : \"");
		sb.append(nameNode2);
		sb.append("\" } }");
		return sb.toString();
	}

	//获得两个节点之间关系的id
	public static String generateJsonForgetRelationshipIdBetweenTwoNodes(String cypherString, int nodeId1, int nodeId2){
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" : \"");
		sb.append(cypherString);
		sb.append("\", \"params\" : { \"nodeId1\" : ");
		sb.append(nodeId1);
		sb.append(", \"nodeId2\" : ");
		sb.append(nodeId2);
		sb.append(" } }");
		return sb.toString();
	}
	public static String generateJsonTransaction(String transactionUri) {

		StringBuilder sb = new StringBuilder();
		sb.append("{ \"statements\" : [{");
		sb.append("\"statement\" : \"");
		sb.append(transactionUri.toString());
		sb.append("\"}]}");

		return sb.toString();
	}

	public static String generateJsonRelationship(URI endNode,
			String relationshipType, String... jsonAttributes) {

		StringBuilder sb = new StringBuilder();
		sb.append("{ \"to\" : \"");
		sb.append(endNode.toString());
		sb.append("\", ");

		sb.append("\"type\" : \"");
		sb.append(relationshipType);
		if (jsonAttributes == null || jsonAttributes.length < 1) {
			sb.append("\"");
		} else {
			sb.append("\", \"data\" : ");
			for (int i = 0; i < jsonAttributes.length; i++) {
				sb.append(jsonAttributes[i]);
				if (i < jsonAttributes.length - 1) { // Miss off the final comma
					sb.append(", ");
				}
			}
		}

		sb.append(" }");
		return sb.toString();
	}
}
