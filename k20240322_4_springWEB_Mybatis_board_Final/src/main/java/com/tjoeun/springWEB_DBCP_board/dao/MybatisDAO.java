package com.tjoeun.springWEB_DBCP_board.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

//	mapper로 사용할 인터페이스
//	이 인터페이스의 풀 패키지와 이름을 sql 명령이 저장된 xml 파일의 namespace에 정확히 적어야한다.
public interface MybatisDAO {


//	public abstract 리턴타입 메소드이름(변수); // 일반적인 추상 메소드 형식
	
//	mapper로 사용할 인터페이스의 추상 메소드 형식은 아래와 같다.
//	<select id="" parameterType="" resultType="" > // sql 명령을 실행하는 xml 파일의 태그
//	public abstract resultType id(parameterType); // mapper 추상 메소드 형식
//	추상메소드 이름이 xml 파일의 실행할 sql명령을 식별하는 id로 사용되고 리턴 타입이 resultType으로
//	사용되고 parameterType이 메소드로 전달되는 인수로 사용된다.
	
	
	void insert(MvcboardVO mvcboardVO);

	int selectCount();

	ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap);

	void increment(int idx);

	MvcboardVO selectByIdx(int idx);


	void update(MvcboardVO mvcboardVO);

	void delete(int idx);

	void replyIncrement(HashMap<String, Integer> hmap);

	void replyInsert(MvcboardVO mvcboardVO);

	
	
}
