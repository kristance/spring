package com.tjoeun.springWEB_DBCP_board.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.springWEB_DBCP_board.dao.MvcboardDAO;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardList;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

public class SelectService implements MvcboardService {

	private static final Logger logger = LoggerFactory.getLogger(SelectService.class);
	
	@Override
	public void execute(MvcboardVO mvcboardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("SelectService 클래스의 execute() 메소드 실행");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		// logger.info("{}", request.getParameter("currentPage"));
		
		// MvcboardDAO 클래스의 bean을 얻어온다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
		
		// 브라우저에 출력할 글의 개수를 정한다.
		int pageSize = 10;
		// HttpServletRequest 인터페이스 객체에 저장되서 넘어오는 화면에 표시할 페이지 번호를 받는다.
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) { }
		// logger.info("{}", currentPage);
		// 테이블에 저장된 전체 글의 개수를 얻어온다.
		int totalCount = mvcboardDAO.selectCount();
		// logger.info("{}", totalCount);
		
		// 1페이지 분량의 글 목록과 페이징 작업에 사용할 8개의 변수를 기억하는 MvcboardList 클래스의
		// bean을 얻어온다.
		MvcboardList mvcboardList = ctx.getBean("mvcboardList", MvcboardList.class);
		// jsp로 작업할 때 처럼 생성자를 사용해서 초기화가 불가능하므로 MvcboardList 클래스의 bean을
		// 얻어온 후 8개의 변수를 초기화시키는 메소드를 실행한다.
		mvcboardList.initMvcboardList(pageSize, totalCount, currentPage);
		// logger.info("{}", mvcboardList);
		
		// MvcboardList 클래스의 1페이지 분량의 글 목록을 기억하는 ArrayList에 1페이지 분량의 글 목록을
		// 얻어와서 넣어주는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", mvcboardList.getStartNo());
		hmap.put("endNo", mvcboardList.getEndNo());
		mvcboardList.setList(mvcboardDAO.selectList(hmap));
		// logger.info("{}", mvcboardList);
		
		// list.jsp로 넘겨줄 데이터를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("mvcboardList", mvcboardList);
	}

}



















