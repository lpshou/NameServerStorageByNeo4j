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
	* @param relationshipData:关系属性,json串形式eg："{\"rel\" : \"very good\"}"
	* @return：
	 */
	public void createRelationshipBetweenTwoNode(int nodeId1,int nodeId2, 
			String relationshipType, String relationshipData);
	//---------------------------------------------------------------------------------------

	
	//更新关系
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
	* @param relationshipId：
	* @return：
	 */
	public void deleteRelationship(int relationshipId);
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
	
	
	
	//查询关系
	//--------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 获取一个节点的关系(深度为1）
	* @param nodeUri
	* @param direction:关系方向，有in、out、all;
	* @param labels:具体关系，放入list中
	* @return：所有关系的uri
	 */
	public List<Integer> getRelationshipOfNode(int nodeId,String direction,List<String>labels);
	
	/**
	 * 
	* @Description: 获取图中所有的关系label
	* @return：所有关系的labels
	 */
	public List<String> getAllRelationships();
	
	
	/**
	 * 
	* @Description: 得到两个节点之间的关系，参数为节点的name值(不怎么实用，保留观察）
	* @param nameNode1
	* @param nameNode2
	* @return：关系
	 */
	public  List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,String nameNode2);

	//--------------------------------------------------------------------------------------------------
	
}
