package com.b409.nameServer.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface IbaseOperation {

	// 基本常量
	//public static final String SERVER_ROOT_URI = "http://192.168.0.187:7474/db/data/";

	// 测试数据库是否正常运行（get）
	public boolean databaseIsRunning();

	// 创建一个节点（post）
	public URI createNode();

	// 为节点添加属性
	public boolean addPropertyToNode(URI nodeUri, String propertyName,
			String propertyValue);

	// 在两个节点之间建立关系（post）
	public URI addRelationshipBetweenTwoNodes(URI startNode, URI endNode,
			String relationshipType, String jsonAttributes)
			throws URISyntaxException;


	// 给一个关系添加属性（put）
	public void addPropertyToRelationship(URI relationshipUri, String name,
			String value) throws URISyntaxException;

	public String toJsonNameValuePairCollection(String name, String value);

	// 查询从与一个节点有关系的其他节点
	/**
	 * 
	 * @Title: findNodeWithRelationshipInDepth
	 * @Description: 查询从与一个节点有关系的其他节点
	 * @param startNode
	 *            :起始节点
	 * @param relationship
	 *            ：节点关系
	 * @param depth
	 *            ：深度，即关系深度
	 * @param direction
	 *            ：方向，in:从其他节点到起始节点，out：从起始节点到其他节点，both：两个方向都有
	 * @throws URISyntaxException
	 *             ：异常
	 * @return： List<URI> 返回其他节点
	 */
	public List<URI> findNodeWithRelationshipInDepth(URI startNode,
			String relationship, int depth, String direction)
			throws URISyntaxException;
	
	//提交一个事务并执行其中cypher
	public void beginAndCommitTransaction(String cypherString)throws URISyntaxException;
	
}
