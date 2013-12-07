package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.common.JerseyClient;
import com.b409.nameServer.service.RelationshipInterface;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RelationshipImpl implements RelationshipInterface, Config {
	
	/**
	 * 
	* @Description: 获取图中所有的关系label
	* @return：所有关系的labels
	 */
	public List<String> getAllRelationships(){
		String cypherUri = SERVER_ROOT_URI + "relationship/types";
		String data = JerseyClient.sendToServer(cypherUri, "{}", "get");
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<String>resultList = new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++)
			resultList.add(jsonArray.get(i).toString());
		
		for(int j=0;j<resultList.size();j++)
			System.out.println(resultList.get(j));
		return resultList;
	}
	
	/**
	 * 
	* @Description: 在两个节点之间建立关系，
	* @param nodeId1
	* @param nodeId2
	* @param relationshipType:关系名
	* @param relationshipData:关系属性,json串形式eg："{\"rel\" : \"very good\"}"
	* @return：
	 */
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

	/**
	 * 
	* @Description: 获取一个节点的关系(深度为1）
	* @param nodeUri
	* @param direction:关系方向，有in、out、all;
	* @param labels:具体关系，放入list中
	* @return：所有关系的uri
	 */
	public List<Integer> getRelationshipIdsOfOneNode(int nodeId,String direction,List<String>relationshipTypes){
		String nodeUri = CommonTool.getNodeUriFromNodeId(nodeId);
		nodeUri = nodeUri + "/relationships/"+direction.toLowerCase();
		if(relationshipTypes.size()!=0)
			nodeUri+="/";
		for(int i=0;i<relationshipTypes.size();i++){
			nodeUri+=relationshipTypes.get(i);
			if(i != (relationshipTypes.size()-1))
				nodeUri+="&";
		}
		List<Integer>relationships = new ArrayList<>();
		String data = JerseyClient.sendToServer(nodeUri, "{}", "get");
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String relationship = jsonObject.getString("self");
			int relationshipId = CommonTool.getRelationshipIdFromRelationshipUri(relationship);
			relationships.add(relationshipId);
		}
//		for(int j=0;j<relationships.size();j++)
//			System.out.println(relationships.get(j));
		return relationships;
	}
	/**
	 * 
	* @Description: 获取与一个节点有关系的节点的id
	* @param nodeId
	* @param direction:关系方向，有in、out、all;
	* @param relationshipTypes：具体关系，放入list中
	* @return：所有节点的ids
	 */
	public List<Integer> getNodeIdHaveRelationshipWithOneNode(int nodeId,String direction,List<String>relationshipTypes){
		String nodeUri = CommonTool.getNodeUriFromNodeId(nodeId);
		nodeUri = nodeUri + "/relationships/"+direction.toLowerCase();
		if(relationshipTypes.size()!=0)
			nodeUri+="/";
		for(int i=0;i<relationshipTypes.size();i++){
			nodeUri+=relationshipTypes.get(i);
			if(i != (relationshipTypes.size()-1))
				nodeUri+="&";
		}
		List<Integer>nodeIds = new ArrayList<>();
		String data = JerseyClient.sendToServer(nodeUri, "{}", "get");
//		System.out.println(data);
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String nodeUriString = jsonObject.getString("end");
			nodeIds.add(CommonTool.getNodeIdFromNodeUri(nodeUriString));
		}
//		for(int j=0;j<nodeIds.size();j++)
//			System.out.println(nodeIds.get(j));
		return nodeIds;
	}
	/**
	 * 
	* @Description: 得到两个节点之间的关系，参数为节点的name值
	* @param nameNode1
	* @param nameNode2
	* @return：关系
	 */
	public List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,
			String nameNode2) {

		final String cypherUri = SERVER_ROOT_URI + "cypher";
		String cypherString = "match (n:user) where n.name={name1} match n-[r]-friend where friend.name={name2} return r";
		System.out.println(cypherString);
		String cypherJson = GenerateJson.generateJsonCypherForgetRelationshipTypeBetweenTwoNode(cypherString,
				nameNode1, nameNode2);
		System.out.println(cypherJson);

		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJson, "post");
