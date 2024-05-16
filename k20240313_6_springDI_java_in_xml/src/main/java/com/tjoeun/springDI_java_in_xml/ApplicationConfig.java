package com.tjoeun.springDI_java_in_xml;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


// java 파일에서 xml 파일의 bean 설정 정보를 읽어오려면 @ImportResource 어노테이션으로 읽어들일
// xml 파일을 java 파일에 포함시키면 된다.
@ImportResource("classpath:/appCTX.xml")

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
