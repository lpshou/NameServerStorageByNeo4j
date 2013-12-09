package com.b409.nameServer.relationship.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class Like {
	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	/**
	 * 
	* @Description: 建立用户对某个文件的喜爱
	* @param userName：用户名
	* @param fileName：文件名
	* @return：
	 */
	public static Integer createLike(String userName, String fileName){
		if(userName.equals("")|| fileName.equals("")){
			System.out.println("参数不能为空");
			return -1;
		}
		String timeString = CommonTool.getTime();
		int userNodeId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		int fileNodeId = namespace.getNodeWithLabelAndProperty("File", "name", fileName);
		if(userNodeId == -1){
			System.out.println("用户"+userName+"不存在");
			return -1;
		}
		if(fileNodeId == -1){
			System.out.println("文件"+fileNodeId+"不存在");
			return -1;
		}
		int relationshipId = relationship.getRelationshipIdBetweenTwoNodes(userNodeId, fileNodeId);
		if(relationshipId != -1){
			System.out.println("两者之间已经存在关系");
			return 0;
		}else {
			String propsString = "{\"createTime\" :\""+timeString+"\"}";
			relationship.createRelationshipBetweenTwoNode(userNodeId, fileNodeId, "like", propsString);
			System.out.println("创建喜好关系成功");
			return 0;
		}
		
	}
	
	/**
	 * 
	* @Description: 删除用户对某个文件的喜好
	* @param userName：用户名
	* @param fileName：文件名
	* @return：
	 */
	public static Integer deleteLike(String userName, String fileName){
		if(userName.equals("")|| fileName.equals("")){
			System.out.println("参数不能为空");
			return -1;
		}
		String timeString = CommonTool.getTime();
		int userNodeId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		int fileNodeId = namespace.getNodeWithLabelAndProperty("File", "name", fileName);
		if(userNodeId == -1){
			System.out.println("用户"+userName+"不存在");
			return -1;
		}
		if(fileNodeId == -1){
			System.out.println("文件"+fileNodeId+"不存在");
			return -1;
		}
		int relationshipId = relationship.getRelationshipIdBetweenTwoNodes(userNodeId, fileNodeId);
		if(relationshipId == -1){
			System.out.println("两者之间不存在关系");
			return 0;
		}else {
			relationship.deleteRelationship(relationshipId);
			System.out.println("解除两者之间的喜爱关系");
			return 0;
		}
	}
	
	/**
	 * 
	* @Description: 列出所有喜爱的文件的id
	* @param userName：用户名
	* @return：
	 */
	public static List<Integer> listAllLikedFile(String userName){
		List<Integer> fileIds = new ArrayList<>();
		if(userName.equals("")){
			System.out.println("参数不能为空");
			return fileIds;
		}
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("该用户不存在");
			return fileIds;
		}else {
			List<String>relationshipTypes = new ArrayList<>();
			relationshipTypes.add("like");
			fileIds = relationship.getNodeIdHaveRelationshipWithOneNode(userId, "out", relationshipTypes);
			if(fileIds.size() == 0){
				System.out.println("该用户与其他任何用户建立朋友关系");
				return fileIds;
			}
			for(int i=0;i<fileIds.size();i++){
				String nameString = namespace.getNameOfNode(fileIds.get(i));
				System.out.println(nameString);
			}
		}
		return fileIds;
	}

}
