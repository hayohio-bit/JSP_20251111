package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.BoardVO;

import util.DBManager;

public class BoardDAO {

	private static BoardDAO instance = new BoardDAO();
	
	private BoardDAO() {}
	                           
	public static BoardDAO getInstance() {
		return instance;
	}

	
	
	
	public List<BoardVO> selectAllBoards() {
		
		// DB 연결 객체를 선언 (초기값 null)
		Connection con = null;
		
		// SQL 실행을 위한 PreparedStatement 선언
		PreparedStatement pstmt = null;
		
		// select 결과를 저장할 ResultSet 선언
		ResultSet rs = null;
		
		// 실행할 SQL문 : 게시판 전체글을 번호 기준 내림차순 정렬
		String sql = "select * from board order by num desc";
		
		// 조회 결과를 저장할 리스트 생성
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			
			// DB 연결 생성
			con = DBManager.getConnection();
			
			// SQL 실행 준비
			pstmt = con.prepareStatement(sql);
			
			// SQL 실행하여 결과 가져오기
			rs = pstmt.executeQuery();
			
			// 결과 집합(rs)에 데이터가 있을 동안 반복
			while(rs.next()) {
				
				// 한 행(row)의 데이터를 저장할 BoardVO 객체 생성
				BoardVO vo = new BoardVO();
				
				// 글번호(num) 조회
				int num = rs.getInt("num");
				
				// 작성자(name) 조회
				String name = rs.getString("name");
				/* 참고 : 나머지 컬럼 조회 예시
				rs.getInt("num");		// 3
				rs.getString("name");	// 전수빈
				rs.getString("pass");	// 3333
				rs.getString("email");	// raccon@nate.com
				rs.getString("title");	// 고등애
				rs.getString("content");// 일식입니다.
				rs.getInt("readcount");	// 0
				rs.getTimestamp("writedate");	// 2025-11-19 10:13:08
				*/
				
		        // VO 객체에 값 저장
		        vo.setNum(num);
		        vo.setName(name);
		        vo.setPass(rs.getString("pass"));
		        vo.setEmail(rs.getString("email"));
		        vo.setTitle(rs.getString("title"));
		        vo.setContent(rs.getString("content"));
		        vo.setReadcount(rs.getInt("readcount"));
		        vo.setWritedate(rs.getTimestamp("writedate"));
				
		        // 완성된 VO를 리스트에 추가
				list.add(vo);
			}
			
		}catch(Exception e) {
			// SQL 또는 DB 연결 중 오류 발생 시 출력
			e.printStackTrace();
		}finally {
			// 사용한 자원(con, pstmt, rs) 정리(담기)
			DBManager.close(con, pstmt, rs);
		}
		// 최종적으로 게시글 목록 반환
		return list;
	} // end selectAllBoards

	
	
	
	public void insertBoard(BoardVO vo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into board(name, pass, email, title, content)"
				+ " values(?, ?, ?, ?, ? )";
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getTitle());
			pstmt.setString(5, vo.getContent());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt);
		}
	} // end insertBoard

	public BoardVO selectOneByNum(int num) {
		
		BoardVO vo = new BoardVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board where num = ?";
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setPass(rs.getString("pass"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setReadcount(rs.getInt("readcount"));
				vo.setWritedate(rs.getTimestamp("writedate"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);	
		}return vo;
	} // end selectOneByNum

	public void updateReadCount(int num) {
		
		String sql = "update board set readcount = readcount+1 where num = ?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt);
		}
	} // end updateReadCount

	public void deleteBoard(int num) {
		
		String sql = "delete from board where num = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt);
		}
	} // end deleteBoard
	
	public Timestamp getWritedate() {
	    return Timestamp.from(Instant.now());
	}


	public void updateBoard(BoardVO vo) {
		
		String sql = "UPDATE board set name=?, pass=?, email=?, title=?, content=?, writedate=? WHERE num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
			
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getTitle());
			pstmt.setString(5, vo.getContent());
			pstmt.setTimestamp(6, getWritedate());
			pstmt.setInt(7, vo.getNum());
						
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt);
		}
		
	}
}
	
