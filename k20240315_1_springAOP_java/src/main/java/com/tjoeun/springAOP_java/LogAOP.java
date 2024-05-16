package com.tjoeun.springAOP_java;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

// java 파일을 이용해서 AOP 설정을 하려면 AOP를 설정할 클래스에 @Aspect 어노테이션을 붙여준다.
@Aspect
public class LogAOP {
	
//	pointcut을 지정하는 1번째 방법 -> 빈 메소드를 이용해서 pointcut 메소드를 만든다.
//	빈 메소드를 이용한 pointcut은 여러 메소드에 pointcut을 적용시킬 수 있다.
//	@Pointcut("within() 또는 execution()을 이용한 pointcut ")
//	public void 적당한이름() { }
	@Pointcut("within(com.tjoeun.springAOP_java.*)")
	public void pointcutMethod () {
	}
	
//	pointcut을 AOP 메소드에 적용하려면 AOP 어노테이션 
//	(@Before, @AfterReturning, @AfterThrowing, @After,	@Around)의 인수로 @Pointcut 어노테이션을 붙여서
//	pointcut을 설정한 빈 메소드 이름을 넣어주면 된다.
	
	@Before("pointcutMethod ()")
	public void before () {
		System.out.println("### LogAOP 클래스의 before() 메소드 실행 ###");
	}
	
	@AfterReturning("pointcutMethod()")
	public void afterReturning () {
		System.out.println("oooo LogAOP 클래스의 afterReturning() 메소드 실행 oooo");
		
	}
	
	@AfterThrowing("pointcutMethod()")
	public void afterThrowing () {
		System.out.println("ㅐㅐㅐㅐ LogAOP 클래스의 afterThrowing() 메소드 실행 ㅐㅐㅐㅐ");
		
	}
	
	@After("pointcutMethod()")
	public void after () {
		System.out.println("[][][] LogAOP 클래스의 after() 메소드 실행 [][][]");
		
	}
	
//	pointcut을 지정하는 2번째 방법 -> AOP 어노테이션의 인수로 바로 pointcut을 지정할 수 있다.
//	AOP 어노테이션의 인수로 pointcut을 지정하면 1개의 메소드에 pointcut을 적용시킬 수 있다.
	
	@Around("execution(* com.tjoeun.springAOP_java.Student.g*())")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("LogAOP 클래스의 around() 메소드 실행 <<< 핵심기능 실행 전 ????? ");
		long start = System.currentTimeMillis();
		try {
			System.out.println("LogAOP 클래스의 around() 메소드 실행 <<< 핵심기능 실행 중.... ");
			Object object = joinPoint.proceed(); 
			return object;
		} finally {
			System.out.println("LogAOP 클래스의 around() 메소드 실행 <<< 핵심기능 실행 후 !!!!! ");
			long end = System.currentTimeMillis();
			System.out.println("핵심 기능 실행 경과 시간 : " + ((end - start) / 1000) +  "초");
		}
	}
		
		

	
	
}


















