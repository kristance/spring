package com.tjoeun.springDI_xml_in_java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		
//		xml
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		Student student2 = ctx.getBean("student2", Student.class);
		System.out.println(student2);
		System.out.println("--------------------------");

		
//		java 
//		AnnotationConfigApplicationContext ctx2 = 
//				new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		Student student2 = ctx2.getBean("student2", Student.class);
//		System.out.println(student2);
		System.out.println("--------------------------");
		
		
		
		
		
	}
}
