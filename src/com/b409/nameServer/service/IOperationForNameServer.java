package com.b409.nameServer.service;

import java.net.URI;
import java.util.List;

public interface IOperationForNameServer {
	//props为json串,返回值为创建的节点的uri
	public List<String> createNodeWithProperties(String label, String props);
	
	//props为json串
	public void setAllPropertiesOnNode(int nodeId, String props);
	public void setAllPropertiesOnNode(String nodeId, String props);

	

}
