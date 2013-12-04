package com.b409.nameServer.common;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient {
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
		int status = 0;
		String dataString = "";
	
		
		switch (typeString) {
		case "post": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.entity(jsonString)
					.post(ClientResponse.class);
			status = response.getStatus();
			String reminderString ="";
			switch(status){
			case 201:reminderString="创建成功";break;
			case 200:reminderString="sucess!";break;
			
			}
			System.out.println(reminderString);
			dataString = response.getEntity(String.class);
			break;
		}
		case "get": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			status = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		case "put": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.entity(jsonString)
					.put(ClientResponse.class);
			status = response.getStatus();
			dataString = response.getEntity(String.class);
			break;
		}
		case "delete": {
			response = resource

					.delete(ClientResponse.class);
			status = response.getStatus();
			String reminderString="";
			switch(status){
			case 204:reminderString="删除成功！";break;
			case 404:reminderString="要删除的节点不存在！";break;
			case 409:reminderString="节点有关系存在，请先删除关系";break;
			}
			System.out.println(reminderString);
			break;

		}
		}
		
		System.out.println(String.format(
				"Send to: [%s] \n status code: [%d]",
				uri, status));
		return dataString;

	}
}
