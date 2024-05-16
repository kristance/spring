package com.tjoeun.springProfile_java;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;


public class MainClass {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("실행할 작업 환경을 입력하세요. (1 -> dev, 2 -> run) : ");
		int info = scanner.nextInt();
		String config = "";
		switch (info) {
		case 1:
			config += "dev";
			break;
		case 2:
			config += "run";
			break;
		}
		
//		profile이 설정된 java 파일의 bean을 읽어오기 위해서는 비어있는 DI 컨테이너를 만든 후
//		읽어올 bean의 profile을 지정한 다음에 profile이 지정된 bean을 load시켜야한다.
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		
//		읽어올 bean의 profile을 넣어준다.
		ctx.getEnvironment().setActiveProfiles(config);
		
//		GenericXmlApplicationContext 클래스로 xml 파일에서 설정한 bean 설정 정보를 DI 컨테이너에 넣어주기 위해서
//		load() 메소드를 사용했지만, GenericXmlApplicationContext 클래스로 java 파일에서 @Profile 어노테이션을 지정해서 설정한
//		bean 설정 정보를 넣어주려면 register() 메소드를 사용한다.
		ctx.register(AppConfig_run.class, AppConfig_dev.class);
		ctx.refresh();
		
		ServerInfo serverInfo = ctx.getBean("serverInfo", ServerInfo.class);
		System.out.println(serverInfo);
		
		ctx.close();
		
	}

}