//		String strResult = response.getEntity(String.class);
		System.out.println(dataResult);
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray array = JSONArray.fromObject(jsonObject.get("data"));
		List<String> strList = new ArrayList<String>();

		for (int i = 0; i < array.size(); i++) {
			String strTemp = array.getString(i);
			strList.add(strTemp);
		}
		return strList;
	}
	
	/**
	 * 
	* @Description: 得到user节点到group节点之间的关系类型
	* @param userName：user名
	* @param groupName：group名
	* @return：关系类型
	 */
	public String getRelationshipTypeFromUserToGroup(String userName, String groupName){
		String cypherUri = SERVER_ROOT_URI + "cypher";
		String cypherString  = "match (n:User) where n.name={name1} match n-[r]->friend where friend.name={name2} return r";
		String cypherJsonString = GenerateJson.generateJsonCypherForgetRelationshipTypeBetweenTwoNode(cypherString, userName, groupName);
//		System.out.println(cypherJsonString);
		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJsonString, "post");
//		System.out.println(dataResult);
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		if(jsonArray.size() == 0){
			//System.out.println("用户"+userName+"到组"+groupName+"不存在关系");
			return "";
		}else{
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String strTempString = jsonObject2.getString("type");
//			System.out.println(strTempString);
			return strTempString;
		}
	}
	//更新关系
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 更新一个关系的属性
	* @param relationshipId
	* @param props：
	* @return：
	 */
	public void updatePropertiesOnRelationship(int relationshipId,String props){
		String relationshipUri = SERVER_ROOT_URI + "relationship/" + relationshipId +"/properties";
		System.out.println(relationshipUri);
		System.out.println(props);
		JerseyClient.sendToServer(relationshipUri, props, "put");
	}
	//---------------------------------------------------------------------------------------------------------------------
	
	//删除关系的属性
	//---------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 删除一个关系上的所有属性
	* @param relationshipId：
	* @return：
	 */
	public void deletePropertiesOnRelationship(int relationshipId){
		String relationshipUri = SERVER_ROOT_URI + "relationship/"+relationshipId + "/properties";
		JerseyClient.sendToServer(relationshipUri, "{}", "delete");
	}
	
	/**
	 * 
	* @Description: 删除一个关系上的某个属性
	* @param relationshipId
	* @param propertyName
	* @return：
	 */
	public void deleteOnePropertyOnRelationship(int relationshipId,String propertyName){
		String relationshipUri = SERVER_ROOT_URI + "relationship/"+relationshipId + "/properties/"+propertyName;
		JerseyClient.sendToServer(relationshipUri, "{}", "delete");
	}
	//----------------------------------------------------------------------------------------------------------------------
	
	
	
	//删除关系
	//----------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 删除一个关系
	* @param relationshipId：
	* @return：
	 */
	public void deleteRelationship(int relationshipId){
		String uriString = SERVER_ROOT_URI + "relationship/"+relationshipId;
		JerseyClient.sendToServer(uriString, "{}", "delete");
	}
	
	/**
	 * 
	* @Description: 删除一个节点的某种关系
	* @param nodeId：节点id
	* @param direction：关系方向，有all、in、out三种
	* @param relationshipTypes：关系类型，有contains、friend、like、create四种
	* @return：
	 */
	public void deleteRelationshipOfNode(int nodeId,String direction, List<String>relationshipTypes){
		List<Integer> relationshipIds = getRelationshipIdsOfOneNode(nodeId, direction, relationshipTypes);
		for(int i=0;i<relationshipIds.size();i++){
			deleteRelationship(relationshipIds.get(i));
		}
	}
	//---------------------------------------------------------------------------------------
	

}
