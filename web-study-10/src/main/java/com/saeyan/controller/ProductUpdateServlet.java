package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.saeyan.dao.ProductDAO;
import com.saeyan.dto.ProductVO;
@WebServlet("/productUpdate.do")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ProductUpdateServlet() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		// 1. code 값 가져오기
		String code = request.getParameter("code");
		
		// 2. ProductDAO 통해서 code값 전체 가져오기
		ProductDAO pdao = ProductDAO.getInstance();
		
		ProductVO vo = pdao.selectProductByCode(code);
		
		// 3. request.setAttribute
		request.setAttribute("product", vo);
		
		// 4. forward(productUpdate.jsp) 이동
		request.getRequestDispatcher("product/productUpdate.jsp")
			.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		ServletContext context = getServletContext();
		System.out.println("context : " + context);
		
		String path = context.getRealPath("upload");
		System.out.println("path : "+ path);
		
		String encType = "utf-8";
		
		int sizeLimit = 20*1024*1024;	// 파일용량 크기 20MB
	
		MultipartRequest multi =
				new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
		
		int code = Integer.parseInt(multi.getParameter("code"));
		String name = multi.getParameter("name");
		String priceStr = multi.getParameter("price");
		String description = multi.getParameter("description");
		
		
		if (priceStr == null || priceStr.trim().isEmpty()) {
		    // 가격값이 없으면 에러 처리 (에러 페이지나 메시지)
		    // 예시: response.sendRedirect("product/productWrite.jsp?error=price");
		    return;
		}

		// 쉼표 모두 제거 후 변환
		priceStr = priceStr.replaceAll(",", "").trim();
		int price = 0;
		try {
		    price = Integer.parseInt(priceStr);     // 숫자로 변환 (예외 발생 가능)
		} catch (NumberFormatException e) {
		    // 잘못된 숫자일 경우 에러 처리
		    // 예시: response.sendRedirect("product/productWrite.jsp?error=priceformat");
		    return;
		}
		
		
		
		
		
	    
		// 파일 업로드		------- getFilesystemName
		String pictureUrl = multi.getFilesystemName("pictureUrl");
		
		if(pictureUrl == null) {
			pictureUrl = request.getParameter("nonmakeImg");
		}

//		String originName = multi.getOriginalFileName("description");
//		System.out.println("originName : " + originName );
	
		ProductVO vo =new ProductVO();
		vo.setCode(code);
		vo.setName(name);
		vo.setPrice(price);
		vo.setDescription(description);
		vo.setPictureUrl(pictureUrl);
		
		ProductDAO pdao = ProductDAO.getInstance();
		
		// ProductDAO 클래스 insertProduct호출
		pdao.updateProduct(vo);
		
		// post -> redirect -> get (PRG패턴)
		response.sendRedirect("productList.do");
	}

}
