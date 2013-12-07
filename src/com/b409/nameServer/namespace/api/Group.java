package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.cypher.internal.commands.Return;
import org.neo4j.cypher.internal.parser.v2_0.functions.Nodes;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class Group {
	//查看所有的组：public List<Integer> listAllGroup()
	//创建一个组：public void createGroup(String groupName)
	//解散一个组：public void deleteGroup(String groupName)
	NamespaceImpl namespace = new NamespaceImpl();
	RelationshipImpl relationship = new RelationshipImpl();
	

	/**
	 * 
	* @Description: 查看所有的组
	* @return：所有的组 
	 */
	public List<Integer> listAllGroup(){
		List<Integer> groupIds = new ArrayList<Integer>();
		groupIds = namespace.getAllNodesWithLabel("Group");
		for(int i=0;i<groupIds.size();i++){
			namespace.getNameOfNode(groupIds.get(i));
		}
		return groupIds;
	}
	
	/**
	 * 
	* @Description: 创建一个组
	* @param groupName：组名
	* @param createUserName：用户名
	* @return：成功返回0，失败返回-1；
	 */
	public Integer createGroup(String groupName,String createUserName){
		if(groupName.equals("")||createUserName.equals("")){
			System.out.println("创建组失败：组名和用户名均不能为空！");
			return -1;
		}
		String timeString = CommonTool.getTime();
		//查看组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
//		System.out.println(groupId);
		if(groupId!=-1){
			System.out.println("创建组失败：名字为"+groupName+"的组已经存在！");
			return -1;
		}else{
			//查看用户是否存在
			int userId = namespace.getNodeWithLabelAndProperty("User", "name", createUserName);
			if(userId == -1){
				System.out.println("创建组失败：用户"+createUserName+"不存在！");
				return -1;
			}else{
				String propsString = "{\"name\": \""+groupName+"\", \"createTime\": \""+timeString+
						"\", \"createUserName\":\""+createUserName+"\"}";
				List<Integer> groupIds = namespace.createNodeWithProperties("Group", propsString);
				int groupId2 = groupIds.get(0);
				//relationship.createRelationshipBetweenTwoNode(groupId2, userId, "contains", "{}");
				relationship.createRelationshipBetweenTwoNode(userId, groupId2, "create", "{}");
				System.out.println("创建组成功");
				return 0;
			}
		}
	}
	
	/**
	 * 
	* @Description: 解散一个组（删除组的所有关系，由组的建立者发起，慎用！）
	* @param groupName：组名
	* @param userName：用户名
	* @return：成功返回0，失败返回-1
	 */
	public Integer deleteGroup(String groupName,String userName){
		if(groupName.equals("")||userName.equals("")){
			System.out.println("解散组失败：组名和用户名不能为空！");
			return -1;
		}
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
//		System.out.println(groupId);
		if(groupId == -1){
			System.out.println("解散组成功：该组本来就不存在！");
			return 0;
		}else {
			String relationshipTypeString = relationship.getRelationshipTypeFromUserToGroup(userName, groupName);
			if(!relationshipTypeString.equals("create")){
				System.out.println("解散组失败：用户"+userName+"不是改组的创建者！");
				return -1;
			}else{
				List<String> relationshipTypes = new ArrayList<String>();
				relationshipTypes.add("contains");
				relationshipTypes.add("friend");
				relationshipTypes.add("like");
				relationshipTypes.add("create");
				//删除该组节点的所有关系
				relationship.deleteRelationshipOfNode(groupId, "all", relationshipTypes);
				//删除组节点
				namespace.deleteNode(groupId);
				System.out.println("解散组成功：该组被成功解散");
				return 0;
			}
		}
	}

	
	/**
	 * 
	* @Description: 列出一个组包含的所有用户
	* @param groupName
	* @return：
	 */
	public List<String> listAllUsersInGroup(String groupName){
		List<String> users = new ArrayList<String>();
		if(groupName.equals("")){
			System.out.println("失败：组名不能为空！");
			return users;
		}
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId == -1){
			System.out.println("失败：该组不存在");
			return users;
		}else {
			List<String> relationshipTypes = new ArrayList<>();
			relationshipTypes.add("contains");
			relationshipTypes.add("create");
			
			List<Integer>nodeIds = relationship.getNodeIdHaveRelationshipWithOneNode(groupId, "all", relationshipTypes);
			System.out.println("该组的用户如下：");
			for(int i=0;i<nodeIds.size();i++){
				String userName  = namespace.getNameOfNode(nodeIds.get(i));
				System.out.println(userName);
				users.add(userName);
			}
			return users;
		}
	}
	
	/**
	 * 
	* @Description: 将一个用户加入一个组
	* @param groupName
	* @param userName：
	* @return：成功返回0，失败返回-1
	 */
	public Integer addUserToGroup(String groupName, String userName){
		if(groupName.equals("")||userName.equals("")){
			System.out.println("加入组失败：组名和用户名不能为空！");
			return -1;
		}
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
//		System.out.println(groupId);
		if(groupId == -1){
			System.out.println("加入组失败：该组不存在！");
			return -1;
		}else {
			int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
			if(userId == -1){
				System.out.println("加入组失败：用户不存在！");
				return -1;
			}else{
				String timeString = CommonTool.getTime();
				//在组和用户之间建立contains关系
				relationship.createRelationshipBetweenTwoNode(groupId, userId, "contains", "{\"time\" : \""+timeString+"\"}");
				System.out.println("加入组成功");
				return 0;
			}
		}
	}
	
	/**
	 * 
	* @Description: 用户退出一个组
	* @param groupName：组名
	* @param userName：用户名
	* @return：
	 */
	public void deleteUserFromGroup(String groupName, String userName){
		
	}
	
}
