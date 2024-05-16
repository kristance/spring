package com.tjoeun.springDI_xml_setter;


// 사칙연산에 사용할 필드를 선언하고 Calculator 클래스의 사칙연산을 실행하는 메소드를 호출하는 클래스
public class MyCalculator {

	private int firstNum;
	private int secondNum;
	private Calculator calculator;
	
	
	
	public int getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(int firstNum) {
		this.firstNum = firstNum;
	}

	public int getSecondNum() {
		return secondNum;
	}

	public void setSecondNum(int secondNum) {
		this.secondNum = secondNum;
	}

	public Calculator getCalculator() {
		return calculator;
	}

	public void setCalculator(Calculator calculator) {
		this.calculator = calculator;
	}
	
	@Override
	public String toString() {
		return "MyCalculator [firstNum=" + firstNum + ", secondNum=" + secondNum + ", calculator=" + calculator + "]";
	}
	
	

//	Calculator 클래스의 add(), subtract(), multiple(), divided() 메소드를 실행하는 메소드를 만든다.
	public void add () {
		calculator.add(firstNum, secondNum);
	}
//	
	public void subtract () {
		calculator.add(firstNum, secondNum);
	}
//	
	public void multiple () {
		calculator.add(firstNum, secondNum);
	}
//	
	public void divided () {
		calculator.add(firstNum, secondNum);
	}
//	
	
}
