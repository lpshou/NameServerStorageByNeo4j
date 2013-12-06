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

public class NamespaceImpl implements NamespaceInterface,Config {
	
	
	//节点相关操作
	//----------------------------------------------------------------------------------------------------
	//创建一个节点，属性值props为json串eg：{"a":"b"},返回值为创建的节点的uri
	public List<String> createNodeWithProperties(String label, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString="";
		switch(label.toUpperCase()){
		case "GROUP":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("group",props);break;
		case "USER":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("user",props);break;
		case "DIRECTORY":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("directory",props);break;
		case "FILE":jsonString = GenerateJson.jenerateJsonForCreateNodeWithProperties("file",props);break;
		}
		
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		//System.out.println(data);
		
		//解析返回值，获得新创建的节点的URI
		List<String>uris = new ArrayList<String>();
		JSONObject jsonObject = JSONObject.fromObject(data);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
		//System.out.println(jsonArray);
		for(int i=0;i<jsonArray.size();i++){
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i));
			//System.out.println("jsonArray2:"+jsonArray2);
			JSONObject jsonObject2 = JSONObject.fromObject(jsonArray2.get(0));
			String uriTemp = jsonObject2.getString("self");
			uris.add(uriTemp);
		}
		
		for(int j=0;j<uris.size();j++)
			System.out.println(uris.get(j));
		return uris;
	}
	
	
	//删除一个节点
	public void deleteNode(int nodeId){
		String nodeUri = SERVER_ROOT_URI+"node/"+nodeId;
		System.out.println("haha"+nodeUri);
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");	
		System.out.println(data);
	}
	
	/**
	 * 
	* @Description: 获取具有某个label的所有node
	* @param label
	* @return：
	 */
	public List<Integer> getAllNodesWithLabel(String label){
		List<Integer> nodeIds = new ArrayList<Integer>();
		nodeIds.clear();
		String uriString = SERVER_ROOT_URI + "label/" + label + "/nodes";
		System.out.println(uriString);
		String data  = JerseyClient.sendToServer(uriString, "{}", "get");
//		System.out.println(data);
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String nodeUri = jsonObject.getString("self");
			int nodeId = CommonTool.getNodeIdFromNodeUri(nodeUri);
			nodeIds.add(nodeId);
		}
