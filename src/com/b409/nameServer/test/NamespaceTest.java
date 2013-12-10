package com.b409.nameServer.test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;





import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;

public class NamespaceTest {
	public static void main(String[] args) throws URISyntaxException {

		NamespaceImpl namespace= new NamespaceImpl();
		namespace.createNodeWithProperties("Group", "{}");
		
	}
}
