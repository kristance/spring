package com.tjoeun.springWEB_Transaction;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tjoeun.springWEB_Transaction.dao.TransactionDAO;
import com.tjoeun.springWEB_Transaction.vo.CardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	TransactionDAO 클래스의 메소드를 실행하기 위해서 servlet-context.xml 에서 설정한 bean을 얻어와야하므로
//	TransactionDAO 클래스 객체를 선언한다.
	private TransactionDAO dao;
	
//	@Autowired 어노테이션을 이용해서 servlet-context.xml 파일에서 설정한 TransactionDAO 클래스의
//	bean을 얻어온다.
	@Autowired
	public void setDao(TransactionDAO dao) { 
// 		서버가 구동될 때 servlet-context.xml에서 생성된 bean(jdbc연결정보를 ref로 참조하는 dao bean)이 TransactionDAO 클래스 dao 인수로 Spring이 자동으로 넣어준다.
//		logger.info("HomeController -> setDao() 실행");
//		logger.info("dao -> {}", dao);
		this.dao = dao;
	}
	
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "ticket";
	}
	
	@RequestMapping("/ticket")
	public String ticket (HttpServletRequest request, Model model) {
		logger.info("HomeController -> ticket() 실행");
		
		return "ticket";
	}
	
	@RequestMapping("/ticketCard")
	public String ticketCard(HttpServletRequest request, Model model, CardVO cardVO) {
		logger.info("HomeController -> ticketCard() 실행");
		logger.info("ConsumerId -> {} ,, amount -> {}", request.getParameter("consumerId"), request.getParameter("amount"));

//		String consumerId = request.getParameter("consumerId");
//		String amount = request.getParameter("amount");
//		model.addAttribute("consumerId", consumerId);
//		model.addAttribute("amount", amount);
		
//		logger.info("cardVO : {}", cardVO);
//		model.addAttribute("ticketInfo", cardVO);
		
//		티켓 구매 정보를 테이블에 저장하는 메소드를 실행한다.
		dao.buyTicket(cardVO);
		
		return "ticketEnd";
	}
	
	
}
