package com.tjoeun.springDI_xml_setter;


public class BMICalculator {

	public BMICalculator() {
		System.out.println("BMICalculator 클래스의 기본 생성자로 bean을 만든다.");
	}
	
	
	
// 키(height)와 몸무게(weight)를 인수로 넘겨받아 BMI 지수를 계산하고 판정을 출력하는 메소드
// BMI 지수는 몸무게(kg) / (키(m) * 키(m))로 계산한다
//	산출된 값이 18.5 미만이면 저체중, 18.5이상 23 미만이면 정상, 23이상 25미만이면 과체중,
//	25 이상 30 미만이면 비만, 30이상이면 고도비만
	
	public static void bmiCalculator(double height, double weight) {
		
		double bmi = weight / Math.pow(height / 100, 2);
		System.out.print("키 -> " + height + ", 몸무게 -> " +
								weight + "kg" + ", BMI ->" + bmi + " ###");
		
		if (bmi < 18.5) {
			System.out.println(" 저체중 입니다.");
		} else if (bmi >= 18.5 && bmi < 23) {
			System.out.println(" 정상 입니다.");
		} else if (bmi >= 23 && bmi < 25) {
			System.out.println(" 과체중 입니다.");
		} else if (bmi >= 25 && bmi < 30) {
			System.out.println(" 비만 입니다.");
		} else if (bmi >= 30) {
			System.out.println(" 고도비만 입니다.");
		}
		
	}
	
	
	
	
	
	
}
