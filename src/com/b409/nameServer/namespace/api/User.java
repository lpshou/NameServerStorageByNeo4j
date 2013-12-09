package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class User {
	NamespaceImpl namespace = new NamespaceImpl();
	RelationshipImpl relationship = new RelationshipImpl();
	
	//用户相关
	//-----------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 创建新用户
	* @param userName：用户名
	* @return：成功返回0，失败返回1
	 */
	public Integer createUser(String userName){
		if(userName.equals("")){
			System.out.println("创建用户失败：用户名为空");
			return -1;
		}
		String timeString = CommonTool.getTime();
		int userId = namespace.getNodeWithLabelAndProperty("User","name",userName);
		if(userId != -1){
			System.out.println("创建用户失败：名字为"+userName+"的用户已经存在！");
			return -1;
		}else {
			String propsString =  "{\"name\": \""+userName+"\", \"createTime\": \""+timeString+"\"}";
			namespace.createNodeWithProperties("User", propsString);
			System.out.println("创建用户"+userName+"成功");
			return 0;
		}
	}
	
	/**
	 * 
	* @Description: 删除用户
	* @param userName：
	* @return：
	 */
	public Integer deleteUser(String userName){
		if(userName.equals("")){
			System.out.println("删除用户失败：用户名为空");
			return -1;
		}
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("删除用户"+userName+"成功：该用户本来就不存在！");
			return 0;
		}else{
			List<String> relationshipTypes = new ArrayList<String>();
			relationshipTypes.add("contains");
			relationshipTypes.add("create");
			
			List<Integer> relationshipIds = relationship.getRelationshipIdsOfOneNode(userId, "out", relationshipTypes);
//				System.out.println(relationshipIds.size());
			//如果用户拥有文件或创建了组，则无法删除
			if(relationshipIds.size()!=0){
				System.out.println("删除用户"+userName+"失败：请先删除用户拥有的文件和用户创建的组");
				return -1;
			}else {
				//删除其他组、文件、用户和该用户之间的关系
				List<String>relationshipTypeStrings = new ArrayList<>();
				relationshipTypeStrings.add("contains");
				relationshipTypeStrings.add("like");
				relationshipTypeStrings.add("friend");
				List<Integer> relationshipIdslList = relationship.getRelationshipIdsOfOneNode(userId, "all", relationshipTypeStrings);
				for(int i=0;i<relationshipIdslList.size();i++){
					relationship.deleteRelationship(relationshipIdslList.get(i));
				}
				//删除这个节点
				namespace.deleteNode(userId);
				System.out.println("删除用户"+userName+"成功");
				return 0;
			}
		}
	}
	//-------------------------------------------------------------------------------------------------
	
	//组相关
	//-------------------------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------------------------
	
	
	
	//文件夹相关
	//-------------------------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------------------------
	
	
	
	//文件相关
	//-------------------------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------------------------

}