//		for(int i=0;i<nodeIds.size();i++)
//			System.out.println(nodeIds.get(i));
		
		return nodeIds;
	}
	
	/**
	 * 
	* @Description: 根据label和属性查找某个节点（只支持单属性查找）
	* @param label
	* @param propertyName：属性名
	* @param propertyValue：属性值
	* @return：
	 */
	public List<Integer> getNodeWithLabelAndProperty(String label, String propertyName,String propertyValue){
		List<Integer>nodes = new ArrayList<Integer>();
		String uriString = SERVER_ROOT_URI + "label/" + label + "/nodes?" + propertyName + "=%22" + propertyValue + "%22";
		System.out.println(uriString);
		String dataString = JerseyClient.sendToServer(uriString, "{}", "get");
		//System.out.println(dataString);
		JSONArray jsonArray = JSONArray.fromObject(dataString);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String nodeUriString = jsonObject.getString("self");
			int nodeId = CommonTool.getNodeIdFromNodeUri(nodeUriString);
			nodes.add(nodeId);
		}
		for(int i=0;i<nodes.size();i++)
			System.out.println(nodes.get(i));
		
		return nodes;
	}
	
	//------------------------------------------------------------------------------------------------
	
	
	//节点属性相关
	//------------------------------------------------------------------------------------------------
	//设置节点属性
	public void setAllPropertiesOnNode(int nodeId, String props){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=GenerateJson.jenerateJsonForSetProperties(nodeId, props);
		System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		System.out.println(data);
	}
	
	//更新节点某个属性值,其他属性值不变
	public void updateOnePropertyOnNode(String nodeUri,String key,String value){
		String uri = nodeUri+"/properties/"+key;
		String dataString = JerseyClient.sendToServer(uri, value, "put");
	}
	
	//获取节点信息（包括属性、id、uri等等）
	public String getMessageOfNode(int nodeId){
		final String uri = SERVER_ROOT_URI + "cypher";
		String jsonString=GenerateJson.generateJsonForGetNodeProperties(nodeId);
		System.out.println(jsonString);
		String data = JerseyClient.sendToServer(uri, jsonString, "post");
		
		System.out.println(data);
		return data;
	}
	
	//获取节点有哪些属性
	public List<String> getPropertiesOfKeyOfNode(int nodeId){
		
		List<String> resultList = new ArrayList<>();
		String jsonString = getPropertiesOfNode(nodeId);
		Map<String, String> map =CommonTool.parserFromJsonToMap(jsonString);
		Set<Map.Entry<String, String>> sets = map.entrySet();
		for(Map.Entry<String, String> entry:sets){
			String strTemp = entry.getKey();
			resultList.add(strTemp);
		}
		for(int i=0;i<resultList.size();i++)
			System.out.println(resultList.get(i));
		return resultList;
	}
	//获取节点具体属性，json串形式
	public String getPropertiesOfNode(int nodeId){
		String dataString = getMessageOfNode(nodeId);
		JSONObject jsonObject = JSONObject.fromObject(dataString);
		String data = jsonObject.getString("data");
		JSONArray array = JSONArray.fromObject(data);
		String data1 = array.getString(0);
		JSONArray array2 = JSONArray.fromObject(data1);
		String data2 = array2.getString(0);
		JSONObject jsonObject2 = JSONObject.fromObject(data2);
		String result = jsonObject2.getString("data");
		//System.out.println(result);		
		return result;
	}
	/**
	 * 
	* @Description: 删除一个节点的所有属性
	* @param nodeId：
	* @return：
	 */
	public void deleteAllPropertiesOnNode(int nodeId){
		String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/properties";
		//System.out.println(""+nodeUri);
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");	
		System.out.println(data);
	}
	
	/**
	 * 
	* @Description: 删除一个节点的某个属性
	* @param nodeId：
	* @return：
	 */
	public void deleteOnePropertyOnNode(int nodeId,String propertyName){
		String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/properties/"+propertyName;
		System.out.println(""+nodeUri);
		String data = JerseyClient.sendToServer(nodeUri, "", "delete");	
		System.out.println(data);
		
	}
	//-----------------------------------------------------------------------------------------------
	
	
	
	//节点label的相关操作
		//--------------------------------------------------------------------------------
		/**
		 * 
		* @Description: 给节点增加labels
		* @param nodeId
		* @param labels：要增加的labels
		* @return：
		 */
		public void addLabelsToNode(int nodeId,List<String>labels){
			String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/labels";
			String labelsString = null;
			if(labels.size() == 0)
				labelsString="";
			else if(labels.size()==1){
				labelsString = labels.get(0);
			}
			else{
				JSONArray array = new JSONArray();
				for(int i=0;i<labels.size();i++){
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
		* @Description: 替换节点的labels
		* @param nodeId
		* @param labels：替换为labels
		* @return：
		 */
		public void replaceLabelsOnNode(int nodeId,List<String>labels){
			String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/labels";
			String labelsString = null;
			if(labels.size() == 0)
				labelsString="";
			else{

				JSONArray array = new JSONArray();
				for(int i=0;i<labels.size();i++){
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
		* @param label：
		* @return：
		 */
		public void removeOneLabelOnNode(int nodeId,String label){
			
			String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/labels/"+label;
			System.out.println(nodeUri);
			JerseyClient.sendToServer(nodeUri, "{}", "delete");

		}
		
		/**
		 * 
		* @Description: 列出一个节点的所有labels
		* @param nodeId
		* @return：
		 */
		public List<String> listAllLabelsOfNode(int nodeId){
			String nodeUri = SERVER_ROOT_URI+"node/"+nodeId+"/labels";
			System.out.println(nodeUri);
			String labels = JerseyClient.sendToServer(nodeUri, "{}", "get");
//			System.out.println(labels);
			
			List<String> labelsList = new ArrayList<String>();
			JSONArray jsonArray = JSONArray.fromObject(labels);
			for(int i=0;i<jsonArray.size();i++){
				labelsList.add(jsonArray.getString(i));
			}
			
			for(int i=0;i<labelsList.size();i++)
				System.out.println(labelsList.get(i));
			return labelsList;
		}
		
		
		/**
		 * 
		* @Description: 列出图中所有的labels
		* @return：
		 */
		public List<String> listAllLabelsInGraph(){
			String nodeUri = SERVER_ROOT_URI+"labels";
			System.out.println(nodeUri);
			String labels = JerseyClient.sendToServer(nodeUri, "{}", "get");
//			System.out.println(labels);
			
			List<String> labelsList = new ArrayList<String>();
			JSONArray jsonArray = JSONArray.fromObject(labels);
			for(int i=0;i<jsonArray.size();i++){
				labelsList.add(jsonArray.getString(i));
			}
			
			for(int i=0;i<labelsList.size();i++)
				System.out.println(labelsList.get(i));
			return labelsList;
		}
		
		//--------------------------------------------------------------------------------
		
		
		//index相关操作
		//--------------------------------------------------------------------------------
		/**
		 * 
		* @Description: 列出一个label的所有index
		* @param label：
		* @return：
		 */
		public List<String> listIndexsForLabel(String label){
			String uriString = SERVER_ROOT_URI + "schema/index/" + label;
			System.out.println(uriString);
			String data= JerseyClient.sendToServer(uriString, "{}", "get");
			System.out.println(data);
			JSONArray jsonArray = JSONArray.fromObject(data);
			List<String> indexsList = new ArrayList<String>();
			if(jsonArray.size() == 0){
				//return indexsList;
			}else {
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
					JSONArray propertyKeyString = JSONArray.fromObject(jsonObject.getString("property-keys"));
					indexsList.add(propertyKeyString.getString(0));
				}
			}
			
			for(int i=0;i<indexsList.size();i++)
				System.out.println(indexsList.get(i));
			return indexsList;
		}
		
		/**
		 * 
		* @Description: 删掉一个label上的某个index
		* @param labelName
		* @param indexName：
		* @return：
		 */
		public void dropOneIndexFromLabel(String labelName, String indexName){
			String uriString = SERVER_ROOT_URI + "schema/index/" + labelName + "/" + indexName;
			System.out.println(uriString);
			JerseyClient.sendToServer(uriString, "{}", "delete");
		}
		
		/**
		 * 
		* @Description: 在一个label上增加一个index
		* @param labelName
		* @param indexName：
		* @return：
		 */
		public void CreateOneIndexOnLabel(String labelName, String indexName){
			String uriString = SERVER_ROOT_URI + "schema/index/" + labelName;
			String jsonString = GenerateJson.generateJsonForCreateIndexOnLabel("name");
			System.out.println(jsonString);
			JerseyClient.sendToServer(uriString, jsonString, "post");
		}
		
		
		//--------------------------------------------------------------------------------
		


}
