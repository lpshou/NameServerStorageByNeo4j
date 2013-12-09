package com.b409.nameServer.relationship.api;

import java.util.ArrayList;
import java.util.List;

public class Like {
	
	/**
	 * 
	* @Description: 建立用户对某个文件的喜爱
	* @param userName：用户名
	* @param fileName：文件名，
	* @return：
	 */
	public static void createLike(String userName, String fileName){
		
	}
	
	/**
	 * 
	* @Description: 删除用户对某个文件的喜好
	* @param userName：用户名
	* @param fileName：文件名
	* @return：
	 */
	public static void deleteLike(String userName, String fileName){
		
	}
	
	/**
	 * 
	* @Description: 列出所有喜爱的文件的id
	* @param userName：用户名
	* @return：
	 */
	public static List<Integer> listAllLikedFile(String userName){
		List<Integer> fileIds = new ArrayList<>();
		return fileIds;
	}

}
