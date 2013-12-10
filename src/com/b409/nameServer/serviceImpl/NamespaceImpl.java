package com.b409.nameServer.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.CommonTool;
import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.common.JerseyClient;
import com.b409.nameServer.service.NamespaceInterface;

public class NamespaceImpl implements NamespaceInterface, Config {

	// 节点相关操作
	// -----------------------------------------------------------------------------------------------------------------------
	/**
	* @Description: 创建节点(一个节点参数：("user", "{}")  )
	* @param label：节点的label
	* @param props：属性值props为json串eg：{"a":"b"}，可以为空eg：{}
	* @return	  ：返回值为创建的节点的id
	 */
	public Integer createNodeWithProperties(String label, String props) {
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString = "";
		switch (label.toUpperCase()) {
		case "GROUP":
			jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties(
					"group", props);
			break;
		case "USER":
			jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties(
					"user", props);
			break;
		case "DIRECTORY":
			jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties(
					"directory", props);
			break;
		case "FILE":
			jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties(
					"file", props);
			break;
		}
		String data = JerseyClient.sendToServer(uri, jsonString, "post");

		// 解析返回值，获得新创建的节点的URI
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		// System.out.println(jsonArray);
		JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(0));
		JSONObject  jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
		String nodeUri = jsonObject2.getString("self");
		int nodeId = CommonTool.getNodeIdFromNodeUri(nodeUri);
//		System.out.println("新创建节点的id为："+nodeId);
		return nodeId;
	}

	/**
	 * 
	 * @Description: 删除一个节点
	 * @param nodeId
	 *            :节点id
	 * @return：
	 */
	public void deleteNode(int nodeId) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId;
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");
	}

	/**
	 * 
	 * @Description: 获取具有某个label的所有node
	 * @param label
	 *            :label名
	 * @return：节点id
	 */
	public List<Integer> getAllNodesWithLabel(String label) {
		List<Integer> nodeIds = new ArrayList<Integer>();
		nodeIds.clear();
		String uriString = SERVER_ROOT_URI + "label/" + label + "/nodes";
		// System.out.println(uriString);
		String data = JerseyClient.sendToServer(uriString, "{}", "get");
		// System.out.println(data);
		JSONArray jsonArray = JSONArray.fromObject(data);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String nodeUri = jsonObject.getString("self");
			int nodeId = CommonTool.getNodeIdFromNodeUri(nodeUri);
			nodeIds.add(nodeId);
		}
		// for(int i=0;i<nodeIds.size();i++)
		// System.out.println(nodeIds.get(i));
		return nodeIds;
	}

	/**
	 * 
	 * @Description: 根据label和属性查找某个节点（只支持单属性查找）
	 * @param label
	 * @param propertyName
	 *            ：属性名
	 * @param propertyValue
	 *            ：属性值
	 * @return：节点不存在返回-1，节点存在返回节点值
	 */
	public Integer getNodeWithLabelAndProperty(String label,
			String propertyName, String propertyValue) {
		String uriString = SERVER_ROOT_URI + "label/" + label + "/nodes?"
				+ propertyName + "=%22" + propertyValue + "%22";
		String dataString = JerseyClient.sendToServer(uriString, "{}", "get");
		JSONArray jsonArray = JSONArray.fromObject(dataString);
		int nodeId = -1;
		if (jsonArray.size() != 0) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(0));
			String nodeUriString = jsonObject.getString("self");
			nodeId = CommonTool.getNodeIdFromNodeUri(nodeUriString);
		}
		return nodeId;
	}

	// ------------------------------------------------------------------------------------------------------------------------

	// 节点属性相关
	// ------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description: 获取节点信息（包括属性、id、uri等等）
	 * @param nodeId
	 *            :节点的id号
	 * @return：节点的信息，为json串
	 */
	public String getMessageOfNode(int nodeId) {
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString = GenerateJson
				.generateJsonForGetNodeProperties(nodeId);
		// System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		return data;
	}

	/**
	 * 
	 * @Description: 获取节点具体属性
	 * @param nodeId
	 * @return：返回值为json串形式（属性：属性值）
	 */
	public String getPropertiesOfNode(int nodeId) {
		String dataString = getMessageOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String data = jsonObject.getString("data");
		// System.out.println(data);
		JSONArray array = JSONArray.fromObject(data);
		String data1 = array.getString(0);
		JSONArray array2 = JSONArray.fromObject(data1);
		String data2 = array2.getString(0);
		JSONObject jsonObject2 = JSONObject.fromObject(data2);
		String result = jsonObject2.getString("data");

		// System.out.println(result);
		return result;
	}

	/**
	 * 
	 * @Description: 获取节点有哪些属性
	 * @param nodeId
	 * @return：节点的属性（属性）
	 */
	public List<String> getPropertiesOfKeyOfNode(int nodeId) {

		List<String> resultList = new ArrayList<>();
		String jsonString = getPropertiesOfNode(nodeId);
		Map<String, String> map = CommonTool.parserFromJsonToMap(jsonString);
		Set<Map.Entry<String, String>> sets = map.entrySet();
		for (Map.Entry<String, String> entry : sets) {
			String strTemp = entry.getKey();
			resultList.add(strTemp);
		}
		for (int i = 0; i < resultList.size(); i++)
			System.out.println(resultList.get(i));
		return resultList;
	}

	/**
	 * 
	 * @Description: 根据节点id获得节点name
	 * @param nodeId
	 * @return：
	 * @return：
	 */
	public String getNameOfNode(int nodeId) {
		String dataString = getPropertiesOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String resultString = jsonObject.getString("name");
		return resultString;
	}

	/**
	 * 
	 * @Description: 根据节点id获得fileLocation
	 * @param nodeId
	 *            ：节点id
	 * @return：
	 */
	public String getFileLocationOfNode(int nodeId) {
		String dataString = getPropertiesOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String resultString = jsonObject.getString("fileLocation");
		// System.out.println(resultString);
		return resultString;
	}

	/**
	 * 
	 * @Description: 设置节点属性，将完全替换原有属性
	 * @param nodeId
	 * @param props
	 *            ：props为json串
	 * @return：
	 */
	public void setAllPropertiesOnNode(int nodeId, String props) {
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString = GenerateJson.jenerateJsonForSetProperties(nodeId,
				props);
		System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		System.out.println(data);
	}

	/**
	 * 
	 * @Description: 更新节点某个属性值,其他属性值不变(将属性key的值改为value)
	 * @param nodeUri
	 * @param key
	 *            :要更新的属性
	 * @param value
	 *            ：更新为的属性值
	 * @return：
	 */
	public void updateOnePropertyOnNode(String nodeUri, String key, String value) {
		String uri = nodeUri + "/properties/" + key;
		String dataString = JerseyClient.sendToServer(uri, value, "put");
	}

	/**
	 * 
	 * @Description: 删除一个节点的所有属性
	 * @param nodeId
	 *            ：
	 * @return：
	 */
	public void deleteAllPropertiesOnNode(int nodeId) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/properties";
		// System.out.println(""+nodeUri);
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");
		System.out.println(data);
	}

	/**
	 * 
	 * @Description: 删除一个节点的某个属性
	 * @param nodeId
	 *            ：
	 * @return：
	 */
	public void deleteOnePropertyOnNode(int nodeId, String propertyName) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/properties/"
				+ propertyName;
		System.out.println("" + nodeUri);
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");
		System.out.println(data);

	}

	// -----------------------------------------------------------------------------------------------------------------------

	
	// 节点label的相关操作
	// -----------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description: 给节点增加labels
	 * @param nodeId
	 *            ：节点id
	 * @param labels
	 *            ：要增加的labels,
	 * @return：
	 */
	public void addLabelsToNode(int nodeId, List<String> labels) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/labels";
		String labelsString = null;
		if (labels.size() == 0)
			labelsString = "";
		else if (labels.size() == 1) {
			labelsString = labels.get(0);
		} else {
			JSONArray array = new JSONArray();
			for (int i = 0; i < labels.size(); i++) {
				array.add(labels.get(i));
			}
			labelsString = array.toString();
		}
		System.out.println(nodeUri);
		System.out.println(labelsString);
		JerseyClient.sendToServer(nodeUri, labelsString, "post");
	}

	/**
	 * 
	* @Description: 替换节点的labels(备注：首次成功，但是最后有问题，有待进一步处理......)
	* 				（备注2：问题已经解决，是每次的response没有关闭的缘故）
	* @param nodeId
	* @param labels：替换为labels
	* @return：
	 */
	public void replaceLabelsOnNode(int nodeId, List<String> labels) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/labels";
		String labelsString = null;
		if (labels.size() == 0)
			labelsString = "";
		else {

			JSONArray array = new JSONArray();
			for (int i = 0; i < labels.size(); i++) {
				array.add(labels.get(i));
			}
			labelsString = array.toString();
		}
		System.out.println(nodeUri);
		System.out.println(labelsString);

		JerseyClient.sendToServer(nodeUri, labelsString, "put");
	}

	/**
	 * 
	 * @Description: 删除节点上的某个label
	 * @param nodeId
	 * @param label
	 *            ：
	 * @return：
	 */
	public void removeOneLabelOnNode(int nodeId, String label) {

		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/labels/"
				+ label;
		System.out.println(nodeUri);
		JerseyClient.sendToServer(nodeUri, "{}", "delete");

	}

	/**
	 * 
	 * @Description: 列出一个节点的所有labels
	 * @param nodeId
	 * @return：
	 */
	public List<String> listAllLabelsOfNode(int nodeId) {
		String nodeUri = SERVER_ROOT_URI + "node/" + nodeId + "/labels";
		// System.out.println(nodeUri);
		String labels = JerseyClient.sendToServer(nodeUri, "{}", "get");
		// System.out.println(labels);

		List<String> labelsList = new ArrayList<String>();
		JSONArray jsonArray = JSONArray.fromObject(labels);
		for (int i = 0; i < jsonArray.size(); i++) {
			labelsList.add(jsonArray.getString(i));
		}
		// for(int i=0;i<labelsList.size();i++)
		// System.out.println(labelsList.get(i));
		return labelsList;
	}

	/**
	 * 
	 * @Description: 列出图中所有的labels
	 * @return：
	 */
	public List<String> listAllLabelsInGraph() {
		String nodeUri = SERVER_ROOT_URI + "labels";
		System.out.println(nodeUri);
		String labels = JerseyClient.sendToServer(nodeUri, "{}", "get");
		// System.out.println(labels);

		List<String> labelsList = new ArrayList<String>();
		JSONArray jsonArray = JSONArray.fromObject(labels);
		for (int i = 0; i < jsonArray.size(); i++) {
			labelsList.add(jsonArray.getString(i));
		}

		for (int i = 0; i < labelsList.size(); i++)
			System.out.println(labelsList.get(i));
		return labelsList;
	}

	// ----------------------------------------------------------------------------------------------------------------------

	// index相关操作
	// ----------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description: 列出一个label的所有index
	 * @param label
	 *            ：
	 * @return：
	 */
	public List<String> listIndexsForLabel(String label) {
		String uriString = SERVER_ROOT_URI + "schema/index/" + label;
		System.out.println(uriString);
		String data = JerseyClient.sendToServer(uriString, "{}", "get");
		System.out.println(data);
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<String> indexsList = new ArrayList<String>();
		if (jsonArray.size() == 0) {
			// return indexsList;
		} else {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray
						.getString(i));
				JSONArray propertyKeyString = JSONArray.fromObject(jsonObject
						.getString("property-keys"));
				indexsList.add(propertyKeyString.getString(0));
			}
		}

		for (int i = 0; i < indexsList.size(); i++)
			System.out.println(indexsList.get(i));
		return indexsList;
	}

	/**
	 * 
	 * @Description: 删掉一个label上的某个index
	 * @param labelName
	 * @param indexName
	 *            ：
	 * @return：
	 */
	public void dropOneIndexFromLabel(String labelName, String indexName) {
		String uriString = SERVER_ROOT_URI + "schema/index/" + labelName + "/"
				+ indexName;
		System.out.println(uriString);
		JerseyClient.sendToServer(uriString, "{}", "delete");
	}

	/**
	 * 
	 * @Description: 在一个label上增加一个index
	 * @param labelName
	 * @param indexName
	 *            ：
	 * @return：
	 */
	public void CreateOneIndexOnLabel(String labelName, String indexName) {
		String uriString = SERVER_ROOT_URI + "schema/index/" + labelName;
		String jsonString = GenerateJson
				.generateJsonForCreateIndexOnLabel("name");
		System.out.println(jsonString);
		JerseyClient.sendToServer(uriString, jsonString, "post");
	}

	// ----------------------------------------------------------------------------------------------------------------------

}
