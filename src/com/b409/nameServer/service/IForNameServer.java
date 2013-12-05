package com.b409.nameServer.service;

import java.util.List;

public interface IForNameServer {
	
	//节点的相关操作
	//--------------------------------------------------------------------------------
	/**
	* @Description: 创建一个节点
	* @param label：节点的label
	* @param props：属性值props为json串eg：{"a":"b"}，可以为空eg：{}
	* @return	  ：返回值为创建的节点的uri 
	 */
	public List<String> createNodeWithProperties(String label, String props);
	/**
	 * 
	* @Description: 删除一个节点
	* @param nodeId：
	* @return：
	 */
	public void deleteNode(int nodeId);
	
	/**
	 * 
	* @Description: 获取具有某个label的所有node
	* @param label
	* @return：
	 */
	public List<Integer> getAllNodesWithLabel(String label);
	
	/**
	 * 
	* @Description: 根据label和属性查找某个节点（只支持单属性查找）
	* @param label
	* @param propertyName：属性名
	* @param propertyValue：属性值
	* @return：
	 */
	public List<Integer> getNodeWithLabelAndProperty(String label, String propertyName,String propertyValue);
	//--------------------------------------------------------------------------------
	
	
	
	//节点的属性相关操作
	//--------------------------------------------------------------------------------
	//-------------------------------查询
	/**
	 * 
	* @Description: 获取节点信息（包括属性、id、uri等等）
	* @param nodeId:节点的id号
	* @return：节点的信息，为json串
	 */
	public String getMessageOfNode(int nodeId);
	/**
	 * 
	* @Description: 获取节点具体属性
	* @param nodeId
	* @return：返回值为json串形式（属性：属性值）
	 */
	public String getPropertiesOfNode(int nodeId);
	/**
	 * 
	* @Description: 获取节点有哪些属性
	* @param nodeId
	* @return：节点的属性（属性）
	 */
	public List<String> getPropertiesOfKeyOfNode(int nodeId);
	//----------------------------更新
	/**
	 * 
	* @Description: 设置节点属性，将完全替换原有属性
	* @param nodeId
	* @param props：props为json串
	* @return：
	 */
	public void setAllPropertiesOnNode(int nodeId, String props);
	
	/**
	 * 
	* @Description: 更新节点某个属性值,其他属性值不变(将属性key的值改为value)
	* @param nodeUri
	* @param key:要更新的属性
	* @param value：更新为的属性值
	* @return：
	 */
	public void updateOnePropertyOnNode(String nodeUri,String key,String value);
	//-----------------------------删除
	/**
	 * 
	* @Description: 删除一个节点的所有属性
	* @param nodeId：
	* @return：
	 */
	public void deleteAllPropertiesOnNode(int nodeId);
	/**
	 * 
	* @Description: 删除一个节点的某个属性
	* @param nodeId：
	* @return：
	 */
	public void deleteOnePropertyOnNode(int nodeId,String propertyName);
	//----------------------------------------------------------------------------------
	

	//节点label的相关操作
	//--------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 给节点增加labels
	* @param nodeId
	* @param labels：要增加的labels,
	* @return：
	 */
	public void addLabelsToNode(int nodeId,List<String>labels);
	
	/**
	 * 
	* @Description: 替换节点的labels(备注：首次成功，但是最后有问题，有待进一步处理......)
	* 				（备注2：问题已经解决，是每次的response没有关闭的缘故）
	* @param nodeId
	* @param labels：替换为labels
	* @return：
	 */
	public void replaceLabelsOnNode(int nodeId,List<String>labels);
	
	/**
	 * 
	* @Description: 删除节点上的某个label
	* @param nodeId
	* @param label：
	* @return：
	 */
	public void removeOneLabelOnNode(int nodeId,String label);
	
	/**
	 * 
	* @Description: 列出一个节点的所有labels
	* @param nodeId
	* @return：
	 */
	public List<String> listAllLabelsOfNode(int nodeId);
	
	/**
	 * 
	* @Description: 列出图中所有的labels
	* @return：
	 */
	public List<String> listAllLabelsInGraph();
	
	//--------------------------------------------------------------------------------
	
	
	

}