package com.tjoeun.springProperties_xml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	
	public static void main(String[] args) {
		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		AdminConnection adminConnection = ctx.getBean("adminConnection", AdminConnection.class);
		System.out.println(adminConnection);
		System.out.println(adminConnection.getAdminId());
		System.out.println(adminConnection.getAdminPw());
		System.out.println(adminConnection.getSub_adminId());
		System.out.println(adminConnection.getSub_adminPw());
		
	}
}
