package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.common.JerseyClient;
import com.b409.nameServer.service.IForRelationship;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RelationshipImpl implements IForRelationship, Config {
	//获得图中所有关系
	public List<String> getAllRelationships(){
		String cypherUri = SERVER_ROOT_URI + "relationship/types";
		String data = JerseyClient.sendToServer(cypherUri, "{}", "get");
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<String>resultList = new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++)
			resultList.add(jsonArray.get(i).toString());
		
		return resultList;
//		
//		for(int j=0;j<resultList.size();j++)
//			System.out.println(resultList.get(j));
//			
//		System.out.println(data);
		
	}
	
	// 在两个节点之间建立关系，
	public void createRelationshipBetweenTwoNode(String nodeUri1,
			String nodeUri2, String relationshipType, String relationshipData){
		
		String cypherUri = nodeUri1+"/relationships";
		String cypherJson = GenerateJson.generateJsonForCreateRelationshipBetweenTwoNodes(nodeUri2, relationshipType, relationshipData);
		JerseyClient.sendToServer(cypherUri, cypherJson, "post");
	}
	public void createRelationshipBetweenTwoNode(int nodeId1,int nodeId2, 
			String relationshipType, String relationshipData){
		
		String nodeUri1 = SERVER_ROOT_URI+"node/"+nodeId1;
		String nodeUri2 = SERVER_ROOT_URI+"node/"+nodeId2;
		createRelationshipBetweenTwoNode(nodeUri1, nodeUri2, relationshipType, relationshipData);
	}

	//获取一个节点的所有关系
	//direction:关系方向，有in、out、all;
	//labels:具体关系
	public List<String> getRelationshipOfNode(String nodeUri,String direction,List<String>labels){
		nodeUri = nodeUri + "/relationships/"+direction.toLowerCase();
		if(labels.size()!=0)
			nodeUri+="/";
		for(int i=0;i<labels.size();i++){
			nodeUri+=labels.get(i);
			if(i != (labels.size()-1))
				nodeUri+="&";
			
		}
		//System.out.println(nodeUri);
		List<String>relationships = new ArrayList<>();
		String data = JerseyClient.sendToServer(nodeUri, "{}", "get");
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String relationship = jsonObject.getString("self");
			relationships.add(relationship);
		}
//		for(int j=0;j<relationships.size();j++)
//			System.out.println(relationships.get(j));
			
		return relationships;
	}
	
	public List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,
			String nameNode2) {

		final String cypherUri = SERVER_ROOT_URI + "cypher";

		String cypherString = "match (n:user) where n.name={name1} match n-[r]-friend where friend.name={name2} return r";

		System.out.println(cypherString);
		String cypherJson = GenerateJson.generateJsonCypher(cypherString,
				nameNode1, nameNode2);
		System.out.println(cypherJson);

		WebResource resource = Client.create().resource(cypherUri);

		// POST JSON to the relationships URI
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(cypherJson)
				.post(ClientResponse.class);

		// final URI location = response.getLocation();
		System.out.println(String.format("POST to [%s], status code [%d]",
				cypherUri, response.getStatus()));
		String strResult = response.getEntity(String.class);
		System.out.println(strResult);
		JSONObject jsonObject = JSONObject.fromObject(strResult);
		JSONArray array = JSONArray.fromObject(jsonObject.get("data"));
		List<String> strList = new ArrayList<String>();

		for (int i = 0; i < array.size(); i++) {
			String strTemp = array.getString(i);
			strList.add(strTemp);
		}

		response.close();
		return strList;

	}

}
