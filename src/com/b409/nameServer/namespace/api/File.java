package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class File {
	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	/**
	 * 
	* @Description: 列出所有文件，列出用户或文件夹下的所有文件
	* @param parentName：用户名或文件夹名
	 * @return 
	* @return：
	 */
	public static List<Integer> listAllFiles(String parentName, String parentType){
		List<Integer> nodeIds = new ArrayList<>();
		//判断参数是否为空
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
		//判断parentName是否存在
		//parentName不存在
		if(parentId == -1){
			System.out.println("查询失败: "+parentName+"不存在");
			return nodeIds;
		}
		//parentName存在
		//判断parentName下是否包含文件
		List<String> relationshipTypes = new ArrayList<>();
		relationshipTypes.add("contains");
		List<Integer>nodeIdsList = relationship.getNodeIdsHaveRelationshipWithOneNode(parentId, "out", relationshipTypes);
		//判断parentName下不包含文件
		if(nodeIdsList.size() == 0){
			System.out.println(parentName+"下不包含文件");
			return nodeIds;
		}
		//判断parentName下可能包含文件
		for(int i=0;i<nodeIdsList.size();i++){
			int nodeId = nodeIdsList.get(i);
			List<String>labels = namespace.listAllLabelsOfNode(nodeId);
			if(CommonTool.existInList(labels, "File")){
				nodeIds.add(nodeId);
				String nodeNameString = namespace.getNameOfNode(nodeId);
				System.out.println(nodeNameString);
			}
		}
		return nodeIds;

	}
	
	/**
	 * 
	* @Description: 创建一个文件，在用户下或某个文件夹下创建一个文件
	* @param parentName：用户名或文件夹名
	* @param parentType：User、Directory,用于区分用户还是文件夹，
	* @param fileName：新创建的文件名
	* @return：成功返回0，失败返回-1，
	 */
	public static Integer createFile(String parentName, String parentType, String fileName, String fileLocation){
		//判断参数是否为空
		if(parentName.equals("")||parentType.equals("")||fileName.equals("")||fileLocation.equals("")){
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
		//parentName不存在
		if(parentId == -1){
			System.out.println("创建失败: "+parentName+"不存在");
			return -1;
		}
		//parentName存在
		//parentName下是否已经存在fileName
		//fileName的name为parentName+""+fileName;
		String name= parentName+"_"+fileName;
		int nodeId = namespace.getNodeWithLabelAndProperty("File", "name", name);
		
		//parentName下面已经存在名为fileName的文件
		if(nodeId != -1){
			System.out.println("创建失败:"+fileName+"已经存在");
			return -1;
		}

		//parentName下面不存在名为fileName的文件
		String propsString = "{\"name\": \""+name +
				"\",\"displayName\": \"" + fileName +
				"\", \"acl\": \"" + "public" +
				"\",\"createTime\": \"" + timeString +
				"\", \"fileLocation\" :\"" + fileLocation+"\"}";
		//创建名为fileName的文件
		int fileId = namespace.createNodeWithProperties("File", propsString);
		//在parentName和fileName之间建立关系
		String timeProps = "{\"createTime\": \""+timeString+"\"}";
		relationship.createRelationshipBetweenTwoNode(parentId, fileId, "contains", timeProps);
		System.out.println("创建文件成功！");
		return 0;

	}
	
	/**
	 * 
	* @Description: 删除一个文件，将用户或某个文件夹下的某个文件删除
	* @param parentName：用户名或文件夹名
	* @param parentType：User、Directory,用于区分用户还是文件夹，
	* @param fileName：要删除的文件名
	* @return：成功返回0，失败返回-1
	 */
	public static Integer deleteFile(String parentName, String parentType, String fileName){
		if(parentName.equals("")||parentType.equals("")||fileName.equals("")){
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
		}
		//parentName存在
		//parentName下是否存在名为fileName的文件
		//其name属性为：
		String name = parentName + "_" + fileName;
		int nodeId = namespace.getNodeWithLabelAndProperty("File", "name", name);
		
		//parentName下面存在名为directoryName的文件夹
		//不存在
		if(nodeId == -1){
			System.out.println("删除失败:"+fileName+"本来就不存在");
			return -1;
		}else{
			//删除关系
			int relId = relationship.getRelationshipIdBetweenTwoNodes(parentId, nodeId);
			relationship.deleteRelationship(relId);
			//删除节点
			namespace.deleteNode(nodeId);
			System.out.println("删除成功");
			return 0;
		}

	}
	
	/**
	 * 
	* @Description: 获得文件存放位置的md5值
	* @param fileName：文件名
	* @return：
	 */
	public static String getFileLocation(String fileName){
		//判断参数是否为空
		if(fileName.equals("")){
			System.out.println("文件名不能为空");
			return "";
		}
		int nodeId = namespace.getNodeWithLabelAndProperty("File", "name", fileName);
		if(nodeId == -1){
			System.out.println("文件名为"+fileName+"的文件不存在");
			return "";
		}else{
			String fileLocation = namespace.getFileLocationOfNode(nodeId);
			System.out.println("文件位置为："+fileLocation);
			return fileLocation;
		}
	}
	
	/**
	 * 
	* @Description: 更新文件存放位置
	* @param fileName：文件名
	* @param fileLocation：文件存放位置
	* @return：
	 */
	public static Integer updateFileLocation(String fileName, String fileLocation){
		//参数判断
		if(fileName.equals("")){
			System.out.println("文件名不能为空");
			return -1;
		}
		//判断文件是否存在
		int nodeId = namespace.getNodeWithLabelAndProperty("File", "name", fileName);
		if(nodeId == -1){
			System.out.println("文件名为"+fileName+"的文件不存在");
			return -1;
		}else{
			//更新节点属性
			String nodeUri = CommonTool.getNodeUriFromNodeId(nodeId);
			namespace.updateOnePropertyOnNode(nodeUri, "fileLocation", fileLocation);
			System.out.println("更新成功");
			return 0;
		}
	}
	

}
