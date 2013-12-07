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
			boolean flag = false;
			flag = jsonString.contains("{")||jsonString.contains("[");
//			System.out.println(flag);
			if(flag){
				response = resource
						.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.entity(jsonString)
						.post(ClientResponse.class);
			}else{
				response = resource
						.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.entity("\"" + jsonString + "\"")
						.post(ClientResponse.class);
			}
			status = response.getStatus();
			String reminderString ="";
			switch(status){
			case 201:reminderString="创建成功";break;
			case 200:reminderString="成功";break;
			case 204:reminderString="成功";break;
			case 400:reminderString="失败";break;
			
			}
//			System.out.println(String.format("POST : [%s]  status code: [%d]",uri, status));
//			System.out.println(reminderString);
			if(status != 204)
				dataString = response.getEntity(String.class);
			response.close();
			break;
		}
		case "get": {
			response = resource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			status = response.getStatus();
			dataString = response.getEntity(String.class);
//			System.out.println(String.format("GET : [%s]  status code: [%d]",uri, status));
			String reminderString="";
			switch(status){
			case 200:reminderString="成功获取";break;
			}
//			System.out.println(reminderString);
			response.close();
			break;
		}
		case "put": {
			boolean flag = false;
			flag = jsonString.contains("{")||jsonString.contains("[");
			System.out.println(flag);
			if(flag){
				response = resource
						.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.entity(jsonString)  //此处在RelationshipImpl中updateRelationship用到
						.put(ClientResponse.class);
			}else{
				response = resource
						.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.entity("\""+ jsonString+ "\"" ) 
						.put(ClientResponse.class);
			}
			String reminderString="";
			status = response.getStatus();
			switch(status){
			case 400:reminderString="失败";break;
			case 204:reminderString="成功";break;
			}
			System.out.println(String.format("PUT : [%s]  status code: [%d]",uri, status));
			System.out.println(reminderString);
			//dataString = response.getEntity(String.class);
			response.close();
			break;
		}
		case "delete": {
			response = resource

					.delete(ClientResponse.class);
			status = response.getStatus();
			String reminderString="";
			switch(status){
			case 204:reminderString="删除成功！";break;
			case 404:reminderString="删除失败，要删除的值不存在！";break;
			case 409:reminderString="删除失败，有关系存在，请先删除关系";break;
			}
//			System.out.println(String.format("DELETE : [%s]  status code: [%d]",uri, status));
//			System.out.println(reminderString);
			response.close();
			break;
		}
		}
		
		//System.out.println(String.format("Send to: [%s]  status code: [%d]",uri, status));
		return dataString;

	}
}
