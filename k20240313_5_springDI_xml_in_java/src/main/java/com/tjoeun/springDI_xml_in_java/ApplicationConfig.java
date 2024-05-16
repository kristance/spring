package com.tjoeun.springDI_xml_in_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
	@Bean
	public Student student2() {
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("골프");
		hobbies.add("스노클링");
		hobbies.add("스킨스쿠버");
		
		Student student = new Student("고길동", 53, hobbies);
		student.setHeight(171.5);
		student.setWeight(57.8);
		
		return student;
		
		
	}
	
}
