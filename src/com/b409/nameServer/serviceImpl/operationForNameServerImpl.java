package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

}
