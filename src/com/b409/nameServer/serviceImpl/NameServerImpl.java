package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.common.JerseyClient;
import com.b409.nameServer.service.IForNameServer;

public class NameServerImpl implements IForNameServer,Config {
	
	//创建一个节点，属性值props为json串eg：{"a":"b"},返回值为创建的节点的uri
	public List<String> createNodeWithProperties(String label, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString="";
		switch(label.toUpperCase()){
		case "GROUP":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("group",props);break;
		case "USER":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("user",props);break;
		case "DIRECTORY":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("directory",props);break;
		case "FILE":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("file",props);break;
		}
		
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
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
	
	//设置节点属性
	public void setAllPropertiesOnNode(int nodeId, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=GenerateJson.jenerateJsonForSetProperties(nodeId, props);
		System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		System.out.println(data);
	}
	public void setAllPropertiesOnNode(String nodeUri, String props){
		int nodeId = CommonTool.getNodeIdFromNodeUri(nodeUri);
		setAllPropertiesOnNode(nodeId, props);
	}
	
	//更新节点某个属性值,其他属性值不变
	public void updateOnePropertyOnNode(String nodeUri,String key,String value){
		String uri = nodeUri+"/properties/"+key;
		String dataString = JerseyClient.sendToServer(uri, value, "put");
	}
	
	//获取节点信息（包括属性、id、uri等等）
	public String getMessageOfNode(int nodeId){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=GenerateJson.generateJsonForGetNodeProperties(nodeId);
		System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		
		System.out.println(data);
		return data;
	}
	
	//获取节点有哪些属性
	public List<String> getPropertiesOfKeyOfNode(int nodeId){
		
		List<String> resultList = new ArrayList<>();
		String jsonString = getPropertiesOfNode(nodeId);
		Map<String, String> map =CommonTool.parserFromJsonToMap(jsonString);
		Set<Map.Entry<String, String>> sets = map.entrySet();
		for(Map.Entry<String, String> entry:sets){
			String strTemp = entry.getKey();
			resultList.add(strTemp);
		}
		for(int i=0;i<resultList.size();i++)
			System.out.println(resultList.get(i));
		return resultList;
	}
	//获取节点具体属性，json串形式
	public String getPropertiesOfNode(int nodeId){
		String dataString = getMessageOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String data = jsonObject.getString("data");
		JSONArray array = JSONArray.fromObject(data);
		String data1 = array.getString(0);
		JSONArray array2 = JSONArray.fromObject(data1);
		String data2 = array2.getString(0);
		JSONObject jsonObject2 = JSONObject.fromObject(data2);
		String result = jsonObject2.getString("data");
		//System.out.println(result);		
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
	
	
	//删除一个节点
	public void deleteNode(String nodeUri){
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");	
		System.out.println(data);
	}
	public void deleteNode(int nodeId){
		String data = SERVER_ROOT_URI+"node/"+nodeId;
		System.out.println("haha"+data);
		deleteNode(data);
	}

}
