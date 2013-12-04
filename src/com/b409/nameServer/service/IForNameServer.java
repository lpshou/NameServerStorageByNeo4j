package com.b409.nameServer.service;

import java.net.URI;
import java.util.List;

public interface IForNameServer {
	//创建一个节点，属性值props为json串eg：{"a":"b"},返回值为创建的节点的uri
	public List<String> createNodeWithProperties(String label, String props);
	
	
	//获取节点信息（包括属性、id、uri等等）
	public String getMessageOfNode(int nodeId);
	//获取节点具体属性，json串形式
	public String getPropertiesOfNode(int nodeId);
	//获取节点有哪些属性
	public List<String> getPropertiesOfKeyOfNode(int nodeId);
	//获取节点URI
	public String getUriOfNode(int nodeId);
	
	
	
	//设置节点属性，props为json串，将完全替换原有属性
	public void setAllPropertiesOnNode(int nodeId, String props);
	public void setAllPropertiesOnNode(String nodeUri, String props);
	//更新节点某个属性值,其他属性值不变(将key的值改为value)
	public void updateOnePropertyOnNode(String nodeUri,String key,String value);
	
	
	
	//删除一个节点
	public void deleteNode(String nodeUri);
	public void deleteNode(int nodeId);

	

}
