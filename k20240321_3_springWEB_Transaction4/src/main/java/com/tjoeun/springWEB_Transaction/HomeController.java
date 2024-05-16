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
import com.tjoeun.springWEB_Transaction.service.TransactionService;
import com.tjoeun.springWEB_Transaction.vo.CardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
//	외부 트랜젝션을 실행하기 위해 TicketInsert 클래스 객체로 TransactionDAO 클래스 객체에
//	접근하므로 주석으로 처리한다.
//	private TransactionDAO dao;
//	
//	@Autowired
//	public void setDao(TransactionDAO dao) { 
//		this.dao = dao;
//	}
	
//	외부 트랜젝션을 실행하기 위해서 TransactionService 인터페이스 객체를 선언하고 초기화한다.
	private TransactionService service;
	
//	servlet-context.xml 파일에서 설정한 TicketInsert 클래스의 bean이 자동으로 채워진다.
	@Autowired
	public void setService(TransactionService service) {
		this.service = service;
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

//		dao.buyTicket(cardVO);
//		dao의 buyTicket() 메소드를 바로 실행(내부 트랜젝션)하지 않고 TicketInsert 클래스에서 
//		외부 트랜젝션을 실행한다.
		service.execute(cardVO);
		
		
		return "ticketEnd";
	}
	
	
}
