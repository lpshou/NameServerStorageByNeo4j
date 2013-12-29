package com.b409.nameServer.experiment;

import scala.annotation.StaticAnnotation;

import com.b409.nameServer.namespace.api.Directory;
import com.b409.nameServer.namespace.api.File;
import com.b409.nameServer.namespace.api.Group;
import com.b409.nameServer.namespace.api.User;
import com.b409.nameServer.relationship.api.Friend;
import com.b409.nameServer.relationship.api.Like;

public class initialization {
	static int groupNodeSum = 0;
	static int userNodeSum = 0;
	static int fileNodeSum = 0;
	static void initializationTemplate(int groupNum,int userNumEveryGroup,int fileNumEveryUser){
		String groupNameString="group";
		String userNameString="user";
		String fileNameString="file";

		/************************创建群组、用户、文件**************************************/
		User.createUser(userNameString);
		//创建群组
		for(int i=0;i<groupNum;i++){
			String groupName = groupNameString+groupNodeSum;
			groupNodeSum++;
			Group.createGroup(groupName, userNameString);
			//创建用户
			for(int j=0;j<userNumEveryGroup;j++){
				String userName = userNameString + userNodeSum;
				userNodeSum++;
				User.createUser(userName);
				User.addUserToGroup(groupName, userName);
				//创建文件，文件夹创建省略
				for(int k=0;k<fileNumEveryUser;k++){
					String fileName = fileNameString+fileNodeSum;
					fileNodeSum++;
					File.createFile(userName, "User", fileName, "{{副本:机器id为1+文件id为"+i+j+k+"}}");
				}
			}
		}
		
		/************************创建好友关系*********************************************/
		//创建规则：1->234,2->345...
		for(int m=0;m<groupNum*userNumEveryGroup-2;m++){
			int m1=m+1;
			int m2=m+2;
			Friend.makeFriends(userNameString+m, userNameString+m1);
			Friend.makeFriends(userNameString+m, userNameString+m2);
		}
	
		/************************创建用户和文件喜好关系************************************/
		//创建规则：1->2的第一个文件，2->3的第一个文件
		for(int m=0;m<groupNum*userNumEveryGroup-1;m++){
			int m1=(m+1)*fileNumEveryUser;
			Like.createLike(userNameString+m, fileNameString+m1);
		}
	}

	public static void main(String[] args) {
		long start=System.currentTimeMillis();

		initializationTemplate(1, 50, 10);
		
		long time = System.currentTimeMillis() - start;
		System.out.println("运行耗时= "+time+" 毫秒");
	
	}

}
