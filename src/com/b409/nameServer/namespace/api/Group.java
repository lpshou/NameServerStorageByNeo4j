package com.b409.nameServer.namespace.api;

import java.util.ArrayList;
import java.util.List;


import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.serviceImpl.NamespaceImpl;
import com.b409.nameServer.serviceImpl.RelationshipImpl;

public class Group {
	//查看所有的组：public List<Integer> listAllGroup()
	//创建一个组：public void createGroup(String groupName)
	//解散一个组：public void deleteGroup(String groupName)
	//列出一个组包含的所有用户：listAllUsersInGroup
	static NamespaceImpl namespace = new NamespaceImpl();
	static RelationshipImpl relationship = new RelationshipImpl();
	

	/**
	 * 
	* @Description: 查看所有的组
	* @return：所有的组 
	 */
	public static List<Integer> listAllGroup(){
		List<Integer> groupIds = new ArrayList<Integer>();
		groupIds = namespace.getAllNodesWithLabel("Group");
		if(groupIds.size() == 0){
			System.out.println("目前没有任何组存在");
			return groupIds;
		}
		System.out.println("所有的组如下所示：");
		for(int i=0;i<groupIds.size();i++){
			String groupName = namespace.getNameOfNode(groupIds.get(i));
			System.out.println(groupName);
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
	public static Integer createGroup(String groupName,String createUserName){
		if(groupName.equals("")||createUserName.equals("")){
			System.out.println("创建组"+groupName+"失败：组名和用户名均不能为空！");
			return -1;
		}
		String timeString = CommonTool.getTime();
		//查看组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId != -1){
			System.out.println("创建组"+groupName+"失败：名字为"+groupName+"的组已经存在！");
			return -1;
		}
		//查看用户是否存在
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", createUserName);
		if(userId == -1){
			System.out.println("创建组"+groupName+"失败：用户"+createUserName+"不存在！");
			return -1;
		}else{
			//创建一个新节点
			String propsString = "{\"name\": \"" + groupName +
					"\", \"displayName\": \"" + groupName +
					"\", \"createTime\": \"" + timeString +
					"\", \"createUserName\": \"" + createUserName+"\"}";
			int newGroupId = namespace.createNodeWithProperties("Group", propsString);
			//建立创建者和组之间的关系
			String nowTimeString = "{\"createTime\": \"" + timeString + "\"}";
			relationship.createRelationshipBetweenTwoNode(userId, newGroupId, "create", nowTimeString);
			System.out.println("创建组"+groupName+"成功");
			return 0;
		}
	}
	
	/**
	 * 
	* @Description: 解散一个组（删除组的所有关系，由组的建立者发起，慎用！）
	* @param groupName：组名
	* @param userName：用户名
	* @return：成功返回0，失败返回-1
	 */
	public static Integer deleteGroup(String groupName,String userName){
		//判断参数
		if(groupName.equals("")||userName.equals("")){
			System.out.println("解散组"+groupName+"失败：组名和用户名不能为空！");
			return -1;
		}
		//判断组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId == -1){
			System.out.println("解散组"+groupName+"失败：该组本来就不存在！");
			return -1;
		}
		//判断用户是否存在
		int userId = namespace.getNodeWithLabelAndProperty("User", "name", userName);
		if(userId == -1){
			System.out.println("解散组"+groupName+"失败：用户不存在"+userName+"不存在！");
			return -1;
		}
		//判断用户userName是否为组groupName的创建者
		String relationshipTypeString = relationship.getRelationshipTypeFromUserToGroup(userName, groupName);
		if(!relationshipTypeString.equals("create")){
			System.out.println("解散组"+groupName+"失败：用户"+userName+"不是改组的创建者！");
			return -1;
		}
		
		List<String> relationshipTypes = new ArrayList<String>();
		relationshipTypes.add("contains");
		relationshipTypes.add("friend");
		relationshipTypes.add("like");
		relationshipTypes.add("create");
		//删除该组节点的所有关系
		relationship.deleteRelationshipOfNode(groupId, "all", relationshipTypes);
		//删除组节点
		namespace.deleteNode(groupId);
		System.out.println("解散组"+groupName+"成功：该组被成功解散");
		return 0;
	}

	
	/**
	 * 
	* @Description: 列出一个组包含的所有用户
	* @param groupName:组名
	* @return：该组下的所有用户的id
	 */
	public static List<Integer> listAllUsersInGroup(String groupName){
		List<Integer> users = new ArrayList<Integer>();
		//判断参数
		if(groupName.equals("")){
			System.out.println("失败：组名不能为空！");
			return users;
		}
		//判断组是否存在
		int groupId = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		if(groupId == -1){
			System.out.println("失败：该组"+groupName+"不存在");
			return users;
		}
		//获取所有组用户，因为组是由用户创建的，所以组下肯定会有用户
		List<String> relationshipTypes = new ArrayList<>();
		relationshipTypes.add("contains");
		relationshipTypes.add("create");
		List<Integer>nodeIds = relationship.getNodeIdsHaveRelationshipWithOneNode(groupId, "all", relationshipTypes);

		System.out.println("该组"+groupName+"的用户如下：");
		for(int i=0;i<nodeIds.size();i++){
			int userId = nodeIds.get(i);
			String userName  = namespace.getNameOfNode(nodeIds.get(i));
			System.out.print(userName);
			users.add(userId);
		}
		System.out.println();
		return users;

	}

}
