package com.b409.nameServer.common;

public class CommonTool {
	public static int getNodeIdFromNodeUri(String uri){
		int i = uri.lastIndexOf("/");
		String string = uri.substring(i+1);
		System.out.println(string);
		return Integer.valueOf(string);
	}
}
