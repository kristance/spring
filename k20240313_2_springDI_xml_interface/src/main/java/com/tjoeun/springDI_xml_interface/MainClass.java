package com.tjoeun.springDI_xml_interface;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
	
	public static void main(String[] args) {
	
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		
		Pencil pencil = ctx.getBean("pencil4B", Pencil4B.class); // Upcasting
		pencil.use();
		
		pencil = ctx.getBean("pencilHB", PencilHB.class); // Upcasting
		pencil.use();
		
		pencil = ctx.getBean("pencilHBEraser", PencilHBEraser.class); // Upcasting
		pencil.use();
		
		
		
	}
	
	
}
