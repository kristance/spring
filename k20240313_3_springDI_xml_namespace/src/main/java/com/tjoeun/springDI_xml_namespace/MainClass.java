package com.tjoeun.springDI_xml_namespace;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		String configLocation = "classpath:/appCTX.xml";
		String configLocation2 = "classpath:/appCTX2.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation, configLocation2);
		
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		System.out.println("이름 -> " + student.getName());
		System.out.println("취미 -> " + student.getHobbies());
		System.out.println("--------------------------");

		Student student2 = ctx.getBean("student", Student.class);
		System.out.println(student2);
		System.out.println("이름 -> " + student2.getName());
		System.out.println("취미 -> " + student2.getHobbies());
		System.out.println("--------------------------");
		
		System.out.println(student.equals(student2) ? "같다" : "다르다");
		System.out.println("--------------------------");
		
		Student student3 = ctx.getBean("student3", Student.class);
		System.out.println(student3);
		System.out.println("이름 -> " + student3.getName());
		System.out.println("취미 -> " + student3.getHobbies());
		System.out.println("--------------------------");
		
		System.out.println(student.equals(student3) ? "같다" : "다르다");
		System.out.println("--------------------------");
		
		Student student4 = ctx.getBean("student4", Student.class);
		System.out.println(student4);
		System.out.println("이름 -> " + student4.getName());
		System.out.println("취미 -> " + student4.getHobbies());
		System.out.println("--------------------------");
		
		System.out.println(student.equals(student4) ? "같다" : "다르다");
		System.out.println("--------------------------");
		
		StudentInfo studentInfo = ctx.getBean("studentInfo", StudentInfo.class);
		System.out.println(studentInfo);
		studentInfo.getStudentInfo();
		System.out.println("--------------------------");
		
		Student student5 = ctx.getBean("student5", Student.class);
		System.out.println(student5);
		System.out.println("이름 -> " + student5.getName());
		System.out.println("취미 -> " + student5.getHobbies());
		System.out.println("--------------------------");
		
		Family family = ctx.getBean("family", Family.class);
		System.out.println(family);
		System.out.println("father -> " + family.getPapaName());
		System.out.println("mother -> " + family.getMamiName());
		System.out.println("brother -> " + family.getBrotherName());
		System.out.println("sister -> " + family.getSisterName());
		System.out.println("--------------------------");
		
		Family family2 = ctx.getBean("family2", Family.class);
		System.out.println(family2);
		System.out.println("father -> " + family2.getPapaName());
		System.out.println("mother -> " + family2.getMamiName());
		System.out.println("brother -> " + family2.getBrotherName());
		System.out.println("sister -> " + family2.getSisterName());
		System.out.println("--------------------------");
		
		
		
		
		
		
		
		
		
	}
}
