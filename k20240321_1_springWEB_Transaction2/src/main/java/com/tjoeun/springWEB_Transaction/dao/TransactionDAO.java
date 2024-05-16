package com.tjoeun.springWEB_Transaction.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tjoeun.springWEB_Transaction.vo.CardVO;

public class TransactionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		
	}
	
//	트랜젝션 처리를 위해 PlatformTransactionManager 인터페이스 객체를 선언한다.
	private PlatformTransactionManager transactionManager;
	
//	트랜젝션 처리를 위한 PlatformTransactionManager 인터페이스 객체를 초기화하기 위해
//	setter 메소드를 선언하고, servlet-context.xml 파일에서 초기화한다.
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
	

	public void buyTicket(CardVO cardVO) {
		logger.info("TransactionDAO -> buyTicket() 실행");
		
//		트랜젝션 상태(commit, rollback)를 처리할 TransactionDefinition 인터페이스 객체를 생성하고
//		PlatformTransactionManager 인터페이스 객체를 사용해 초기화시킨다.
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
		
//		try ~ catch로 트랜젝션 처리를 한다.
		try {
			String sql = "insert into card(consumerid, amount) values (?, ?)";
			template.update(sql, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, cardVO.getConsumerId());
					ps.setString(2, cardVO.getAmount());
				}
			});
			
			sql = "insert into ticket(consumerid, countnum) values (?, ?)";
			template.update(sql, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, cardVO.getConsumerId());
					ps.setString(2, cardVO.getAmount());
				}
			});
			
			
//			트랜젝션의 모든 sql 명령이 정상 실행되면 트랜젝션을 commit 시킨다.
			logger.info("### 트랜젝션 정상 실행 ### >>>> commit");
			transactionManager.commit(status);
		} catch (Exception e) {
//			트랜젝션의 모든 sql 명령이 정상 실행되지 않으면 트랜젝션을 rollback 시킨다.
			logger.info("### 트랜젝션 정상 실행 안됨 ### >>>> rollback");
			transactionManager.rollback(status);
		}
		
		
		
		
		
	}

	
}
