package com.tjoeun.springLifeCycle;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
	
	public static void main(String[] args) {
		
		
		/*
		System.out.println("-----------------------------------------");
//		afterPropertiesSet() 메소드는 실행되지만 destroy() 메소드가 실행되지 않는다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		System.out.println("-----------------------------------------");
		
		Person person = ctx.getBean("person", Person.class);
		System.out.println(person);
		System.out.println(person.getName() + "님은 " + person.getAge() + "살입니다.");
		System.out.println("-----------------------------------------");
		
		Person person2 = ctx.getBean("person2", Person.class);
		System.out.println(person2);
		System.out.println(person2.getName() + "님은 " + person2.getAge() + "살입니다.");
		System.out.println("-----------------------------------------");
		*/
		
//		비어있는 DI 컨테이너를 만든다. -> 필요할 때 bean 설정 정보를 채워서 실행한다.
//		GenericXmlApplicationContext 클래스 생성자로 bean 설정 정보를 넘기지 않고 만든 DI 컨테이너는
//		bean 설정 정보가 들어있지 않은 비어있는 컨테이너로 생성된다.
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		
//		load() 메소드로 xml 파일에서 정의한 bean 설정 정보를 DI 컨테이너에 넣어준다.
//		load() DI 컨테이너에 bean 설정 정보를 넣어주기만 하기때문에 아직 bean이 생성되지 않은 상태이다.
//		-> 생성자가 실행되지 않은 상태
		
		ctx.load("classpath:/appCTX.xml");
		
//		load() 메소드를 사용해서 bean 설정 정보를 컨테이너에 넣어줄 경우 bean을 생성하기 위해서 
//		refresh() 메소드를 실행해야 한다.
//		refresh() 메소드가 실행되는 순간 load() 메소드로 DI 컨테이너에 넣어준 bean 설정 정보에 따른
//		bean이 생성되고 refresh() 메소드를 실행하지 않으면 spring 3.2.18 버전까지는 getBean() 메소드로
//		컨테이너에서 bean을 얻어내는 순간 bean이 생성되고, 4.0.0버전부터는 에러가 발생된다.
		
		ctx.refresh(); // bean 설정 정보에 따른 bean을 만든다.
		
		System.out.println("-----------------------------------------");
		Person person = ctx.getBean("person", Person.class);
		System.out.println(person);
		System.out.println(person.getName() + "님은 " + person.getAge() + "살입니다.");
		
		System.out.println("-----------------------------------------");
		
		Person person2 = ctx.getBean("person2", Person.class);
		System.out.println(person2);
		System.out.println(person2.getName() + "님은 " + person2.getAge() + "살입니다.");
		System.out.println("-----------------------------------------");
		
		
//		refresh() 메소드를 실행했더라도 close() 메소드를 실행하지 않으면 bean이 소멸될 때 destroy() 메소드는 실행되지 않는다.
//		spring 3.2.18 버전까지는 close() 메소드를 실행해도 refresh() 메소드가 실행되지 않았다면 destroy() 메소드가 실행되지 않는다.
		
		ctx.close();
		
		
		
	}
	
	
}











