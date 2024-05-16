package com.tjoeun.springProperties_Environment;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

// AdminConnection 클래스의 bean이 생성되는 순간 admin.properties 파일의 내용을 읽어서 필드에 저장한다.
public class AdminConnection implements EnvironmentAware, InitializingBean, DisposableBean{
	
	private String adminID; 
	private String adminPw; 
	private Environment env; // DI 컨테이너에 환경설정 정보를 저장한다.
	
	public AdminConnection() {
		System.out.println("## AdminConnection 클래스의 bean이 생성 ##");
	}

//

	public String getAdminID() {
		return adminID;
	}



	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}



	public String getAdminPw() {
		return adminPw;
	}



	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}



	public Environment getEnv() {
		return env;
	}



	public void setEnv(Environment env) {
		this.env = env;
	}

//

	@Override
	public String toString() {
		return "AdminConnection [adminID=" + adminID + ", adminPw=" + adminPw + ", env=" + env + "]";
	}

	
//	EnvironmentAware 인터페이스를 구현하면 사용할 수 있고, EnvironmentAware 인터페이스가 구현된 클래스의 bean이 생성된 후
//	자동으로 실행되는 메소드
	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("AdminConnection 클래스의 bean이 생성된 후 자동으로 setEnvironment() 메소드 실행");
//		setEnvironment() 메소드의 인수인 DI 컨테이너 환경설정 정보를 기억하는 Environment 인터페이스 타입의 객체
//		environment에 spring 알아서 EnvironmentAware 인터페이스가 구현된 클래스의 bean이 생성되는 순간 DI 컨테이너의
//		환경설정 정보를 넘겨준다. -> admin.properties 파일의 정보가 넘어온다.
		System.out.println(environment);
		System.out.println("admin.id -> " + environment.getProperty("admin.id"));
		System.out.println("admin.Pw -> " + environment.getProperty("admin.pw"));
		System.out.println("--------------------------------------------------");
		
//		Environment 인터페이스 객체로 넘어온 DI 컨테이너의 환경설정 정보를 AdminConnection 클래스에서 사용하기 위해
//		필드로 선언한 Environment 인터페이스 객체 env에 저장한다.
		env = environment;
	}

	
	
//	InitializingBean 인터페이스를 구현하면 사용할 수 있고 bean이 생성될 때 자동으로 실행되는 메소드
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("AdminConnection 클래스의 bean이 생성된 후 자동으로 afterPropertiesSet(##) 메소드 실행");
//		Environment 인터페이스 객체 env에 저장된 환경설정 정보에 properties를 필드에 넣어준다.
		adminID = env.getProperty("admin.id");
		adminPw = env.getProperty("admin.pw");
	}

	
//	DisposableBean 인터페이스를 구현하면 사용할 수 있고 bean이 소멸될 때 자동으로 실행되는 메소드
	@Override
	public void destroy() throws Exception {
		System.out.println("AdminConnection 클래스의 bean이 소멸된 후 자동으로 destroy(##) 메소드 실행");
		
	}



	
	
	
	
	
}
