package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class User {
	NamespaceImpl namespace = new NamespaceImpl();
	RelationshipImpl relationship = new RelationshipImpl();
	
	/**
	 * 
	* @Description: 创建新用户
	* @param userName：
	* @return：
	 */
	public void createUser(String userName){
//		String timeString = CommonTool.getTime();
//		List<Integer> users = namespace.getNodeWithLabelAndProperty("User","name",userName);
//		if(users.size()!=0){
//			System.out.println("创建用户失败：名字为"+userName+"的用户已经存在！");
//		}else {
//			String propsString =  "{\"name\": \""+userName+"\", \"createTime\": \""+timeString+"\"}";
//			namespace.createNodeWithProperties("User", propsString);
//			System.out.println("创建用户"+userName+"成功");
//		}
	}
	
	/**
	 * 
	* @Description: 删除用户
	* @param userName：
	* @return：
	 */
	public void deleteUser(String userName){
//		List<Integer> users = namespace.getNodeWithLabelAndProperty("User", "name", userName);
//		if(users.size() == 0){
//			System.out.println("删除用户"+userName+"成功：该用户本来就不存在！");
//		}else{
//			for(int i=0;i<users.size();i++){
//				List<String> labels = new ArrayList<String>();
//				labels.add("contains");
//				List<Integer> relationshipIds = relationship.getRelationshipOfNode(users.get(i), "out", labels);
////				System.out.println(relationshipIds.size());
//				if(relationshipIds.size()!=0){
//					System.out.println("删除用户"+userName+"失败：请先删除用户拥有的文件");
//				}
//				else {
//					//删除群组和用户的关系
//					List<Integer> relationshipIdForGroups = relationship.getRelationshipOfNode(users.get(i), "in", labels);
//					for(int j=0;j<relationshipIdForGroups.size();j++){
//						relationship.deleteRelationship(relationshipIdForGroups.get(j));
//					}
//					//删除朋友关系、喜好关系
//					labels.clear();
//					labels.add("friend");
//					labels.add("like");
//					List<Integer> relationshipIdForFriendAndLikes = relationship.getRelationshipOfNode(users.get(i), "all", labels);
//					for(int k=0;k<relationshipIdForFriendAndLikes.size();k++){
//						relationship.deleteRelationship(relationshipIdForFriendAndLikes.get(k));
//					}
//					namespace.deleteNode(users.get(i));
//					System.out.println("删除用户"+userName+"成功");
//				}
//			}
//		}
	}
}
