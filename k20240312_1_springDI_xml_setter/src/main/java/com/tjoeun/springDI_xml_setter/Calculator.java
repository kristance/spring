package com.tjoeun.springDI_xml_setter;


// MyCalculator 클래스에서 호출되는 firstNum, secondNum을 인수로 넘겨받아 사칙연산을 실행하는 메소드ㄹ를
// 모아놓은 클래스
public class Calculator {
//
	public void add (int firstNum, int secondNum) {
		
		System.out.println("add()");
		int result = firstNum + secondNum;
		System.out.println(firstNum+ " + " + secondNum + " = " + result);
		
	}
//	
	public void subtract (int firstNum, int secondNum) {
		
		System.out.println("subtract()");
		int result = firstNum - secondNum;
		System.out.println(firstNum+ " - " + secondNum + " = " + result);
		
	}
//	
	public void multiple (int firstNum, int secondNum) {
		
		System.out.println("multiple()");
		int result = firstNum * secondNum;
		System.out.println(firstNum+ " * " + secondNum + " = " + result);
		
	}
//	
	public void divided (int firstNum, int secondNum) {
		
		System.out.println("divided()");
		int result = firstNum / secondNum;
		System.out.println(firstNum+ " / " + secondNum + " = " + result);
		
	}
//	
	
	
	
}
