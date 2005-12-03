package com.b409.nameServer.common;

import java.net.URI;

public class generateJson {
	
	public static String generateJsonCypher(String cypherString,String nameNode1,String nameNode2){
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" : \");");
		sb.append(cypherString);
		sb.append("\", \"params\" : { \"name1\" : \" );");
		sb.append(nameNode1);
		sb.append("\", \"name2\" : \");");
		sb.append("\" } }");
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
