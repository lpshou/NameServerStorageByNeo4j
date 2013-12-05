package com.b409.nameServer.common;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommonTool implements Config{
	//由节点的uri得到节点id
	public static int getNodeIdFromNodeUri(String uri){
		int i = uri.lastIndexOf("/");
		String string = uri.substring(i+1);
//		System.out.println(string);
		return Integer.valueOf(string);
	}
	
	//由节点id得到节点的uri
	public static String getNodeUriFromNodeId(int nodeId){
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId;
		return nodeUri;
	}
	
	//将一个json串转化为map
	public static Map<String, String> parserFromJsonToMap(String jsonString){
		GsonBuilder gbBuilder = new GsonBuilder();
		Gson gson = gbBuilder.create();
		Map<String, String> map = gson.fromJson(jsonString, new TypeToken<Map<String, String>>(){}.getType());
		return map;
	}
}
