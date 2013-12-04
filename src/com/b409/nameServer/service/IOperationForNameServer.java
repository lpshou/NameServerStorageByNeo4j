package com.b409.nameServer.service;

import java.net.URI;
import java.util.List;

public interface IOperationForNameServer {
	//props为json串
	public List<String> createNodeWithProperties(String label, String props);
	

}
