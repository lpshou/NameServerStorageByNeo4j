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
			List<Integer>nodeIdsList = relationship.getNodeIdHaveRelationshipWithOneNode(parentId, "out", relationshipTypes);
			if(nodeIdsList.size() == 0){
				System.out.println(parentName+"下不包含文件夹");
				return nodeIds;
			}
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
		if(parentId == -1){
			System.out.println("创建失败: "+parentName+"不存在");
			return -1;
		}else{
			int nodeId = namespace.getNodeWithLabelAndProperty("File", "name", fileName);
			
			//parentName下面已经存在名为fileName的文件
			if(nodeId != -1){
				System.out.println("创建失败:"+fileName+"已经存在");
				return -1;
			}else{
				//parentName下面不存在名为fileName的文件
				String propsString = "{\"name\": \""+fileName+"\",\"createTime\": \""+timeString+
						"\", \"fileLocation\" :\""+fileLocation+"\"}";
				//创建名为fileName的文件
				List<Integer> directoryIds = namespace.createNodeWithProperties("File", propsString);
				int directoryId = directoryIds.get(0);
				//在parentName和fileName之间建立关系
				relationship.createRelationshipBetweenTwoNode(parentId, directoryId, "contains", "{\"createTime\": \""+timeString+"\"}");
				System.out.println("创建文件成功！");
				return 0;
			}
		}
	}
	
	/**
	 * 
	* @Description: 删除一个文件，将用户或某个文件夹下的某个文件删除
	* @param parentName：用户名或文件夹名
	* @param parentType：User、Directory,用于区分用户还是文件夹，
	* @param fileName：要删除的文件名
	* @return：
	 */
	public static void deleteFile(String parentName, String parentType, String fileName){
		
	}

}
