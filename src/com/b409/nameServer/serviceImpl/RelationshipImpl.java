package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.b409.nameServer.common.Config;
import com.b409.nameServer.common.GenerateJson;
import com.b409.nameServer.service.IForRelationship;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RelationshipImpl implements IForRelationship,
		Config {
	public List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,
			String nameNode2) {

		final String cypherUri = SERVER_ROOT_URI + "cypher";

		String cypherString = "match (n:user) where n.name={name1} match n-[r]-friend where friend.name={name2} return r";

		System.out.println(cypherString);
		String cypherJson = GenerateJson.generateJsonCypher(cypherString,
				nameNode1, nameNode2);
		System.out.println(cypherJson);

		WebResource resource = Client.create().resource(cypherUri);

		// POST JSON to the relationships URI
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.entity(cypherJson)
				.post(ClientResponse.class);

		//final URI location = response.getLocation();
		System.out.println(String.format(
				"POST to [%s], status code [%d]",
				cypherUri, response.getStatus()));
		String strResult = response.getEntity(String.class);
		System.out.println(strResult);
		JSONObject jsonObject = JSONObject.fromObject(strResult);
		JSONArray array = JSONArray.fromObject(jsonObject.get("data"));
		List<String> strList = new ArrayList<String>();

		for(int i=0;i<array.size();i++){
			String strTemp = array.getString(i);
			strList.add(strTemp);
		}

		response.close();
		return strList;

	}

}
