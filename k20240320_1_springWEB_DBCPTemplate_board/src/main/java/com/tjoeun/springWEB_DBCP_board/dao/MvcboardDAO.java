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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_DBCP_board.Constant;
import com.tjoeun.springWEB_DBCP_board.vo.MvcboardVO;

public class MvcboardDAO {

	private static final Logger logger = LoggerFactory.getLogger(MvcboardDAO.class);
	
//	JdbcTemplate 설정
//	MvcboardDAO 클래스에서 JdbcTemplate을 사용하기 위해 JdbcTemplate 클래스 타입의 객체를 선언한다.
	private JdbcTemplate template;
	
//	MvcboardDAO 클래스이 bean이 기본 생성자로 생성되는 순간 servlet-content.xml 파일에서 생성되서
//	컨트롤러가 전달받아 Constant 클래스의 JdbcTemplate 클래스 타입의 static 객체에 저장한 bean으로
//	초기화 한다.
	public MvcboardDAO() {
		template = Constant.template;
	}
	
//	===============================================================================================
//	여기부터
//	private DataSource dataSource;
//	
//	public MvcboardDAO() {
//		template = Constant.template;
//		System.out.println("MvcboardDAO 생성자 => " + template);
//		logger.info("MvcboardDAO 생성자 => {}", template);
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
//		} catch (NamingException e) {
//			logger.info("연결 실패");
//		}
//	}
//	여기까지는 DBCP 방식으로 사용하는 객체를 초기화하는 부분이므로 JdbcTemplate으로 코드 변환이
//	완료되면 모두 주석으로 처리한다.
//	===============================================================================================
	
//	spring 3.1.x 버전까지는 insert, delete, update sql 명령을 실행하는 메소드의 인수로 넘어온 데이터가 중간에
//	값이 변경되면 안되기 때문에 JdbcTemplate에서는 insert, delete, update sql 명령을 실행하는 메소드의 인수를 선언할 때
//	반드시 final을 붙여서 인수로 넘어온 데이터를 수정할 수 없도록 인수 선언을 해야했었다.
//	spring 4 버전 이후부터는 final을 붙이지 않아도 실행된다.
	public void insert( MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 insert() 메소드 실행");
		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//				"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcboardVO.getName());
//			pstmt.setString(2, mvcboardVO.getSubject());
//			pstmt.setString(3, mvcboardVO.getContent());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
		
			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
				"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
//			
//		update() : 테이블의 내용이 갱신되는 sql 명령 -> insert, delete, update
//		query() : 테이블의 내용이 갱신되지 않는 sql 명령 -> select -> 결과가 여러 건일 경우
//		queryforInt() : 테이블의 내용이 갱신되지 않는 sql 명령 -> select -> 결과가 정수 1건일 경우
//		queryforObject() : 테이블의 내용이 갱신되지 않는 sql 명령 -> select -> 결과가 1건일 경우
			
//		update(실행할sql명령, "?"를 채운다.)
//		update() 메소드의 2번쨰 인수로 PreparedStatementSetter 인터페이스  객체를 익명으로 구현해서 Override된
//		setValues() 메소드에서 실행할 sql 명령의 "?" 를 채운다.
//		"?"가 많을 때
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
//				sql 명령의 "?"를 채운다.
				ps.setString(1, mvcboardVO.getName());
				ps.setString(2, mvcboardVO.getSubject());
				ps.setString(3, mvcboardVO.getContent());
				
			}
		});
		
//			"?"가 없거나 1개 정도 있다면 아래 방식을 사용하는 것도 효과적이다.
//			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//					"values (mvcboard_idx_seq.nextval, '" +
//					mvcboardVO.getName() + 
//					"', '" + mvcboardVO.getSubject() + 
//					"', '"+ mvcboardVO.getContent() +
//					"', mvcboard_idx_seq.currval, 0, 0)";
		
