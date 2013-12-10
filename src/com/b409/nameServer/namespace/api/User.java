package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class User {
	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	
	//用户相关
	//------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 创建用户
	* @param userName：用户名
	* @return：成功返回0，失败返回1
	 */
	public static Integer createUser(String userName){
		//判断参数
		if(userName.equals("")){
			System.out.println("创建用户失败：用户名为空");
			return -1;
		}
		//判断用户是否存在
		String timeString = CommonTool.getTime();
		int userId = namespace.getNodeWithLabelAndProperty("User","name",userName);
		//用户已经存在
		if(userId != -1){
			System.out.println("创建用户失败：名字为"+userName+"的用户已经存在！");
			return -1;
		}
		//用户不存在
		String propsString =  "{\"name\": \"" + userName +
				"\", \"displayName\": \"" + userName + 
				"\", \"createTime\": \"" + timeString + "\"}";
		namespace.createNodeWithProperties("User", propsString);
		System.out.println("创建用户"+userName+"成功");
		return 0;
	}
	
	/**
	 * 
	* @Description: 删除用户
	* @param userName：用户名
	* @return：成功返回0，失败返回-1
	 */
	public static Integer deleteUser(String userName){
		//判断参数
		if(userName.equals("")){
			System.out.println("删除用户失败：用户名为空");
			return -1;
		}
		//判断用户是否存在
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		//用户不存在
		if(userId == -1){
			System.out.println("删除用户"+userName+"失败：该用户本来就不存在！");
			return -1;
		}
		//用户存在
		List<String> relationshipTypes = new ArrayList<String>();
		relationshipTypes.add("contains");
		relationshipTypes.add("create");
		List<Integer> relationshipIds = relationship.getRelationshipIdsOfOneNode(userId, "out", relationshipTypes);
	
		//如果用户拥有文件或创建了组，则无法删除
		if(relationshipIds.size()!=0){
			System.out.println("删除用户"+userName+"失败：请先删除用户拥有的文件和用户创建的组");
			return -1;
		}
		//如果用户没有拥有文件或创建了群组，则删除其他组、文件、用户和该用户之间的关系
		List<String>relationshipTypeStrings = new ArrayList<>();
		relationshipTypeStrings.add("contains");
		relationshipTypeStrings.add("like");
		relationshipTypeStrings.add("friend");
		relationship.deleteRelationshipOfNode(userId, "all", relationshipTypeStrings);
		
		//删除这个节点
		namespace.deleteNode(userId);
		System.out.println("删除用户"+userName+"成功");
		return 0;

	}
	//-----------------------------------------------------------------------------------------------------------------------
	
	
	
	//用户和组的关系
	//------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	* @Description: 将一个用户加入一个组
	* @param groupName
	* @param userName：
	* @return：成功返回0，失败返回-1
	 */
	public static Integer addUserToGroup(String groupName, String userName){
		//判断参数
		if(groupName.equals("")||userName.equals("")){
			System.out.println("加入组"+groupName+"失败：组名和用户名不能为空！");
			return -1;
		}
		//判断组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId == -1){
			System.out.println("加入组"+groupName+"失败：该组不存在！");
			return -1;
		}
		//判断用户是否存在
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("加入组"+groupName+"失败：用户不存在！");
			return -1;
		}
		//在组和用户之间建立contains关系
		String timeString = CommonTool.getTime();
		String nowTimeString = "{\"containsTime\": \"" + timeString + "\"}";
		relationship.createRelationshipBetweenTwoNode(groupId, userId, "contains", nowTimeString);
		System.out.println("加入组"+groupName+"成功");
		return 0;

	}
	
	/**
	 * 
	* @Description: 用户退出一个组
	* @param groupName：组名
	* @param userName：用户名
	* @return：
	 */
	public static Integer deleteUserFromGroup(String groupName, String userName){
		//判断参数
		if(groupName.equals("")||userName.equals("")){
			System.out.println("将用户"+userName+"从组"+groupName+"删除失败：组名和用户名不能为空！");
			return -1;
		}
		//判断组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId == -1){
			System.out.println("将用户"+userName+"从组"+groupName+"删除失败：该组"+groupName+"不存在！");
			return -1;
		}
		//判断用户是否存在
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("将用户"+userName+"从组"+groupName+"删除失败：用户"+userName+"不存在！");
			return -1;
		}
		//获取用户到组的关系
		String relationshipType = relationship.getRelationshipTypeFromUserToGroup(userName, groupName);
		if(relationshipType.equals("create")){
			System.out.println("将用户"+userName+"从组"+groupName+"删除失败：用户"+userName+"是该组"+groupName+"的创建者");
			return -1;
		}else if(relationshipType.equals("")){
			System.out.println("将用户"+userName+"从组"+groupName+"删除失败：用户"+userName+"不是组"+groupName+"的成员");
			return -1;
			
		}else{}
		//删除该组到用户的contains关系
		int relationshipId = relationship.getRelationshipIdFromGroupToUser(userName, groupName);
		relationship.deleteRelationship(relationshipId);
		System.out.println("将用户"+userName+"从组"+groupName+"退出成功");
		return 0;
	}
	//------------------------------------------------------------------------------------------------------------------------
}
