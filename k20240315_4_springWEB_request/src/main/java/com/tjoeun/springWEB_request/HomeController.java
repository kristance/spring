package com.tjoeun.springWEB_request;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.log.UserDataHelper.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		
		return "home";
	}
	
	@RequestMapping("/memberView")
//	HttpServletRequest 인터페이스 객체로 view 페이지에서 컨트롤러로 넘어오는 데이터를 받는다.
//	Model 인터페이스 객체에 컨트롤러에서 view 페이지로 넘겨줄 데이터를 저장한다.
	public String memberView(HttpServletRequest request, Model model) {
		logger.info("HomeController의 memberView()");
		
//		view 페이지에서 컨트롤러로 넘어와 HttpServletRequest 인터페이스 객체에 저장되는 데이터를 받는다.
		String id = request.getParameter("id");
		String pw =  request.getParameter("pw");
		System.out.println("id -> " + id + ", pw ->" + pw);
		logger.info("id -> #{}, pw -> #{}", id, pw);
		
//		컨트롤러에서 view 페이지로 넘겨줄 데이터가 있으면 Model 인터페이스 객체에 저장한다.
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "memberView";
	}
	
	@RequestMapping ("/memberLogin")
//	@RequestParam("view 페이지에서 컨트롤러로 넘어오는 변수명") 자료형 변수명
//	@RequestParam("id") String id는 String id = request.getParameter("id");와 같은 기능이 실행된다.
//	HttpServletRequest 인터페이스 객체로  view 페이지에서 컨트롤러로 넘어오는 데이터를 받을 때 데이터가 넘어오지 않아도
//	에러가 발생되지 않고 null로 처리하지만, @RequestParam 어노테이션을 사용해서 view 페이지에서 넘어오는 데이터를 받을 때
//	데이터가 넘어오지 않으면 400 에러가 발생된다.
	public String memberLogin (/*HttpServletRequest request,*/ Model model,
												@RequestParam("id") String id, 
												@RequestParam("pw") String pw) {
		logger.info("HomeController의 memberLogin()");
		logger.info("id -> ##{}, pw -> ##{}", id, pw);
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
	return "memberLogin";	
	}
	
	@RequestMapping("/member")
	public String member (HttpServletRequest request, Model model) {
		logger.info("HomeController의 member()");
		
		return "member";
	}
/*	
	@RequestMapping("/memberInsert")
	public String memberInsert (HttpServletRequest request, Model model) {
		logger.info("HomeController의 memberInsert()");

//		view 페이지에서 POST 방식으로 컨트롤러로 넘어와 HttpServletRequest 인터페이스 객체에
//		저장된 데이터를 받는다.
		
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		String email = request.getParameter("email");
//		logger.info(String.format("name -> ###%s, id -> ###%s, pw -> ###%s, email -> ###%s", name, id, pw, email));
		
//		기본 생성자로 VO 클래스 객체를 만들고 setter 메소드로 데이터를 넣어준다.
//		MemberVO vo = new MemberVO();
//		vo.setName(name);
//		vo.setId(id);
//		vo.setPw(pw);
//		vo.setEmail(email);
		
//		public MemberVO(String name, String id, String pw, String email) 생성자를 사용해서 데이터를 넣어준다.
//		MemberVO vo = new MemberVO(name, id, pw, email);
		
//		xml 파일을 이용해서 MemberVO 클래스의 bean을 생성하고 setter 메소드를 이용해 데이터를 넣어준다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/appCTX.xml");
		MemberVO vo = ctx.getBean("vo", MemberVO.class);
		vo.setName(name);
		vo.setId(id);
		vo.setPw(pw);
		vo.setEmail(email);
		
//		logger.info(vo.toString());
//		logger.info("{}",vo);
		
//		memberInsert.jsp로 MemberVO 클래스 객체를 넘겨주기 위해 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", vo);
//		request는 받을때, model은 보낼때
		
		
		return "memberInsert";
	}
*/
	
	/*
	@RequestMapping("/memberInsert")
//	커맨드 객체(넘겨받을 데이터를 저장할 객체) 사용하기 -> <jsp:useBean>
//	view 페이지에서 컨트롤러로 넘어노는 데이터를 클래스 객체에 저장하려는 경우 커맨드 객체를 사용하면 편리하다.
//	커맨드 객체를 사용하면 HttpServletRequest 인터페이스 객체나 @RequestParam 어노테이션으로 넘겨받은 데이터를
//	클래스 객체에 setter 메소드를 사용해서 저장한 다음 Model 인터페이스 객체에 넣어주는 동작이 자동으로 처리된다.
//	커맨드 객체는 view 페이지에서 컨트롤러로 넘어오는 데이터를 저장할 클래스 객체를 의미하며 
//	view 페이지로 넘어가는 객체의 이름은 별도의 설정이 없으면 커맨드 객체 이름이 첫 문자만 소문자로 변경되어 넘어간다.
	
	public String memberInsert (HttpServletRequest request, Model model, MemberVO vo ;;<-커맨드 객체;;) {
//		커맨드 객체를 사용할 때 커맨드 객체의 필드값과 값을 읽어올 대상 (ex) 텍스트박스의 name 속성이 동일해야 커맨드 객체를 사용해서 값을 사용할 수 있다.
		logger.info("HomeController의 memberInsert()");
//		logger.info("{}",vo);
		model.addAttribute("vo", vo); // 별도의 설정
		return "memberInsert";
	}
	
	*/
	
	@RequestMapping("/memberInsert")
//	커맨드 객체의 이름이 너무 길어서 사용하기 불편하면 view 페이지로 넘겨주는 커맨드 객체의 이름을 @ModelAttribute
//	어노테이션을 이용해서 별도로 커맨드 객체의 이름을 지정해서 사용할 수 있다.
	public String memberInsert (HttpServletRequest request, Model model, 
			@ModelAttribute("vo") MemberVO memberVO) {
		logger.info("HomeController의 memberInsert()");
		
		return "memberInsert";
	}
	
}

