package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.neo4j.example.helloworld.Relationship;
import org.neo4j.example.helloworld.TraversalDescription;

import com.b409.nameServer.service.IbaseOperation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class baseOperation implements IbaseOperation {

	// 测试数据库是否正常运行（get）
	public boolean databaseIsRunning() {
		WebResource resource = Client.create().resource(SERVER_ROOT_URI);
		ClientResponse response = resource.get(ClientResponse.class);

		int status = response.getStatus();
		if (status == 200)
			return true;
		else
			return false;
	}

	// 创建一个节点（post）
	@Override
	public URI createNode() {
		// http://192.168.0.187:7474/db/data/node
		final String nodeEntryPointUri = SERVER_ROOT_URI + "node";

		WebResource resource = Client.create().resource(nodeEntryPointUri);

		// POST {} to the node entry point URI
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity("{}")
				.post(ClientResponse.class);

		final URI location = response.getLocation();
		System.out.println(String.format(
				"POST to [%s],   location header [%s]", nodeEntryPointUri,
				location.toString()));
		response.close();

		return location;
	}

	// 为节点添加属性（put）
	@Override
	public boolean addPropertyToNode(URI nodeUri, String propertyName,
			String propertyValue) {
		// http://192.168.0.187:7474/db/data/node/{node_id}/properties/{property_name}
		String propertyUri = nodeUri.toString() + "/properties/" + propertyName;

		WebResource resource = Client.create().resource(propertyUri);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.entity("\"" + propertyValue + "\"").put(ClientResponse.class);

		System.out.println(String.format("PUT to [%s], status code [%d]",
				propertyUri, response.getStatus()));
		response.close();

		return true;
	}

	// 在两个节点之间建立关系（post）
	public URI addRelationshipBetweenTwoNodes(URI startNode, URI endNode,
			String relationshipType, String jsonAttributes)
			throws URISyntaxException {

		URI fromUri = new URI(startNode.toString() + "/relationships");
		String relationshipJson = generateJsonRelationship(endNode,
				relationshipType, jsonAttributes);

		WebResource resource = Client.create().resource(fromUri);

		// POST JSON to the relationships URI
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(relationshipJson)
				.post(ClientResponse.class);

		final URI location = response.getLocation();
		System.out.println(String.format(
				"POST to [%s], status code [%d], location header [%s]",
				fromUri, response.getStatus(), location.toString()));

		response.close();
		return location;
	}

	public String generateJsonRelationship(URI endNode,
			String relationshipType, String... jsonAttributes) {

		StringBuilder sb = new StringBuilder();
		sb.append("{ \"to\" : \"");
		sb.append(endNode.toString());
		sb.append("\", ");

		sb.append("\"type\" : \"");
		sb.append(relationshipType);
		if (jsonAttributes == null || jsonAttributes.length < 1) {
			sb.append("\"");
		} else {
			sb.append("\", \"data\" : ");
			for (int i = 0; i < jsonAttributes.length; i++) {
				sb.append(jsonAttributes[i]);
				if (i < jsonAttributes.length - 1) { // Miss off the final comma
					sb.append(", ");
				}
			}
		}

		sb.append(" }");
		return sb.toString();
	}

	// 给一个关系添加属性（put）
	public void addPropertyToRelationship(URI relationshipUri, String name,
			String value) throws URISyntaxException {

		URI propertyUri = new URI(relationshipUri.toString() + "/properties");
		String entity = toJsonNameValuePairCollection(name, value);
		WebResource resource = Client.create().resource(propertyUri);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(entity)
				.put(ClientResponse.class);

		System.out.println(String.format("PUT [%s] to [%s], status code [%d]",
				entity, propertyUri, response.getStatus()));
		response.close();
	}

	public String toJsonNameValuePairCollection(String name, String value) {
		return String.format("{ \"%s\" : \"%s\" }", name, value);

	}

	// TODO
	// 查询（post）
	public List<URI> findNodeWithRelationshipInDepth(URI startNode,
			String relationship, int depth, String direction)
			throws URISyntaxException {
		// TraversalDescription turns into JSON to send to the Server
		TraversalDescription t = new TraversalDescription();
		t.setOrder(TraversalDescription.DEPTH_FIRST);
		t.setUniqueness(TraversalDescription.NODE);
		t.setMaxDepth(depth);
		t.setReturnFilter(TraversalDescription.ALL);
		if (direction.toUpperCase().equals("OUT")) {
			t.setRelationships(new Relationship(relationship, Relationship.OUT));
		} else if (direction.toUpperCase().equals("IN")) {
			t.setRelationships(new Relationship(relationship, Relationship.IN));
		} else {
			t.setRelationships(new Relationship(relationship, Relationship.BOTH));
		}
		// END SNIPPET: traversalDesc

		// START SNIPPET: traverse
		URI traverserUri = new URI(startNode.toString() + "/traverse/node");
		WebResource resource = Client.create().resource(traverserUri);
		String jsonTraverserPayload = t.toJson();
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).entity(jsonTraverserPayload)
				.post(ClientResponse.class);

		// 得到返回信息
		String msgReturn = response.getEntity(String.class);

		// System.out.println( String.format(
		// "POST [%s] to [%s], status code [%d], returned data: "
		// + System.getProperty( "line.separator" ) + "%s",
		// jsonTraverserPayload, traverserUri, response.getStatus(),
		// //response.getEntity( String.class ) ) );
		// msgReturn) );

		response.close();
		if (msgReturn.equals("[ ]")) {

			System.out.println("value is null");
			return null;
		} else {
			List<URI> uris = new ArrayList<URI>();
			JSONArray array = JSONArray.fromObject(msgReturn);
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = JSONObject.fromObject(array.get(i));
				URI uri = new URI(object.getString("self"));
				uris.add(uri);
			}
			System.out.println("Test: \n");
			return uris;
		}

	}
	public URI startTransaction(String cypherString){
		final String transactionUri = SERVER_ROOT_URI + "transaction";


				WebResource resource = Client.create().resource(transactionUri);

				String transactionJson = generateJson.generateJsonTransaction(cypherString);
				System.out.println(transactionJson);
				// POST {} to the node entry point URI
				ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).entity(transactionJson)
						.post(ClientResponse.class);

				final URI location = response.getLocation();
				System.out.println(String.format(
						"POST to [%s],   location header [%s]", transactionUri,
						location.toString()));
				response.close();

		
		return location;
	}
	
	//执行一个事务
	public URI executeTransaction(URI transactionUri,String cypherString) throws URISyntaxException {
//
				WebResource resource = Client.create().resource(transactionUri);
				String transactionJson = generateJson.generateJsonTransaction(cypherString);
				System.out.println(transactionJson);


				ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.entity(transactionJson).post(ClientResponse.class);

				System.out.println(String.format("Post to [%s], status code [%d]",
						transactionUri, response.getStatus()));
				String str = response.getEntity(String.class);
				JSONObject jsonObject = JSONObject.fromObject(str);
				String strTemp = jsonObject.getString("commit");
				System.out.println(strTemp);
				response.close();
				
				return new URI(strTemp);
	}
	
	//提交一个事务，可以执行cypher
	public void commitTransaction(URI transactionUri,String cypherString){
		
		WebResource resource = Client.create().resource(transactionUri);
		String transactionJson = generateJson.generateJsonTransaction(cypherString);
		System.out.println(transactionJson);


		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.entity(transactionJson).post(ClientResponse.class);

		System.out.println(String.format("Post to [%s], status code [%d]",
				transactionUri, response.getStatus()));
		
		response.close();
		
	}



}
