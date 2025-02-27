package com.tjoeun.springProfile_java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Profile 어노테이션으로 profile을 지정한다.
@Profile("run")

@Configuration
public class AppConfig_run {
	
	@Bean
	public ServerInfo serverInfo () {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setIpNumber("192.168.0.100");
		serverInfo.setPortNumber("8080");
		
		return serverInfo;
	}

}
