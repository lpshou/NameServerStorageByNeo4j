package com.b409.nameServer.service;

import java.net.URI;
import java.util.List;

public interface IOperationForNameServer {
	//创建一个节点，props为json串,返回值为创建的节点的uri
	public List<String> createNodeWithProperties(String label, String props);
	
	//获取节点信息（包括属性、id、uri等等）
	public String getMessageOfNode(int nodeId);
	//获取节点属性（返回json串）
	public String getPropertieOfNode(int nodeId);
	//获取节点URI
	public String getUriOfNode(int nodeId);
	
	//设置节点属性，props为json串
	public void setAllPropertiesOnNode(int nodeId, String props);
	public void setAllPropertiesOnNode(String nodeUri, String props);

	

}
