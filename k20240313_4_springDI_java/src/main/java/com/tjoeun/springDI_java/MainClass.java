package com.tjoeun.springDI_java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		
//		xml파일에서 설정한 bean 설정 정보를 읽어오려면 아래의 방법을 사용한다.
		String configLocation = "classpath:/appCTX.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
//		getBean("bean의 id", bean을 생성한 클래스 이름.class)
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		System.out.println("--------------------------");

		
//		java 파일에 설정한 bean 설정 정보를 읽어오려면 아래의 방법을 사용한다.
//		new AnnotationConfigApplicationContext(bean을 설정한 클래스 이름.class);
		AnnotationConfigApplicationContext ctx2 = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		getBean("메소드이름", 메소드의 리턴타입.class)
		Student student2 = ctx2.getBean("student2", Student.class);
		System.out.println(student2);
		System.out.println("--------------------------");
		
		
		
		
		
	}
}
