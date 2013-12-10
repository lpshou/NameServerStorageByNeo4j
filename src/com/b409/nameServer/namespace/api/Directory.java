package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class Directory {

	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	/**
	 * 
	* @Description: 列出某个用户或文件夹下所有的文件夹
	* @param parentName：用户名或文件夹名
	* @param parentType：User、Directory,用于区分用户还是文件夹，
	* @return：
	 */
	public static List<Integer> listAllDirectories(String parentName, String parentType){
		List<Integer> nodeIds = new ArrayList<>();
		if(parentName.equals("")||parentType.equals("")){
			System.out.println("参数不能为空！");
			return nodeIds;
		}
		int parentId =-1;
		//parentName类型
		switch(parentType.toUpperCase()){
		case "USER":parentId = namespace.getNodeWithLabelAndProperty("User", "name", parentName);break;
		case "DIRECTORY":parentId = namespace.getNodeWithLabelAndProperty("Directory", "name", parentName);break;
		default:System.out.println("参数type的类型不正确");
		}
		//parentName是否存在
		if(parentId == -1){
			System.out.println("查询失败: "+parentName+"不存在");
			return nodeIds;
		}else{
			List<String> relationshipTypes = new ArrayList<>();
			relationshipTypes.add("contains");
			List<Integer>nodeIdsList = relationship.getNodeIdsHaveRelationshipWithOneNode(parentId, "out", relationshipTypes);
			if(nodeIdsList.size() == 0){
				System.out.println(parentName+"下不包含文件夹");
				return nodeIds;
			}
			for(int i=0;i<nodeIdsList.size();i++){
				int nodeId = nodeIdsList.get(i);
				List<String>labels = namespace.listAllLabelsOfNode(nodeId);
				if(CommonTool.existInList(labels, "Directory")){
					nodeIds.add(nodeId);
					String nodeNameString = namespace.getNameOfNode(nodeId);
					System.out.println(nodeNameString);
				}
			}
			return nodeIds;
		}
	}
	
	
	/**
	 * 
	* @Description: 创建一个文件夹，在用户下或某个文件夹下创建一个文件夹
	* @param parentName：为用户名或文件夹名，
	* @param parentType：User、Directory,用于区分用户还是文件夹，
	* @param directoryName：要创建的文件夹名，
	* @return：成功返回0，失败返回-1，
	 */
	public static Integer createDirectory(String parentName, String parentType, String directoryName){
		if(parentName.equals("")||parentType.equals("")||directoryName.equals("")){
			System.out.println("参数不能为空！");
			return -1;
		}
		String timeString = CommonTool.getTime();
		int parentId =-1;
		//parentName类型
		switch(parentType.toUpperCase()){
		case "USER":parentId = namespace.getNodeWithLabelAndProperty("User", "name", parentName);break;
		case "DIRECTORY":parentId = namespace.getNodeWithLabelAndProperty("Directory", "name", parentName);break;
		default:System.out.println("参数type的类型不正确");
		}
		//parentName是否存在
		if(parentId == -1){
			System.out.println("创建失败: "+parentName+"不存在");
			return -1;
		}else{
			//检查parentName下是否已经存在名为directoryName的文件夹
//			方法1：
//			List<String> relationshipTypes = new ArrayList<>();
//			relationshipTypes.add("contains");
//			List<Integer> nodeIds = relationship.getNodeIdHaveRelationshipWithOneNode(parentId, "out", relationshipTypes);
//			boolean flag = false;
//			for(int i=0;i<nodeIds.size();i++){
//				String nodeName = namespace.getNameOfNode(nodeIds.get(i));
//				if(nodeName.equals(directoryName)){
//					flag =true;
//					break;
//				}
//			}
//			方法2：
			int nodeId = namespace.getNodeWithLabelAndProperty("Directory", "name", directoryName);
			
			//parentName下面已经存在名为directoryName的文件夹
			if(nodeId != -1){
				System.out.println("创建失败:"+directoryName+"已经存在");
				return -1;
			}else{
				//parentName下面不存在名为directoryName的文件夹
				String propsString = "{\"name\": \""+directoryName+"\",\"createTime\": \""+timeString+"\"}";
				//创建名为directoryName的文件夹
				List<Integer> directoryIds = namespace.createNodeWithProperties("Directory", propsString);
				int directoryId = directoryIds.get(0);
				//在parentName和directoryName之间建立关系
				relationship.createRelationshipBetweenTwoNode(parentId, directoryId, "contains", "{\"createTime\": \""+timeString+"\"}");
				System.out.println("创建文件夹成功！");
				return 0;
			}
		}
	}
	
	
	/**
	 * 
	* @Description: 删除一个文件夹，在用户或某个文件夹下删除一个文件夹
	* @param parentName：用户名或文件夹名
	* @param parentName：User、Directory,用于区分用户还是文件夹，
	* @param directoryName：文件夹名
	* @return：成功返回0，失败返回-1
	 */
	public static Integer deleteDirectory(String parentName, String parentType, String directoryName){
		if(parentName.equals("")||parentType.equals("")||directoryName.equals("")){
			System.out.println("参数不能为空！");
			return -1;
		}
		String timeString = CommonTool.getTime();
		int parentId =-1;
		//parentName类型
		switch(parentType.toUpperCase()){
		case "USER":parentId = namespace.getNodeWithLabelAndProperty("User", "name", parentName);break;
		case "DIRECTORY":parentId = namespace.getNodeWithLabelAndProperty("Directory", "name", parentName);break;
		default:System.out.println("参数type的类型不正确");
		}
		//parentName是否存在
		if(parentId == -1){
			System.out.println("失败: "+parentName+"不存在");
			return -1;
		}else{
			int nodeId = namespace.getNodeWithLabelAndProperty("Directory", "name", directoryName);
			
			//parentName下面存在名为directoryName的文件夹
			if(nodeId == -1){
				System.out.println("删除成功:"+directoryName+"本来就不存在");
				return 0;
			}else{
				int relId = relationship.getRelationshipIdBetweenTwoNodes(parentId, nodeId);
				relationship.deleteRelationship(relId);
				namespace.deleteNode(nodeId);
				System.out.println("删除成功");
				return 0;
			}
		}
	}
	
}
