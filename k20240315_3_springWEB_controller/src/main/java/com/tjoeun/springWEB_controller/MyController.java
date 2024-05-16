package com.tjoeun.springWEB_controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 클라이언트로부터 서비스 요청이 들어왔을때 컨트롤러로 진입하고 컨트롤러는 요청에 따른 작업을 실행한 후
// view 페이지로 데이터를 전달한다.

//	컨트롤러 제작 순서
//	1.. base-package로 지정된 패키지에 적당한 이름으로 클래스를 만들고 @Controller 어노테이션을 붙여서
//		컨트롤러로 사용될 클래스임을 Spring에게 알려준다.
//	2.. 로그 관련 설정이 필요하면 한다.
//	3.. @RequestMapping 어노테이션에 인수로 클라이언트에서 넘어오는 요청을 지정한다.
//	4.. 요청을 처리할 적당한 이름의 리턴 타입이 String인  메소드를 만든다.


@Controller
public class MyController {

	private static final Logger logger = LoggerFactory.getLogger(MyController.class);

	@RequestMapping("/view") // 클라이언트에서 넘어오는 서비스 요청을 받는다.
	public String view() {
		System.out.println("MyController의 view() 메소드 실행");
		logger.info("MyController의 view() 메소드 실행");
//		필요한 작업
		
//		"/WEB-INF/views/" + "view" + ".jsp" -> "/WEB-INF/views/view.jsp"
		return "view"; // view 페이지 이름을 리턴시킨다.
	}
	
//	@RequestMapping 어노테이션이 설정된 메소드는 Model 인터페이스 객체를 인수로 가질 수 있다.
//	Model 인터페이스 객체는 컨트롤러에서 view 페이지로 넘겨줄 데이터를 저장한다.
	@RequestMapping ("/model")
	public String model (Model model) {
		System.out.println("MyController의 model() 메소드 실행");
		logger.info("MyController의 model() 메소드 실행");
		
//		addAttribute(key, value) : Model 인터페이스 객체에 key에 따른 value를 넣어준다.
		model.addAttribute("id", "QWER");
		model.addAttribute("password", "7777");
		model.addAttribute("name", "나마에");
		
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("바둑");
		hobbies.add("오목");
		hobbies.add("장기");
		model.addAttribute("hobbies", hobbies);
		
//		"/WEB-INF/views/" + "/model/model" + ".jsp" -> "/WEB-INF/views/model/model.jsp"
		return "/model/model";
	}
	
	@RequestMapping("/modelAndView")
//	ModelAndView 클래스는 객체는 view 페이지의 이름과 view 페이지로 넘겨줄 데이터를 저장한다.
//	view페이지 이름과 view 페이지로 넘겨줄 데이터를 ModelAndView 클래스 객체에 저장시켜서 리턴해야하므로
//	메소드의 리턴 타입에 ModelAndView를 지정한다.
	public ModelAndView modelAndView () {
		logger.info("MyController의 modelAndView() 메소드 실행");
		
//		view 펭지ㅣ로 넘겨줄 데이터와 view 페이지 이름을 저장할 ModelAndView 클래스 객체를 선언한다.
		ModelAndView modelAndView = new ModelAndView();
//		view 페이지로 넘겨줄 데이터를 저장한다.		
//		addObject(key, value) 메소드로 ModelAndView 클래스 객체에 key에 따른 value를 넣어준다.
		modelAndView.addObject("id", "ABCD");
		modelAndView.addObject("password", "0000");
		modelAndView.addObject("name", "우아앙");
		
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("컬링");
		hobbies.add("달리기");
		hobbies.add("수영");
		modelAndView.addObject("hobbies", hobbies);
		
//		view 이름을 저장한다.
//		setViewName() 메소드로 ModelAndView 클래스 객체에 view 페이지 이름을 넣어준다.
		modelAndView.setViewName("/modelAndView/modelAndView");
		
//		ModelAndView 클래스 객체를 리턴시킨다.
		return modelAndView;
	}
	
	
	
	
	

}





















