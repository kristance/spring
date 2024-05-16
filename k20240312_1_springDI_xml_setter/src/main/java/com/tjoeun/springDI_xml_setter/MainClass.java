package com.tjoeun.springDI_xml_setter;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
//		System.out.println("hi, Spring");
		
		/*
//		자바에서 사용하던 방식
		MyCalculator myCalculator = new MyCalculator();
		myCalculator.setFirstNum(5);
		myCalculator.setSecondNum(3);
		
		System.out.println(myCalculator);
		
//		Calculator calculator = new Calculator();
//		myCalculator.setCalculator(calculator);
		myCalculator.setCalculator(new Calculator());
		*/
		
//		클래스의 객체를 설정하는 xml 파일 만들기
//		src/main/resources에서 우클릭 -> New -> spring Bean Configuration File -> xml 파일 이름 입력 -> Finish
		
//		객체(bean)를 선언한 xml 파일의 경로를 지정한다.
//		src/main/resources는 classpath로 설정되어 있다. -> "classpath:/"로 접근한다.
		String configLocation = "classpath:/applicationCTX.xml";
		
//		xml 파일에서 설정한 객체(bean) 정보를 읽어들인다.
//		GenericXmlApplicationContext 클래스 생성자의 인수로 객체를 설정하는 xml파일의 경로와 이름을 넘겨주면
//		xml 파일의 내용을 읽어서 java 객체로 변환한 후 부모 클래스인 AbstractApplicationContext 클래스 객체에
//		저장한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
//		getBean() 메소드로 AbstractApplicationContext 클래스 타입의 객체 ctx에 저장된 객체를 얻어온다.
//		getBean("얻어올 bean의 id", bean을 생성한 클래스 이름.class)
		MyCalculator myCalculator = ctx.getBean("myCalculator", MyCalculator.class);
		System.out.println(myCalculator);
		
		myCalculator.add();
		myCalculator.subtract();
		myCalculator.multiple();
		myCalculator.divided();
		
	}
	
}
