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
	
	
	private TransactionTemplate transactionTemplate; // 내부 트랜젝션
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	

	public void buyTicket(CardVO cardVO) {
		logger.info("TransactionDAO -> buyTicket() ");
		
		try {
			logger.info("TransactionDAO -> buyTicket() 실행 -> %%내부%% 트랜젝션 실행");
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
