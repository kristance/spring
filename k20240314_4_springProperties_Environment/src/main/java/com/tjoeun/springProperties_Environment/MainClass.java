package com.tjoeun.springProperties_Environment;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class MainClass {

	public static void main(String[] args) {
//		빈 DI 컨테이러를 만든다.
		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
//		DI 컨테이너의 환경설정 정보를 얻어온다.		
		ConfigurableEnvironment env = ctx.getEnvironment();
//		환경설정 정보에서 properties 정보만 얻어온다.		
		MutablePropertySources mutablePropertySources = env.getPropertySources();
		
		try {
//			환경설정 정보의 properties에 admin.properties 정보를 추가한다.
			mutablePropertySources.addLast(new ResourcePropertySource("classpath:/admin.properties"));
			System.out.println("admin.id -> " + env.getProperty("admin.id"));
			System.out.println("admin.pw -> " + env.getProperty("admin.pw"));
			System.out.println("properties 파일을 읽어서 DI 컨테이너에 저장한 후 얻어온다.");
		} catch (IOException e) {
			System.out.println("### admin.properties 파일을 읽어올 수 없음 ###");
		}
		System.out.println("--------------------------------------------------");
		
//		AdminConnection 클래스의 bean을 얻어온다.
//		아래와 같이 DI 컨테이너를 다시 만들면 admin.properties 파일의 properties 정보가 존재하지 않는다.
//		AbstractApplicationContext gCtx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		
//		DI 컨테이너를 다시 만들어서 환경설정 정보를 읽어오면 addLast() 메소드를 사용해 추가한 properties 정보가
//		없기때문에 ConfigurableApplicationContext 인터페이스 타입 객체의 환경설정 정보를 그대로 사용하기 위해서
//		xml 파일에서 bean 설정 정보를 얻어오는 GenericXmlApplicationContext 클래스 타입으로 형변환시켜 사용한다.
//		-> properties 정보가 추가된 기존 DI 컨테이너를 그대로 사용한다.
		GenericXmlApplicationContext gCtx = (GenericXmlApplicationContext) ctx;
		gCtx.load("classpath:/appCTX.xml");
		gCtx.refresh();
		
		
		AdminConnection adminConnection = gCtx.getBean("adminConnection", AdminConnection.class);

		System.out.println(adminConnection); // QWER; 7777
		
		gCtx.close();
		
	}

}
