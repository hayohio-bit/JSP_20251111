package unit01;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		out.println("<h1>Hello World!</h1>charset=utf-8 문자 인코딩 방식 미입력시 한글 깨짐<br>");
		out.println(name + "님 안녕하세요<br>");
		out.println("나이는 : "+age+"세입니다.<br>");
		
		out.println("</body>");
		out.println("</html>");

		out.close();	// 자원 사용 종료시 close
		
	}
}