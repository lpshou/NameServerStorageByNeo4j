package com.b409.nameServer.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.b409.nameServer.common.config;
import com.b409.nameServer.common.generateJson;
import com.b409.nameServer.service.IOperationForRelationship;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class operationForRelationship implements IOperationForRelationship,config {
	public List<String> getRelationshipTypeBetweenTwoNode(String nameNode1,String nameNode2){
		List<String> strList = new ArrayList<String>();
				final String cypherUri = SERVER_ROOT_URI + "cypher";

				WebResource resource = Client.create().resource(cypherUri);
				String cypherString = 
				
				String cypherJson = generateJson.generateJsonCypher(cypherString, nameNode1, nameNode2)

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
		return strList;

	}

}