//			String sql = String.format( "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//				"values (mvcboard_idx_seq.nextval, '%s', '%s', '%s', mvcboard_idx_seq.currval, 0, 0)"
//				, mvcboardVO.getName(), mvcboardVO.getSubject(), mvcboardVO.getContent());
//			template.update(sql);
	}

	public int selectCount() {
		logger.info("MvcboardDAO 클래스의 selectCount() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int result = 0;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select count(*) as cnt from mvcboard";
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			rs.next();
//			result = rs.getInt("cnt");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
//		return result;
		
		String sql = "select count(*) as cnt from mvcboard";
		
//		queryForInt(실행할 sql 명령)
//		return template.queryForInt(sql);
//		queryForObject(실행할 sql 명령, 리턴타입.class)
		return template.queryForObject(sql, Integer.class);
	}

	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
//		logger.info("MvcboardDAO 클래스의 selectList() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		ArrayList<MvcboardVO> list = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from (" +
//						 "    select rownum rnum, AA.* from (" +
//						 "        select * from mvcboard order by gup desc, seq asc" +
//						 "    ) AA where rownum <= ?" +
//						 ") where rnum >= ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, hmap.get("endNo"));
//			pstmt.setInt(2, hmap.get("startNo"));
//			rs = pstmt.executeQuery();
//			list = new ArrayList<MvcboardVO>();
//			while (rs.next()) {
//				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//				MvcboardVO mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
//				mvcboardVO.setIdx(rs.getInt("idx"));
//				mvcboardVO.setName(rs.getString("name"));
//				mvcboardVO.setSubject(rs.getString("subject"));
//				mvcboardVO.setContent(rs.getString("content"));
//				mvcboardVO.setGup(rs.getInt("gup"));
//				mvcboardVO.setLev(rs.getInt("lev"));
//				mvcboardVO.setSeq(rs.getInt("seq"));
//				mvcboardVO.setHit(rs.getInt("hit"));
//				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
//				list.add(mvcboardVO);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
//		return list;
		
		
//		JdbcTemplate을 사용하는 경우 select sql 명령에는 "?"를 사용할 수 없다.
//		-> "?" 자리에 데이터가 저장된 변수를 직접 사용한다.
			String sql = "select * from (" +
						 "    select rownum rnum, AA.* from (" +
						 "        select * from mvcboard order by gup desc, seq asc" +
						 "    ) AA where rownum <= " + hmap.get("endNo") +
						 ") where rnum >= " + hmap.get("startNo");
		
//		query(실행할 sql 명령, new BeanPropertyRowMapper(VO 클래스.class))
//		select sql 명령 실행 결과를 BeanPropertyRowMapper 클래스 생성자의 인수인 MvcboardVO 클래스로
//		넘겨서 sql 명령 실행 결과를 저장시켜 리턴한다.
//		query() 메소드 실행 결과는 List 인터페이스 타입으로 리턴되기때문에 메소드의 리턴 타입인
//		ArrayList<MvcboardVO>로 형변환이 필요하다.
//		데이터베이스 테이블의 필드 이름과 BeanPropertyRowMapper 클래스의 생성자의 인수로 전달되는 클래스의 필드 이름이
//		같아야 정상적으로 처리된다.
			
		return (ArrayList<MvcboardVO>) template.query(sql, new BeanPropertyRowMapper(MvcboardVO.class));
	}


	public void increment(int idx) {
		logger.info("MvcboardDAO 클래스의 increment() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set hit = hit + 1 where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
//			String sql = "update mvcboard set hit = hit + 1 where idx = " + idx;
			String sql = String.format("update mvcboard set hit = hit + 1 where idx = %d", idx);
			template.update(sql);
		
		
	}

	public MvcboardVO selectByIdx(int idx) {
		logger.info("MvcboardDAO 클래스의 selectByIdx() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		MvcboardVO mvcboardVO = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:/applicationCTX.xml");
//				mvcboardVO = ctx.getBean("mvcboardVO", MvcboardVO.class);
//				mvcboardVO.setIdx(rs.getInt("idx"));
//				mvcboardVO.setName(rs.getString("name"));
//				mvcboardVO.setSubject(rs.getString("subject"));
//				mvcboardVO.setContent(rs.getString("content"));
//				mvcboardVO.setGup(rs.getInt("gup"));
//				mvcboardVO.setLev(rs.getInt("lev"));
//				mvcboardVO.setSeq(rs.getInt("seq"));
//				mvcboardVO.setHit(rs.getInt("hit"));
//				mvcboardVO.setWriteDate(rs.getTimestamp("writeDate"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
//		return mvcboardVO;
		
//			String sql = "select * from mvcboard where idx = " + idx;
			String sql = String.format("select * from mvcboard where idx = %d", idx);
			
			return (MvcboardVO) template.queryForObject(sql, new BeanPropertyRowMapper(MvcboardVO.class));
		
	}

	public void update(int idx, String subject, String content) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, subject);
