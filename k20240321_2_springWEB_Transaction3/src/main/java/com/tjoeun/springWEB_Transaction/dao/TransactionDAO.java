package com.tjoeun.springWEB_Transaction.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.springWEB_Transaction.vo.CardVO;

public class TransactionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		
	}
	
	
//	PlatformTransactionManager 인터페이스보다 더 많이 사용되고 개발자의 수고를 덜어주는 TransactionTemplate 클래스 객체를
//	사용해 트랜젝션 처리를 한다.
//	PlatformTransactionManager 인터페이스 대신 TransactionTemplate 클래스 객체를 사용해 트랜젝션을 처리하기때문에
//	PlatformTransactionManager 인터페이스의 객체 선언과 setter 부분을 주석으로 처리한다.

//	private PlatformTransactionManager transactionManager;
//	public void setTransactionManager(PlatformTransactionManager transactionManager) {
//		this.transactionManager = transactionManager;
//	}
	
//	PlatformTransactionManager 인터페이스 객체 대신 TransactionTemplate 클래스 객체를 사용해서 트랜젝션을 처리하기 위해
//	TransactionTemplate 클래스의 객체를 선언한다.
	private TransactionTemplate transactionTemplate; 
	
//	트랜젝션 처리를 위해 사용했던 PlatformTransactionManager 인터페이스 객체를 대신해서 사용할 TransactionTemplate 클래스
//	객체를 초기화하기 위해 setter 메소드를 선언한고 servlet-context.xml 파일에서 초기화시킨다.
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	

	public void buyTicket(CardVO cardVO) {
		logger.info("TransactionDAO -> buyTicket() 실행");
		
//	PlatformTransactionManager 인터페이스 대신 TransactionTemplate 클래스 객체를 사용해 트랜젝션을 처리하기때문에
//	PlatformTransactionManager 인터페이스의 객체 선언과 setter 부분을 주석으로 처리한다.
		
//		TransactionDefinition definition = new DefaultTransactionDefinition();
//		TransactionStatus status = transactionManager.getTransaction(definition);
		/*
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
			
			
			logger.info("### 트랜젝션 정상 실행 ### >>>> commit");
			transactionManager.commit(status);
		} catch (Exception e) {
			logger.info("### 트랜젝션 정상 실행 안됨 ### >>>> rollback");
			transactionManager.rollback(status);
		}
		*/
		
//		TransactionTemplate 클래스 객체를 사용하는 트랜젝션
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
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
					
				}
			});
			logger.info("### 트랜젝션 정상 실행 ### >>>> commit");
		} catch (Exception e) {
			logger.info("### 트랜젝션 정상 실행 안됨 ### >>>> rollback");
			
		}
		
		
	}


	
}
