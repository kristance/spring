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
	
	private JdbcTemplate template;
	
	public MvcboardDAO() {
		template = Constant.template;
	}
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
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mvcboardVO.getName());
				ps.setString(2, mvcboardVO.getSubject());
				ps.setString(3, mvcboardVO.getContent());
				
			}
		});
	}

	public int selectCount() {
		logger.info("MvcboardDAO 클래스의 selectCount() 메소드 실행");
		
		String sql = "select count(*) as cnt from mvcboard";
		
		return template.queryForObject(sql, Integer.class);
	}

	public ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap) {
			String sql = "select * from (" +
						 "    select rownum rnum, AA.* from (" +
						 "        select * from mvcboard order by gup desc, seq asc" +
						 "    ) AA where rownum <= " + hmap.get("endNo") +
						 ") where rnum >= " + hmap.get("startNo");
		
			
		return (ArrayList<MvcboardVO>) template.query(sql, new BeanPropertyRowMapper(MvcboardVO.class));
	}


	public void increment(int idx) {
		logger.info("MvcboardDAO 클래스의 increment() 메소드 실행");
			String sql = String.format("update mvcboard set hit = hit + 1 where idx = %d", idx);
			template.update(sql);
		
		
	}

	public MvcboardVO selectByIdx(int idx) {
		logger.info("MvcboardDAO 클래스의 selectByIdx() 메소드 실행");
			String sql = String.format("select * from mvcboard where idx = %d", idx);
			
			return (MvcboardVO) template.queryForObject(sql, new BeanPropertyRowMapper(MvcboardVO.class));
		
	}

	public void update(int idx, String subject, String content) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
			String sql = String.format(  "update mvcboard set subject = '%s', content = '%s' where idx = %d" , subject, content, idx);
			template.update(sql);
			
			
			
			
	}

	public void update(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 update() 메소드 실행");
		
		String sql = "update mvcboard set subject = '" + mvcboardVO.getSubject() +
		"', content = '" + mvcboardVO.getContent() + "' where idx = " + mvcboardVO.getIdx();
		template.update(sql);
		
		
	}

	public void delete(int idx) {
		logger.info("MvcboardDAO 클래스의 delete() 메소드 실행");
		
		
		
		
			String sql = "delete from mvcboard where idx =  " + idx;
			template.update(sql);
	}

	public void replyIncrement(HashMap<String, Integer> hmap) {
		logger.info("MvcboardDAO 클래스의 replyIncrement() 메소드 실행");
			String sql = String.format( "update mvcboard set seq = seq + 1 where gup = %d and seq >= %d", hmap.get("gup"), hmap.get("seq") );
			template.update(sql);
			
			
	}

	public void replyInsert(MvcboardVO mvcboardVO) {
		logger.info("MvcboardDAO 클래스의 replyInsert() 메소드 실행");
			String sql = String.format( "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " +
					"values (mvcboard_idx_seq.nextval, '%s', '%s', '%s', %d, %d, %d)",
							mvcboardVO.getName(), mvcboardVO.getSubject(), mvcboardVO.getContent(),
							mvcboardVO.getGup(), mvcboardVO.getLev(), mvcboardVO.getSeq());
			template.update(sql); 
	}
	
}