//			pstmt.setString(2, content);
//			pstmt.setInt(3, idx);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			
//			template.update(sql, new PreparedStatementSetter() {
//				
//				@Override
//				public void setValues(PreparedStatement ps) throws SQLException {
//					ps.setString(1, subject);
//					ps.setString(2, content);
//					ps.setInt(3, idx);
//					
//				}
//			});
		
//			String sql = "update mvcboard set subject = '" + subject +
//														"', content = '" + content + "' where idx = " + idx;
			String sql = String.format(  "update mvcboard set subject = '%s', content = '%s' where idx = %d" , subject, content, idx);
			template.update(sql);
			
			
			
			
	}

	public void update(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcboardVO.getSubject());
//			pstmt.setString(2, mvcboardVO.getContent());
//			pstmt.setInt(3, mvcboardVO.getIdx());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
//		String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//		template.update(sql, new PreparedStatementSetter() {
//			
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setString(1, mvcboardVO.getSubject());
//				ps.setString(2, mvcboardVO.getContent());
//				ps.setInt(3, mvcboardVO.getIdx());
//				
//			}
//		});
		
		String sql = "update mvcboard set subject = '" + mvcboardVO.getSubject() +
		"', content = '" + mvcboardVO.getContent() + "' where idx = " + mvcboardVO.getIdx();
//		String sql = String.format(  "update mvcboard set subject = '%s', content = '%s' where idx = %d" ,
//											mvcboardVO.getSubject(), mvcboardVO.getContent(), mvcboardVO.getIdx());
		template.update(sql);
		
		
	}

	public void delete(int idx) {
		logger.info("MvcboardDAO 클래스의 delete() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "delete from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
		
//			String sql = "delete from mvcboard where idx =  ?";
//			template.update(sql, new PreparedStatementSetter() {
//				
//				@Override
//				public void setValues(PreparedStatement ps) throws SQLException {
//					ps.setInt(1, idx);
//				}
//			});
		
		
			String sql = "delete from mvcboard where idx =  " + idx;
			template.update(sql);
	}

	public void replyIncrement(HashMap<String, Integer> hmap) {
		logger.info("MvcboardDAO 클래스의 replyIncrement() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
//			String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
//			template.update(sql, new PreparedStatementSetter() {
//				
//				@Override
//				public void setValues(PreparedStatement ps) throws SQLException {
//					ps.setInt(1, hmap.get("gup"));
//					ps.setInt(2, hmap.get("seq"));
//					
//				}
//			});
		
//			String sql = "update mvcboard set seq = seq + 1 where gup = " + hmap.get("gup") + " and seq >= " + hmap.get("seq");
			String sql = String.format( "update mvcboard set seq = seq + 1 where gup = %d and seq >= %d", hmap.get("gup"), hmap.get("seq") );
			template.update(sql);
			
			
	}

	public void replyInsert(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 replyInsert() 메소드 실행");
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = dataSource.getConnection();
//			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//					"values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcboardVO.getName());
//			pstmt.setString(2, mvcboardVO.getSubject());
//			pstmt.setString(3, mvcboardVO.getContent());
//			pstmt.setInt(4, mvcboardVO.getGup());
//			pstmt.setInt(5, mvcboardVO.getLev());
//			pstmt.setInt(6, mvcboardVO.getSeq());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
//		}
		
//			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//					"values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
//			template.update(sql, new PreparedStatementSetter() {
//				
//				@Override
//				public void setValues(PreparedStatement ps) throws SQLException {
//					ps.setString(1, mvcboardVO.getName());
//					ps.setString(2, mvcboardVO.getSubject());
//					ps.setString(3, mvcboardVO.getContent());
//					ps.setInt(4, mvcboardVO.getGup());
//					ps.setInt(5, mvcboardVO.getLev());
//					ps.setInt(6, mvcboardVO.getSeq());
//					
//				}
//			});
		
//		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
//				"values (mvcboard_idx_seq.nextval, '" + mvcboardVO.getName() + "', '" +
//				mvcboardVO.getSubject() + "', '" + mvcboardVO.getContent() + "', " + 
//				mvcboardVO.getGup() + ", " + mvcboardVO.getLev() + ", " + mvcboardVO.getSeq()  + ")";
		
			String sql = String.format( "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
					"values (mvcboard_idx_seq.nextval, '%s', '%s', '%s', %d, %d, %d)",
							mvcboardVO.getName(), mvcboardVO.getSubject(), mvcboardVO.getContent(),
							mvcboardVO.getGup(), mvcboardVO.getLev(), mvcboardVO.getSeq());
			template.update(sql); 
	}
	
}













