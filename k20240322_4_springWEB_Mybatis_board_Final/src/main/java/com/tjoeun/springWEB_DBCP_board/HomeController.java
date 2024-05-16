package com.tjoeun.springWEB_DBCP_board;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.springWEB_DBCP_board.dao.MybatisDAO;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardList;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	mapper 초기화
	@Autowired
	private SqlSession sqlSession; 
//	sqlSession 인터페이스가 sqlSessionTemplate의 상위 클래스이고 또한 sqlSessionTemplate이 sqlSession의 구현체이므로 
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
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
		mapper.insert(mvcboardVO);
		
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		logger.info("HomeController의 list() 메소드 실행");
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
		int pageSize = 10;
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) { }
		int totalCount = mapper.selectCount();
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		mvcboardList.setList(mapper.selectList(hmap));
		
		model.addAttribute("mvcboardList", mvcboardList);
		
		return "list";
	}
	
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("HomeController의 increment() 메소드 실행");
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		int idx =  Integer.parseInt( request.getParameter("idx") );
		mapper.increment(idx);
		
		model.addAttribute("idx", request.getParameter("idx"));
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		int idx = Integer.parseInt( request.getParameter("idx") );
		
		MvcboardVO mvcboardVO = mapper.selectByIdx(idx);
		
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");
		return "contentView";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
		mapper.update(mvcboardVO);
		
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		int idx = Integer.parseInt(request.getParameter("idx"));
		mapper.delete(idx);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		logger.info("HomeController의 reply() 메소드 실행");
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		int idx = Integer.parseInt( request.getParameter("idx") );
		MvcboardVO mvcboardVO = mapper.selectByIdx(idx);
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");
		
		return "reply";
	}
	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model, MvcboardVO mvcboardVO) {
		logger.info("HomeController의 replyInsert() 메소드 실행");
		
		MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
		
		mvcboardVO.setLev(mvcboardVO.getLev() + 1);
		mvcboardVO.setSeq(mvcboardVO.getSeq() + 1);
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("gup", mvcboardVO.getGup());
		hmap.put("seq", mvcboardVO.getSeq());
		mapper.replyIncrement(hmap);
		
		mapper.replyInsert(mvcboardVO);
		
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		return "redirect:list";
	}
	
}



























