package com.tjoeun.springDI_xml_setter;

import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;


public class MainClass {

	public static void main(String[] args) {
	
//		Myinfo myinfo = new Myinfo();
//		myinfo.setName("뽀로로");
//		myinfo.setHeight(157.8);
//		myinfo.setWeight(59.0);
//		
//		ArrayList<String> hobbies = new ArrayList<String>();
//		hobbies.add("놀기");
//		hobbies.add("달리기");
//		hobbies.add("산책");
//		myinfo.setHobbies(hobbies);
//		myinfo.setBmiCalculator(new BMICalculator());
		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		Myinfo myinfo = ctx.getBean("myinfo", Myinfo.class);
		
		
		myinfo.getMyInfo();
		
		
	}
	
	
	
}
