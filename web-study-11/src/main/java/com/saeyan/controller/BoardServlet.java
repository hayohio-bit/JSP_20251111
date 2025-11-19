package com.saeyan.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.controller.action.Action;

@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardServlet() {
	}

	// localhost:8080/web-study-10?command=board_list
	// 누군가 웹사이트에서 GET 방식으로 요청하면 아래 내용을 처리
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 누군가 주소창에 '?command=board_list'처럼 명령(command)을 보내면, 그걸 받아와서 변수에 넣음
		String command = request.getParameter("command");	//board_insert
		
		System.out.println("BoardServlet에서 요청을 받음을 확인" + command);
		
		// ActionFactory에서 af를 꺼내옴
		ActionFactory af = ActionFactory.getInstance();
		
		// ActionFactory한테 command를 처리할 수 있는 action을 달라고 해서 받아옴
		Action action = af.getAction(command);
		
		// action이 있다면 action에게 명령을 처리하라고 시킴
		if(action != null) {
			action.execute(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	
			throws ServletException, IOException {
		// 누군가 웹사이트에서 POST방식(주로 폼 제출)에 요청하면,
		// 글자 깨짐을 막고 위에서 만든 doGet 방식과 같이 처리함
		request.setCharacterEncoding("utf-8");
		doGet(request,response);
	}

}
