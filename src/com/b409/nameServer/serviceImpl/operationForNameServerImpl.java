package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.commonTool;
import com.b409.nameServer.common.config;
import com.b409.nameServer.common.generateJson;
import com.b409.nameServer.common.jerseyClient;
import com.b409.nameServer.service.IOperationForNameServer;

public class operationForNameServerImpl implements IOperationForNameServer,config {
	
	public List<String> createNodeWithProperties(String label, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString="";
		switch(label.toUpperCase()){
		case "GROUP":jsonString = generateJson.jenerateJsonForCreateNodeWithProperties("group",props);break;
		case "USER":jsonString = generateJson.jenerateJsonForCreateNodeWithProperties("user",props);break;
		case "DIRECTORY":jsonString = generateJson.jenerateJsonForCreateNodeWithProperties("directory",props);break;
		case "FILE":jsonString = generateJson.jenerateJsonForCreateNodeWithProperties("file",props);break;
		}
		
		String data = jerseyClient.sendToServer(uri, jsonString, "post");
		//System.out.println(data);
		
		//解析返回值，获得新创建的节点的URI
		List<String>uris = new ArrayList<String>();
		
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		//System.out.println(jsonArray);

	
		for(int i=0;i<jsonArray.size();i++){
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i));
			//System.out.println("jsonArray2:"+jsonArray2);
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String uriTemp = jsonObject2.getString("self");
			uris.add(uriTemp);
		}
		
		for(int j=0;j<uris.size();j++)
			System.out.println(uris.get(j));
		return uris;
	}
	
	
	public void setAllPropertiesOnNode(int nodeId, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=generateJson.jenerateJsonForSetProperties(nodeId, props);
		System.out.println(jsonString);
		String data = jerseyClient.sendToServer(uri, jsonString, "post");
		System.out.println(data);
	}
	public void setAllPropertiesOnNode(String nodeUri, String props){
		int nodeId = commonTool.getNodeIdFromNodeUri(nodeUri);
		setAllPropertiesOnNode(nodeId, props);
	}
	
	//获取节点信息（包括属性、id、uri等等）
	public String getMessageOfNode(int nodeId){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=generateJson.generateJsonForGetNodeProperties(nodeId);
		System.out.println(jsonString);
		String data = jerseyClient.sendToServer(uri, jsonString, "post");
		
		System.out.println(data);
		return data;
	}
	
	//获取节点属性
	public String getPropertieOfNode(int nodeId){
		String dataString = getMessageOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String data = jsonObject.getString("data");
		JSONArray array = JSONArray.fromObject(data);
		String data1 = array.getString(0);
		JSONArray array2 = JSONArray.fromObject(data1);
		String data2 = array2.getString(0);
		JSONObject jsonObject2 = JSONObject.fromObject(data2);
		String result = jsonObject2.getString("data");
		System.out.println(result);		
		return result;
		
	}
	//获取节点URI
	public String getUriOfNode(int nodeId){
		String dataString = getMessageOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String data = jsonObject.getString("data");
		JSONArray array = JSONArray.fromObject(data);
		String data1 = array.getString(0);
		JSONArray array2 = JSONArray.fromObject(data1);
		String data2 = array2.getString(0);
		JSONObject jsonObject2 = JSONObject.fromObject(data2);
		String result = jsonObject2.getString("self");
		System.out.println(result);		
		return result;
	}

}
