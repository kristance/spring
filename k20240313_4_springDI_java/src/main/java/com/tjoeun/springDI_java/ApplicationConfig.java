package com.tjoeun.springDI_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// appCTX.xml 파일에서 구현한 Student 클래스의 bean 설정을 java 클래스로 구현한다.
// java 클래스를 이용해서 bean을 설정하려면 적당한 이름으로 클래스를 만들고
// @Configuration 어노테이션을 붙여서 이 클래스가 bean을 설정하는 xml파일의 
// <beans> 태그 역할을 한다고 알려준다.
@Configuration // 이 클래스는 DI 설정에 사용되는 클래스임을 spring에게 알려준다.
public class ApplicationConfig {
	
//	적당한 이름으로 메소드를 만들고 @Bean 어노테이션을 붙여서 bean을 설정하는 xml파일의
//	<bean> 태그 역할을 한다는 것을 spring에게 알려준다.
//	<bean id="student" class="com.tjoeun.springDI_java.Student"> -> xml 파일의 bean 설정
//	@Bean
//	public 리턴타입[class 속성값 -> 클래스 이름만] 메소드이름[id 속성값]() {
//		생성자나 setter 메소드를 사용해서 bean을 초기화하는 작업을 실행한다.
//		...
//		return bean 객체;
//	}
	
	@Bean
	public Student student2() {
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("골프");
		hobbies.add("스노클링");
		hobbies.add("스킨스쿠버");
		
//		Student student = new Student();
//		student.setName("고길동");
//		student.setAge(53);
//		student.setHobbies(hobbies);
		Student student = new Student("고길동", 53, hobbies);
		student.setHeight(171.5);
		student.setWeight(57.8);
		
		return student;
		
		
	}
	
}
