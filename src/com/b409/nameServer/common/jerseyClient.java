package com.b409.nameServer.common;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class jerseyClient {
	/**
	* @Description: client发送请求
	* @param uri：请求目的地址
	* @param jsonString：请求中的参数
	* @param typeString：请求类型，get、put、post、delete
	* @return： String    返回值
	 */
	public static String sendToServer(String uri, String jsonString,
			String typeString) {
		WebResource resource = Client.create().resource(uri);
		ClientResponse response;
		int statusString = 0;
		String dataString = "";
	
		
		switch (typeString) {
		case "post": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).entity(jsonString)
					.post(ClientResponse.class);
			statusString = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		case "get": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).entity(jsonString)
					.get(ClientResponse.class);
			statusString = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		case "put": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).entity(jsonString)
					.put(ClientResponse.class);
			statusString = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		case "delete": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).entity(jsonString)
					.delete(ClientResponse.class);
			statusString = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		}
		
		System.out.println(String.format(
				"Send to: [%s] \n status code: [%d]",
				uri, statusString));
		return dataString;

	}
}
