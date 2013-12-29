package com.b409.nameServer.relationship.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class Friend {
	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	
	/**
	 * 
	* @Description: 查询两个用户之间的好友关系
	* @param userName1
	* @param userName2
	* @return：
	* @return：
	 */
	public static String queryFriends(String userName1, String userName2){
		if(userName1.equals("")||userName2.equals("")){
				System.out.println("参数不能为空");
				return "";
		}
		String timeString = CommonTool.getTime();
		int userId1 = namespace.getNodeWithLabelAndProperty("User", "name", userName1);
		int userId2 = namespace.getNodeWithLabelAndProperty("User", "name", userName2);
		if(userId1 == -1 || userId2 == -1){
			System.out.println(userName1+"和"+userName2+"中某个用户不存在");
			return "";
		}
		String relationshipProperties =  relationship.getRelationshipTypeBetweenTwoNodes(userName1, userName2);
		if(relationshipProperties.equals("")){
			System.out.println("两者之间不存在朋友关系");
		}else{
			System.out.println(relationshipProperties);
		}
		return relationshipProperties;
	}
	/**
	 * 
	* @Description: 建立朋友关系，（严格意义上说，应该是用户1对用户2的关注关系）
	* @param userName1：用户1
	* @param userName2：用户2
	* @return：
	 */
	public static Integer makeFriends(String userName1, String userName2){
		if(userName1.equals("")||userName2.equals("")){
				System.out.println("参数不能为空");
				return -1;
		}
		String timeString = CommonTool.getTime();
		int userId1 = namespace.getNodeWithLabelAndProperty("User", "name", userName1);
		int userId2 = namespace.getNodeWithLabelAndProperty("User", "name", userName2);
		if(userId1 == -1 || userId2 == -1){
			System.out.println(userName1+"和"+userName2+"中某个用户不存在");
			return -1;
		}
		int relashiptionId = relationship.getRelationshipIdBetweenTwoNodes(userId1, userId2);
		if(relashiptionId != -1){
			System.out.println("建立朋友关系成功：两者原来就存在朋友关系");
			return 0;
		}else{
			String propsString = "{\"createTime\" :\""+timeString+"\"}";
			relationship.createRelationshipBetweenTwoNode(userId1, userId2, "friend", propsString);
			System.out.println("创建朋友关系成功");
			return 0;
		}
	}
	
	/**
	 * 
	* @Description: 解除朋友关系，（严格意义上说，应该是用户1对用户2的关注关系）
	* @param userName1：用户1
	* @param userName2：用户2
	* @return：
	 */
	public static Integer unfriend(String userName1, String userName2){
		if(userName1.equals("")||userName2.equals("")){
			System.out.println("参数不能为空");
			return -1;
		}
		int userId1 = namespace.getNodeWithLabelAndProperty("User", "name", userName1);
		int userId2 = namespace.getNodeWithLabelAndProperty("User", "name", userName2);
		if(userId1 == -1 || userId2 == -1){
			System.out.println(userName1+"和"+userName2+"中某个用户不存在");
			return -1;
		}
		int relashiptionId = relationship.getRelationshipIdBetweenTwoNodes(userId1, userId2);
		if(relashiptionId == -1){
			System.out.println("解除朋友关系成功：两者之间不存在朋友关系");
			return 0;
		}else{
			relationship.deleteRelationship(relashiptionId);
			System.out.println("解除朋友关系成功");
			return 0;
		}
	}
	
	/**
	 * 
	* @Description: 找到所有的朋友（关注的人）
	* @param userName：
	* @return：
	 */
	public static List<Integer> listAllFriends(String userName){
		List<Integer> friendIds = new ArrayList<>();
		if(userName.equals("")){
			System.out.println("参数不能为空");
			return friendIds;
		}
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("该用户不存在");
			return friendIds;
		}else {
			List<String>relationshipTypes = new ArrayList<>();
			relationshipTypes.add("friend");
			friendIds = relationship.getNodeIdsHaveRelationshipWithOneNode(userId, "out", relationshipTypes);
			if(friendIds.size() == 0){
				System.out.println("该用户与其他任何用户建立朋友关系");
				return friendIds;
			}
			for(int i=0;i<friendIds.size();i++){
				String nameString = namespace.getNameOfNode(friendIds.get(i));
				System.out.println(nameString);
			}
		}
		return friendIds;
	}
}
