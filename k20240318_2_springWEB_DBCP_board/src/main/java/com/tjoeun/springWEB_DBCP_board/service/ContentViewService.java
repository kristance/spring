package com.tjoeun.springWEB_DBCP_board.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.springWEB_DBCP_board.dao.MvcboardDAO;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

public class ContentViewService implements MvcboardService {

	private static final Logger logger = LoggerFactory.getLogger(ContentViewService.class);
	
	@Override
	public void execute(MvcboardVO mvcboardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("ContentViewService 클래스의 execute() 메소드 실행");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		// logger.info("얻어올 글번호: {}, 돌아갈 페이지 번호: {}", request.getParameter("idx"), request.getParameter("currentPage"));
		
		// Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 
		// 글번호를 받는다.
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		// 글 1건을 얻어오는 메소드를 실행한다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
		MvcboardDAO mvcboardDAO = ctx.getBean("mvcboardDAO", MvcboardDAO.class);
		// MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
		// mvcboardVO = MvcboardDAO.selectByIdx(idx);
		MvcboardVO mvcboardVO = mvcboardDAO.selectByIdx(idx);
		// logger.info("얻어온 글: {}", mvcboardVO);
		
		// contentView.jsp로 넘겨줄 글 1건, 작업 후 돌아갈 페이지 번호, 줄바꿈에 사용할 "\r\n"을 Model
		// 인터페이스 객체에 넣어준다.
		model.addAttribute("vo", mvcboardVO);
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		model.addAttribute("enter", "\r\n");
	}

}







