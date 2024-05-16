package com.tjoeun.springWEB_DBCP_board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

public class MvcboardDAO {

	private static final Logger logger = LoggerFactory.getLogger(MvcboardDAO.class);
	
//	DBCP에 사용할 DataSource 인터페이스 객체를 선언한다.
	private DataSource dataSource;
	
//	기본 생성자를 이용해서 MvcboardDAO 클래스의 bean이 생성될 때 오라클과 연결한다.
	public MvcboardDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
			// logger.info("연결 성공: {}", dataSource);
		} catch (NamingException e) {
			// e.printStackTrace();
			logger.info("연결 실패");
		}
	}
	
//	InsertService 클래스에서 호출되는 메인글을 저장하는 insert sql 명령을 실행하는 메소드
	public void insert(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 insert() 메소드 실행");
		// logger.info("{}", mvcboardVO);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
				"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcboardVO.getName());
			pstmt.setString(2, mvcboardVO.getSubject());
			pstmt.setString(3, mvcboardVO.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	SelectService 클래스에서 호출되는 테이블에 저장된 전체 글의 개수를 얻어오는 select sql 명령을 실행하는
//	메소드
	public int selectCount() {
		logger.info("MvcboardDAO 클래스의 selectCount() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0; // 테이블에 저장된 전체 글의 개수를 저장해서 리턴시킬 변수를 선언한다.
		
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) as cnt from mvcboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 테이블에 저장된 글의 개수는 null이 나올리가 없으므로 조건을 비교할 필요는 없다.
			rs.next();
			// result = rs.getInt(1);
			result = rs.getInt("cnt");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return result;
	}

//	SelectService 클래스에서 호출되는 브라우저에 출력할 1페이지 분량의 글 목록을 얻어오기 위해서 시작 인덱스,
//	끝 인덱스가 저장된 HashMap 객체를 넘겨받고 1페이지 분량의 글 목록을 얻어오는 select sql 명령을 실행하는
//	메소드
	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
		logger.info("MvcboardDAO 클래스의 selectList() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MvcboardVO> list = null; // 1페이지 분량의 글 목록을 저장해 리턴시킬 ArrayList 선언
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from (" +
						 "    select rownum rnum, AA.* from (" +
						 "        select * from mvcboard order by gup desc, seq asc" +
						 "    ) AA where rownum <= ?" +
						 ") where rnum >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("endNo"));
			pstmt.setInt(2, hmap.get("startNo"));
			rs = pstmt.executeQuery();
			
			// ResultSet 객체에 저장된 1페이지 분량의 글 목록을 저장할 ArrayList 객체 생성
			list = new ArrayList<MvcboardVO>();
			// ResultSet 객체에 저장된 글의 개수만큼 반복하며 ArrayList 저장한다.
			while (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
				MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
				mvcboardVO.setIdx(rs.getInt("idx"));
				mvcboardVO.setName(rs.getString("name"));
				mvcboardVO.setSubject(rs.getString("subject"));
				mvcboardVO.setContent(rs.getString("content"));
				mvcboardVO.setGup(rs.getInt("gup"));
				mvcboardVO.setLev(rs.getInt("lev"));
				mvcboardVO.setSeq(rs.getInt("seq"));
				mvcboardVO.setHit(rs.getInt("hit"));
				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
				list.add(mvcboardVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return list;
	}

//	IncrementService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는 update sql
//	명령을 실행하는 메소드
	public void increment(int idx) {
		logger.info("MvcboardDAO 클래스의 increment() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	ContentViewService 클래스에서 호출되는 글번호를 넘겨받고 글 1건을 얻어오는 select sql 명령을 실행하는 메소드
	public MvcboardVO selectByIdx(int idx) {
		logger.info("MvcboardDAO 클래스의 selectByIdx() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MvcboardVO mvcboardVO = null; // 글 1건을 얻어와서 저장시켜 리턴할 객체 선언
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			// ResultSet 객체에 저장된 글 1건을 리턴시키기 위해서 MvcboardVO 클래스 객체에 저장한다.
			if (rs.next()) {
				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
				mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
				mvcboardVO.setIdx(rs.getInt("idx"));
				mvcboardVO.setName(rs.getString("name"));
				mvcboardVO.setSubject(rs.getString("subject"));
				mvcboardVO.setContent(rs.getString("content"));
				mvcboardVO.setGup(rs.getInt("gup"));
				mvcboardVO.setLev(rs.getInt("lev"));
				mvcboardVO.setSeq(rs.getInt("seq"));
				mvcboardVO.setHit(rs.getInt("hit"));
				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return mvcboardVO;
	}

//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터를 넘겨받고 글 1건을 수정하는 update sql 명령을
//	실행하는 메소드
	public void update(int idx, String subject, String content) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	UpdateService 클래스에서 호출되는 수정할 글번호와 데이터가 저장된 MvcboardVO 클래스 객체를 넘겨받고 
//	글 1건을 수정하는 update sql 명령을 실행하는 메소드
	public void update(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcboardVO.getSubject());
			pstmt.setString(2, mvcboardVO.getContent());
			pstmt.setInt(3, mvcboardVO.getIdx());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	DeleteService 클래스에서 호출되는 삭제할 글번호를 넘겨받고 글 1건을 삭제하는 delete sql 명령을 실행하는
//	메소드
	public void delete(int idx) {
		logger.info("MvcboardDAO 클래스의 delete() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from mvcboard where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	ReplyService 클래스에서 호출되는 글그룹과 글이 출력되는 순서가 저장된 HashMap 객체를 넘겨받고
//	조건에 만족하는 레코드의 seq를 1씩 증가시키는 update sql 명령을 실행하는 메소드
	public void replyIncrement(HashMap<String, Integer> hmap) {
		logger.info("MvcboardDAO 클래스의 replyIncrement() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hmap.get("gup"));
			pstmt.setInt(2, hmap.get("seq"));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}

//	ReplyService 클래스에서 호출되는 답글이 저장된 객체를 넘겨받고 답글을 저장하는 insert sql 명령을
//	실행하는 메소드
	public void replyInsert(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 replyInsert() 메소드 실행");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
					"values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mvcboardVO.getName());
			pstmt.setString(2, mvcboardVO.getSubject());
			pstmt.setString(3, mvcboardVO.getContent());
			pstmt.setInt(4, mvcboardVO.getGup());
			pstmt.setInt(5, mvcboardVO.getLev());
			pstmt.setInt(6, mvcboardVO.getSeq());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
	}
	
}













