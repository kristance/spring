package com.tjoeun.springWEB_DBCP_board;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.springWEB_DBCP_board.service.ContentViewService;
import com.tjoeun.springWEB_DBCP_board.service.DeleteService;
import com.tjoeun.springWEB_DBCP_board.service.IncrementService;
import com.tjoeun.springWEB_DBCP_board.service.InsertService;
import com.tjoeun.springWEB_DBCP_board.service.MvcboardService;
import com.tjoeun.springWEB_DBCP_board.service.ReplyService;
import com.tjoeun.springWEB_DBCP_board.service.SelectService;
import com.tjoeun.springWEB_DBCP_board.service.UpdateService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	JdbcTemplate을 사용하려면 servlet-content.xml 파일에서 프로젝트가 시작될 때 DriverManagerDataSource
//	클래스의 bean(데이터베이스 연결 정보)을 참조해서 생성한 JdbcTemplate 클래스의 bean을 컨트롤러에서
//	JdbcTemplate 클래스 타입의 객체를 생성하고 넣어줘야 한다.
	private JdbcTemplate template; // 컨트롤러보다 DAO 클래스에서 사용해야 한다.

//	JdbcTemplate 클래스 타입 객체의 setter를 만든다.
//	프로젝트를 실행하면 spring 환경 설정 파일인 servlet-content.xml 파일이 읽혀진 다음 특정 메소드가
//	자동으로 실행되게 하기 위해서 @Autowired 어노테이션을 붙여준다.
//	@Autowired 어노테이션을 붙여서 선언한 메소드는 서버가 구동되는 단계에서 자동으로 실행되며 setter
//	메소드의 인수로 spring이 알아서 servlet-content.xml 파일에서 생성한 JdbcTemplate 클래스의 bean을
//	자동으로 전달한다.
//	자동으로 실행된 setter 메소드는 자동으로 전달받은 JdbcTemplate 클래스의 bean으로 JdbcTemplate
//	클래스 객체를 초기화 시킨다.
	@Autowired // @Autowired 어노테이션을 붙여서 선언한 메소드는 서버 구동 단계에서 자동으로 실행된다.
	public void setTemplate(JdbcTemplate template) {
		// logger.info("꺄오~~~~~~~~~~");
		// logger.info("{}", template);
		this.template = template;
		
		// 여기까지 정상적으로 실행되면 컨트롤러에서 JdbcTemplate을 사용할 수 있게된다.
		// 프로젝트에서 실행할 sql 명령은 데이터베이스와 연결한 후 주로 DAO 클래스에서 실행하므로 
		// 컨트롤러 이외의 클래스에서 JdbcTemplate을 사용할 수 있게 하기 위해서 적당한 이름의 
		// 패키지(base-package)에 적당한 이름의 클래스(Constant)를 만들고 정적 필드에 servlet-content.xml
		// 파일에서 생성해서 @Autowired 어노테이션을 사용해서 초기화한 JdbcTemplate 클래스의 bean을
		// 넣어준다.
		
		// 적당한 이름의 패키지에 적당한 이름의 클래스를 선언하고 JdbcTemplate 객체를 public static으로
		// 선언한 후 코딩한다.
		Constant.template = this.template; 
	}

	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		logger.info("Mvcboard 게시판 실행");
		return "redirect:list";
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		logger.info("HomeController의 insert() 메소드 실행");
		return "insert";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		logger.info("HomeController의 insertOK() 메소드 실행 => Model 인터페이스 객체 사용");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("HomeController의 list() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		return "list";
	}
	
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("HomeController의 increment() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		model.addAttribute("idx", request.getParameter("idx"));
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		logger.info("HomeController의 contentView() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		return "contentView";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		logger.info("HomeController의 update() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		logger.info("HomeController의 delete() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		logger.info("HomeController의 reply() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		return "reply";
	}
	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model) {
		logger.info("HomeController의 replyInsert() 메소드 실행");
		model.addAttribute("request", request);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		return "redirect:list";
	}
	
}



























