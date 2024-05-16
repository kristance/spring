package com.tjoeun.springDI_xml_constructor;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	
	public static void main(String[] args) {
		
//		Student student = new Student();
//		student.setName("뽀로로");
//		student.setAge(13);
//		student.setGradeNum(6);
//		student.setClassNum(13);
		
//		resource 안에 ctx.xml파일이 있으면 classpath:에 /를 생략할 수 있다.; 하위 폴더 또는 다른 폴더는 X
//		아래 줄로 인해 이미 bean은 생성된다. -> getBean으로 얻어오는 것
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCTX.xml");
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		
//		Student student2 = new Student("루피", 12, 5, 15);
		
		Student student2 = ctx.getBean("student2", Student.class);
		System.out.println(student2);
		
		StudentInfo studentInfo1 = ctx.getBean("studentInfo1", StudentInfo.class);
		System.out.println(studentInfo1);
		studentInfo1.getStudentInfo();
		
		StudentInfo studentInfo2 = ctx.getBean("studentInfo2", StudentInfo.class);
		studentInfo2.getStudentInfo();
	}
	
	
}
