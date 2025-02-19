package com.tjoeun.springDI_xml_setter;

import java.util.ArrayList;

public class Myinfo {

	
	private String name;
	private double height;
	private double weight;
	private ArrayList<String> hobbies;
	private BMICalculator bmiCalculator;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public ArrayList<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(ArrayList<String> hobbies) {
		this.hobbies = hobbies;
	}
	public BMICalculator getBmiCalculator() {
		return bmiCalculator;
	}
	public void setBmiCalculator(BMICalculator bmiCalculator) {
		this.bmiCalculator = bmiCalculator;
	}
	@Override
	public String toString() {
		return "Myinfo [name=" + name + ", height=" + height + ", weight=" + weight + ", hobbies=" + hobbies
				+ ", bmiCalculator=" + bmiCalculator + "]";
	}
	
	
//	개인 정보를 출력하는 메소드
	public void getMyInfo() {
		System.out.println("이름 :" + name);
		System.out.println("신장 :" + height);
		System.out.println("체중 :" + weight);
		System.out.println("취미 :" + hobbies);
		BMICalculator.bmiCalculator(height, weight);
	}
	
	
	
	
}
