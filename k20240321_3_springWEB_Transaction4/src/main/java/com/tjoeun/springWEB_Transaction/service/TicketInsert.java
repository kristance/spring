package com.tjoeun.springWEB_Transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.springWEB_Transaction.dao.TransactionDAO;
import com.tjoeun.springWEB_Transaction.vo.CardVO;

public class TicketInsert implements TransactionService{

	private static final Logger logger = LoggerFactory.getLogger(TicketInsert.class);
	
	private TransactionDAO dao;
//	트랜젝션을 2개를 사용해야하므로 TransactionTemplate 클래스 객체를 만든다.
	private TransactionTemplate transactionTemplate2; // 외부 트랜젝션
	
	
//	TransactionDAO, TransactionTemplate 클래스 객체를 초기화시키기 위해 setter를 만든다.
	public void setDao(TransactionDAO dao) {
		this.dao = dao;
	}
	
	public void setTransactionTemplate2(TransactionTemplate transactionTemplate2) {
		this.transactionTemplate2 = transactionTemplate2;
	}


	@Override
	public void execute(CardVO cardVO) {
		logger.info("TicketInsert -> execute(CardVO) 실행");
//		ticket.jsp의 입력값과 상관없이 외부 트랜젝션이 있을 때와 없을 때를 구분해서 실행시켜
//		트랜젝션 전파속성 옵션을 확인한다.
//		servlet-context.xml 파일에서 TransactionTemplate 클래스의 bean을 설정할 때 propagationBehavior 속성값으로
//		트랜젝션 전파속성 옵션을 지정한다.
		
//		==============================================================================================================
		
//		REQUIRED(기본값, 0) <--> REQUIRES_NEW(3)
//		REQUIRED
//		외부 트랜젝션이 존재한다면 외부 트랜젝션으로 합류하고, 외부 트랜젝션이 없다면 새로운 트랜젝션을 생성한다.
//		중간에 롤백이 발생한다면 모두 하나의 트랜젝션이기때문에 진행 상황이 모두 롤백된다.
//		REQUIRES_NEW
//		무조건 새로운 트랙젝션을 생성하고 각각의 트랜젝션이 롤백되더라도 서로 영향을 주지 않는다.
		
//		외부 트랜젝션(transactionTemplate2)이 없고, 내부 트랜젝션 (transactionTemplate)이 0이면 모두 적용된다. -> 정상 처리
//		외부 트랜젝션이 없고, 내부 트랜젝션이 0이면 내부 트랜젝션이 정상처리된다.
//		외부 트랜젝션이 있고, 내부 트랜젝션이 0이면 내부,외부 트랜젝션이 모두 정상이어야 정상 처리 된다.
//		==============================================================================================================
		
//		SUPPORTS(1) <--> NOT_SUPPORTED(4)
//		SUPPORTS
//		외부 트랜젝션이 있다면 내부 트랜젝션이 외부 트랜젝션에 합류하고, 외부 트랜젝션이 없다면 내부 트랜젝션을 생성하지 않는다.
//		NOT_SUPPORTED
//		외부 트랜젝션이 있다면 보류시키고, 외부 트랜젝션이 없다면 트랜젝션을 실행하지 않는다.
		
//		외부 트랜젝션이 없고, 내부 트랜젝션이 1이면 내부 트랜젝션을 생성하지 않는다.
//		외부 트랜젝션이 있고, 내부 트랜젝션이 1이면 내부,외부 트랜젝션이 모두 정상이어야 정상 처리 된다.
//		==============================================================================================================
		
//		MANDATORY(2) <--> NEVER(5)
//		MANDATORY
//		외부 트랜젝션이 있다면 내부 트랜젝션이 외부 트랜젝션에 합류하고, 외부 트랜젝션이 없다면 예외를 발생시킨다.
//		NEVER
//		내부 트랜젝션을 생성하지 않고, 외부 트랜젝션이 존재한다면 예외를 발생시킨다.
		
//		외부 트랜젝션이 없고, 내부 트랜젝션이 2이면 내부 트랜젝션에서 예외가 발생된다.
//		외부 트랜젝션이 있고, 내부 트랜젝션이 2이면 내부,외부 트랜젝션이 모두 정상이어야 정상 처리 된다.
//		==============================================================================================================
		
		
//		외부 트랜젝션이 없을 경우 -> TransactionDAO 클래스의 트랜젝션을 바로 실행한다.
//		cardVO.setAmount("4"); // 정상 실행
//		dao.buyTicket(cardVO);
//		cardVO.setAmount("5"); // 오류 발생
//		dao.buyTicket(cardVO);
		
		
//		외부 트랜젝션이 있을 경우
		try {
			logger.info("TicketInsert -> execute(CardVO) -> ##외부## 트랜젝션 실행 ");
			transactionTemplate2.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					cardVO.setAmount("4"); // 정상 실행
					dao.buyTicket(cardVO); 
					cardVO.setAmount("5"); // 오류 발생
					dao.buyTicket(cardVO);
				}
			});
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("### Exception 발생 ###");
		}
		
		
		
		
	}

	
}
