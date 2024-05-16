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
	
	private TransactionDAO dao;
	
	@Autowired
	public void setDao(TransactionDAO dao) { 
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

		dao.buyTicket(cardVO);
		
		return "ticketEnd";
	}
	
	
}
