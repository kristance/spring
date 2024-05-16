package com.tjoeun.springDI_java_in_xml;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		
//		xml
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");

		
//		java 
		AnnotationConfigApplicationContext ctx2 = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		Student student = ctx2.getBean("student", Student.class);
//		System.out.println(student);
		Student student2 = ctx2.getBean("student2", Student.class);
		System.out.println(student2);
		
		
		
		
		
	}
}
