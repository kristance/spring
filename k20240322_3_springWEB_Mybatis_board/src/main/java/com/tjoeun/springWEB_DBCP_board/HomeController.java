package com.tjoeun.springWEB_DBCP_board;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.springWEB_DBCP_board.dao.MybatisDAO;
import com.tjoeun.springWEB_DBCP_board.service.ContentViewService;
import com.tjoeun.springWEB_DBCP_board.service.DeleteService;
import com.tjoeun.springWEB_DBCP_board.service.IncrementService;
import com.tjoeun.springWEB_DBCP_board.service.InsertService;
import com.tjoeun.springWEB_DBCP_board.service.MvcboardService;
import com.tjoeun.springWEB_DBCP_board.service.ReplyService;
import com.tjoeun.springWEB_DBCP_board.service.SelectService;
import com.tjoeun.springWEB_DBCP_board.service.UpdateService;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardList;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
//	여기부터 ===============================================
//	private JdbcTemplate template; 
//
//	@Autowired
//	public void setTemplate(JdbcTemplate template) {
//		this.template = template;
//		
//		Constant.template = this.template; 
//	}
//	여기까지 Mybatis로 변환이 완료되면 주석으로 처리한다.==========
	
	/*
//	servlet-context.xml 파일에서 생성한 Mybatis bean(sqlSession)을 사용하기 위해\
//	SqlSession 인터페이스 타입의 객체를 생성한다.
	private SqlSession sqlSession;

//	@Autowired를 붙여 선언한 SqlSession 인터페이스 객체의 setter 메소드로 servlet-context.xml 파일에서
//	선언된 SqlSessionTemplate 클래스의 bean으로 SqlSession 인터페이스 객첼르 초기화한다.
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	*/
	
//	SqlSession 인터페이스 객체를 선언할 때 @Autowired 어노테이션을 붙여주면 Spring이 알아서
//	setter 메소드를 실행해서 servlet-context.xml 파일에서 선언된 SqlSessionTemplate 클래스의 bean으로 초기화시켜준다.
	@Autowired
	private SqlSession sqlSession; 
// sqlSession 인터페이스가 sqlSessionTemplate의 상위 클래스이고 또한 sqlSessionTemplate이 sqlSession의 구현체이므로 
//	servlet-context.xml에 생성된 sqlsessionTemplate의 bean인 sqlSession은 상위 클래스인 SqlSession 속하여 초기화할수 있다.
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		logger.info("HomeController -> sqlSession -> {}", sqlSession);
		logger.info("Mvcboard 게시판 실행");
		return "redirect:list";
	}

	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		logger.info("HomeController의 insert() 메소드 실행");
		return "insert";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		logger.info("HomeController의 insertOK() 메소드 실행 => 커맨드 객체 객체 사용");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("insert", InsertService.class);
//		service.execute(model);
		
//		logger.info("insertOK ->  {}", mvcboardVO);
//		mapper(SqlSession 인터페이스 객체)에 mapper로 사용하려는 인터페이스(MybatisDAO)를 넣어준다.
//		SqlSession 인터페이스 객체에서 getMapper() 메소드로 mapper를 얻어와서 프로그램에서 mapper로
//		사용하려는 인터페이스 (MybatisDAO) 객체에 넣어준다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		메인글을 저장하는 insert sql 명령을 실행한다.
		mapper.insert(mvcboardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("HomeController의 list() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("select", SelectService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		1페이지에 표시할 글의 갯수, 브라우저에 표시할 페이지 번호, 전체 글의 갯수를 저장한다.
		int pageSize = 10;
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) { }
		int totalCount = mapper.selectCount();
//		1페이지 분량의 글 목록과 페이징 작업에 사용할 변수를 초기화한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
		
//		1페이지 분량의 글 목록을 얻어온다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		mvcboardList.setList(mapper.selectList(hmap));
		
//		list.jsp로 넘겨줄 데이터를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("mvcboardList", mvcboardList);
		
		return "list";
	}
	
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("HomeController의 increment() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("increment", IncrementService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		조회수를 증가시킬 글번호를 받는다.
		int idx =  Integer.parseInt( request.getParameter("idx") );
//		조회수를 증가시킬 메소드를 실행한다.
		mapper.increment(idx);
		
//		조회수를 증가시킨 글번호와 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("idx", request.getParameter("idx"));
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
//		logger.info("HomeController의 contentView() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		얻어올 글 번호를 받는다.
		int idx = Integer.parseInt( request.getParameter("idx") );
		
//		글 1건을 얻어와서 MvcboardVO 클래스에 저장한다.
		MvcboardVO mvcboardVO = mapper.selectByIdx(idx);
		
//		얻어온 글, 돌아갈 글 번호, 줄바꿈에 사용할 "\r\n"을 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");
		return "contentView";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
//		logger.info("HomeController의 update() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("update", UpdateService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		넘어오는 수정할 데이터를 받아서 글 1건을 수정하는 메소드를 실행한다.
		mapper.update(mvcboardVO);
		
//		글 수정 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
//		logger.info("HomeController의 delete() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("delete", DeleteService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		삭제할 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
//		글 1건을 삭제하는 메소드를 실행한다.
		mapper.delete(idx);
//		글 삭제 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		logger.info("HomeController의 reply() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("contentView", ContentViewService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
//		답글을 입력할 글번호를 받는다.
		int idx = Integer.parseInt( request.getParameter("idx") );
//		답글을 입력할 글을 얻어와서 MvcboardVO 클래스 객체에 저장한다.
		MvcboardVO mvcboardVO = mapper.selectByIdx(idx);
//		답글을 입력할 글, 돌아갈 글 번호, 줄바꿈에 사용할 "\r\n"을 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");
		
		return "reply";
	}
	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		logger.info("HomeController의 replyInsert() 메소드 실행");
//		model.addAttribute("request", request);
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//		MvcboardService service = ctx.getBean("reply", ReplyService.class);
//		service.execute(model);
		
//		mapper를 얻어온다.
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
//		커맨드 객체를 사용해서 답글 데이터를 받아서 답글은 질문글 바로 아래에 위치해야하므로
//		lev와 seq를 1씩 증가시킨다.
		mvcboardVO.setLev(mvcboardVO.getLev() + 1);
		mvcboardVO.setSeq(mvcboardVO.getSeq() + 1);
		
//		답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시키는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("gup", mvcboardVO.getGup());
		hmap.put("seq", mvcboardVO.getSeq());
		mapper.replyIncrement(hmap);
		
//		답글을 저장하는 메소드를 실행한다.
		mapper.replyInsert(mvcboardVO);
		
//		답글 저장 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		return "redirect:list";
	}
	
}



























