package com.tjoeun.springWEB_Transaction.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_Transaction.vo.CardVO;

public class TransactionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	
//	JdbcTemplate 객체를 선언
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		
		
		
	}

	public void buyTicket(CardVO cardVO) {
		logger.info("TransactionDAO -> buyTicket() 실행");
		logger.info("{}",cardVO);
		
		String sql = "insert into card(consumerid, amount) values (?, ?)";
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, cardVO.getConsumerId());
				ps.setString(2, cardVO.getAmount());
			}
		});
		
//		sql = "insert into ticket(consumerid, countnum) values (?, ?)";
//		template.update(sql, new PreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setString(1, cardVO.getConsumerId());
//				ps.setString(2, cardVO.getAmount());
//			}
//		});
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into ticket(consumerid, countnum) values (?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, cardVO.getConsumerId());
				ps.setString(2, cardVO.getAmount());
				return ps;
			}
		});
		
		
		
		
		
	}
	
	
	
	
	
	
}
