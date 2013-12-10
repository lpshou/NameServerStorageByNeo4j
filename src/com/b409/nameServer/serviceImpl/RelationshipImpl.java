package com.b409.nameServer.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.common.JerseyClient;
import com.b409.nameServer.service.RelationshipInterface;


public class RelationshipImpl implements RelationshipInterface, Config {
	
	
	//创建关系
	//---------------------------------------------------------------------------------------------------------------------
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

	//-----------------------------------------------------------------------------------------------------------------------
	
	
	
	//查询关系
	//-----------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 获取一个节点的关系id
	* @param nodeId:节点id
	* @param direction:关系方向，有in、out、all;
	* @param relationshipTypes:具体关系，放入list中
	* @return：所有关系ids
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
	public List<Integer> getNodeIdsHaveRelationshipWithOneNode(int nodeId,String direction,List<String>relationshipTypes){
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
			String nodeUriString="";
			if(direction.equals("out")){
				nodeUriString = jsonObject.getString("end");
			}else if(direction.equals("in")){
				nodeUriString = jsonObject.getString("start");
			}else{
				String nodeUriTemp = CommonTool.getNodeUriFromNodeId(nodeId);
				if(nodeUriTemp.equals(jsonObject.getString("end"))){
					nodeUriString = jsonObject.getString("start");
				}else{
					nodeUriString = jsonObject.getString("end");
				}
			}
			nodeIds.add(CommonTool.getNodeIdFromNodeUri(nodeUriString));
		}
//		for(int j=0;j<nodeIds.size();j++)
//			System.out.println(nodeIds.get(j));
		return nodeIds;
	}
	
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
	* @Description: 得到两个节点之间的关系，参数为节点的name值
	* @param nodeName1：第一个节点的name
	* @param nodeName2：第二个节点的name
	* @return：
	 */
	public String getRelationshipTypeBetweenTwoNodes(String nodeName1, String nodeName2){
		final String cypherUri = SERVER_ROOT_URI + "cypher";
		String cypherString = "start n=node(*) where n.name={name1} match n-[r]-friend where friend.name={name2} return r";
		String cypherJson = GenerateJson.generateJsonCypherForgetRelationshipTypeBetweenTwoNode(cypherString,
				nodeName1, nodeName2);

		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJson, "post");
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		if(jsonArray.size() == 0){
			//System.out.println("用户"+userName+"到组"+groupName+"不存在关系");
			return "";
		}else{
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String strTempString = jsonObject2.getString("type");
			System.out.println(strTempString);
			return strTempString;
		}
	}
	
	/**
	 * 
	* @Description: 得到两个节点之间关系的id
	* @param nodeId1
	* @param nodeId2
	* @return：
	 */
	public Integer getRelationshipIdBetweenTwoNodes(int nodeId1, int nodeId2){
		final String cypherUri = SERVER_ROOT_URI + "cypher";
		String cypherString = "start n=node({nodeId1}),m=node({nodeId2}) match n-[rel]-m return rel";
		String cypherJson = GenerateJson.generateJsonForgetRelationshipIdBetweenTwoNodes(cypherString, nodeId1, nodeId2);
		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJson, "post");
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		if(jsonArray.size() == 0){
			//System.out.println("用户"+userName+"到组"+groupName+"不存在关系");
			return -1;
		}else{
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String strTempString = jsonObject2.getString("self");
			int nodeId = CommonTool.getNodeIdFromNodeUri(strTempString);
//			System.out.println(nodeId);
			return nodeId;
		}
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
		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJsonString, "post");
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		if(jsonArray.size() == 0){
			//System.out.println("用户"+userName+"到组"+groupName+"不存在关系");
			return "";
		}else{
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String strTempString = jsonObject2.getString("type");
			return strTempString;
		}
	}
	
	/**
	 * @Description: 得到group节点到user节点的关系id
	 * @param userName
	 * @param groupName
	 * @return
	 */
	public Integer getRelationshipIdFromGroupToUser(String userName,String groupName){
		String cypherUri = SERVER_ROOT_URI + "cypher";
		String cypherString  = "match (n:Group) where n.name={name1} match n-[r]-friend where friend.name={name2} return r";
		String cypherJsonString = GenerateJson.generateJsonCypherForgetRelationshipTypeBetweenTwoNode(cypherString, groupName, userName);
		String dataResult = JerseyClient.sendToServer(cypherUri, cypherJsonString, "post");
		JSONObject jsonObject = JSONObject.fromObject(dataResult);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		if(jsonArray.size() == 0){
			//System.out.println("用户"+userName+"到组"+groupName+"不存在关系");
			return -1;
		}else{
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String strTempString = jsonObject2.getString("self");
			int relationshipId = CommonTool.getRelationshipIdFromRelationshipUri(strTempString);
//			System.out.println(strTempString);
			return relationshipId;
		}
	}
	
	
	//更新关系的属性
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
	
	
	//删除关系
	//----------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 删除一个关系
	* @param relationshipId：关系的id
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
	//-----------------------------------------------------------------------------------------------------------------------
	
	
	
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
	
	
	


}
