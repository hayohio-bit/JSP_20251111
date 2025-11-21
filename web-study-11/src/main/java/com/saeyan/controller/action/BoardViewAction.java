package com.saeyan.controller.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
	// 1. num 가져오기
	int num = Integer.parseInt(request.getParameter("num"));
	
	// 2. DB 에서 num 해당하는 값 가져오기
	BoardDAO dao = BoardDAO.getInstance();
	BoardVO vo = dao.selectOneByNum(num);
	
	// 3. 조회수 증가
	dao.updateReadCount(num);
	
	// 4. boardView 값 전달하기	
	request.setAttribute("board", vo);
	
	String url = "board/boardView.jsp";
	
	//RequestDispatcher dis = request.getRequestDispatcher(url);
	//dis.forward(request, response);
	
	request.getRequestDispatcher(url)
		.forward(request, response);
	
	}

}
