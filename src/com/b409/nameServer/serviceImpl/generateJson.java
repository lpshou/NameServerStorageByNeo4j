package com.b409.nameServer.serviceImpl;

public class generateJson {
	static String generateJsonTransaction(String transactionUri) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"statements\" : [{");
		sb.append("\"statement\" : \"");
		sb.append(transactionUri.toString());
		sb.append("\"}]}");
		
		return sb.toString();
	}
}
