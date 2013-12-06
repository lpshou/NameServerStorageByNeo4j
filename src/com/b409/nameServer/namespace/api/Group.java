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
	* @param groupName：
	* @return：
	 */
	public void createGroup(String groupName){
		String timeString = CommonTool.getTime();
		List<Integer> groups = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
//		System.out.println(groups.size());
		if(groups.size()!=0){
			System.out.println("创建组失败：名字为"+groupName+"的组已经存在！");
		}else{
			String propsString = "{\"name\": \""+groupName+"\", \"createTime\": \""+timeString+"\"}";
			//System.out.println(propsString);
			namespace.createNodeWithProperties("Group", propsString);
			System.out.println("创建组成功");
		}
	}
	
	/**
	 * 
	* @Description: 解散一个组（删除组的所有关系，慎用！）（未测试）
	* @param groupName：
	* @return：
	 */
	public void deleteGroup(String groupName){
		List<Integer> groups = namespace.getNodeWithLabelAndProperty("Group", "name", groupName);
		System.out.println(groups.size());
		if(groups.size()==0){
			System.out.println("删除成功：该组本来就不存在！");
		}else {
			
			for(int i=0;i<groups.size();i++){
				List<String> labels = new ArrayList<String>();
				labels.add("contains");
				labels.add("friend");
				labels.add("like");
				List<Integer> relationshipIds = relationship.getRelationshipOfNode(groups.get(i), "out", labels);
				for(int j=0;j<relationshipIds.size();j++)
					relationship.deleteRelationship(relationshipIds.get(j));
				namespace.deleteNode(groups.get(i));
			}
			System.out.println("删除成功：该组被删除");
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
		return users;
	}
	
	/**
	 * 
	* @Description: 将一个用户加入一个组
	* @param groupName
	* @param userName：
	* @return：
	 */
	public void addUserToGroup(String groupName, String userName){
		
	}
	
	public void deleteUserFromGroup(String group, String userName){
		
	}
	
}
