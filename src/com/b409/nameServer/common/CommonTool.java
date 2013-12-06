package com.b409.nameServer.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommonTool implements Config{
	
	//获取当前时间
	public static String getTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	//由关系的uri得到节点id
	public static int getRelationshipIdFromRelationshipUri(String relationshipUri){
		int i = relationshipUri.lastIndexOf("/");
		String string = relationshipUri.substring(i+1);
		return Integer.parseInt(string);
	}
	
	//由关系的id得到关系的uri
	public static String getRelationshipUriFromRelationshipId(int relationshipId){
		String relationshipUri = SERVER_ROOT_URI + "relationship/" + relationshipId;
		return relationshipUri;
	}
	
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
