package com.b409.nameServer.service;

import java.util.List;


public interface RelationshipInterface {
	
	//创建关系
	//-------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 在两个节点之间建立关系，
	* @param nodeId1
	* @param nodeId2
	* @param relationshipType:关系名
	* @param relationshipData:关系属性,json串形式eg："{\"rel\" : \"very good\"}" 为空："{}"
	* @return：
	 */
	public void createRelationshipBetweenTwoNode(int nodeId1,int nodeId2, 
			String relationshipType, String relationshipData);
	//---------------------------------------------------------------------------------------

	//查询关系
	//--------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 获取一个节点的关系id
	* @param nodeId:节点id
	* @param direction:关系方向，有in、out、all;
	* @param relationshipTypes:具体关系，放入list中
	* @return：所有关系ids
	 */
	public List<Integer> getRelationshipIdsOfOneNode(int nodeId,String direction,List<String>relationshipTypes);
	
	
	/**
	 * 
	* @Description: 获取与一个节点有关系的节点的id
	* @param nodeId
	* @param direction:关系方向，有in、out、all;
	* @param relationshipTypes：具体关系，放入list中
	* @return：所有节点的ids
	 */
	public List<Integer> getNodeIdsHaveRelationshipWithOneNode(int nodeId,String direction,List<String>relationshipTypes);

	/**
	 * 
	* @Description: 获取图中所有的关系label
	* @return：所有关系的labels
	 */
	public List<String> getAllRelationships();
	

	/**
	 * 
	* @Description: 得到两个节点之间的关系，参数为节点的name值
	* @param nodeName1：第一个节点的name
	* @param nodeName2：第二个节点的name
	* @return：
	 */
	public String getRelationshipTypeBetweenTwoNodes(String nodeName1, String nodeName2);
	
	/**
	 * 
	* @Description: 得到两个节点之间关系的id
	* @param nodeId1
	* @param nodeId2
	* @return：
	 */
	public Integer getRelationshipIdBetweenTwoNodes(int nodeId1, int nodeId2);
	/**
	 * 
	* @Description: 得到user节点到group节点之间的关系类型
	* @param userName：user名
	* @param groupName：group名
	* @return：关系类型,不存在关系返回"",存在关系则返回关系类型
	 */
	public String getRelationshipTypeFromUserToGroup(String userName, String groupName);
	
	/**
	 * @Description: 得到group节点到user节点的关系id
	 * @param userName
	 * @param groupName
	 * @return:关系id，不存在返回-1，存在返回关系id
	 */
	public Integer getRelationshipIdFromGroupToUser(String userName,String groupName);
	//--------------------------------------------------------------------------------------------------
	
	
	//更新关系的属性
	//---------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 更新一个关系的属性
	* @param relationshipId
	* @param props：json串eg："{\"rel\":\"test\"}"
	* @return：
	 */
	public void updatePropertiesOnRelationship(int relationshipId,String props);
	//---------------------------------------------------------------------------------------
	
	
	//删除关系
	//---------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 删除一个关系
	* @param relationshipId：关系id
	* @return：
	 */
	public void deleteRelationship(int relationshipId);
	
	/**
	 * 
	* @Description: 删除一个节点的某种关系
	* @param nodeId：节点id
	* @param direction：关系方向，有all、in、out三种
	* @param relationshipTypes：关系类型，有contains、friend、like、create四种
	* @return：
	 */
	public void deleteRelationshipOfNode(int nodeId,String direction, List<String>relationshipTypes);
	//---------------------------------------------------------------------------------------
	
	
	
	//删除关系的属性
	//---------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 删除一个关系上的所有属性
	* @param relationshipId：
	* @return：
	 */
	public void deletePropertiesOnRelationship(int relationshipId);
	

	/**
	 * 
	* @Description: 删除一个关系上的某个属性
	* @param relationshipId
	* @param propertyName
	* @return：
	 */
	public void deleteOnePropertyOnRelationship(int relationshipId,String propertyName);
	
	//---------------------------------------------------------------------------------------
	
	
	
}
