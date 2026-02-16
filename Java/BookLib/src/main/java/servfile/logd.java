package servfile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import classFile.objClass;

@WebServlet("/logd")
public class logd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname =(String)request.getParameter("username");
		String psw = request.getParameter("password");
		boolean isValidUser = false;
		
		Runnable r = () ->{
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
				PreparedStatement st = con.prepareStatement("select bname,coverloc,bloc from books;");
				
				ResultSet rs= st.executeQuery();
				
				ArrayList<objClass> ar = new ArrayList<>();
				
				while(rs.next()){
					
					String name = rs.getString("bname"),cloc = rs.getString("coverloc"),bloc=rs.getString("bloc");
					
					ar.add(new objClass(name,cloc,bloc));
				}
			
				request.setAttribute("contents", ar);
				request.setAttribute("issearch", false);
				
				}catch(Exception e) {
				System.out.println("Exception happened "+e);
				}
		};
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("select * from users where username=? and password=?");
			st.setString(1, uname);
			st.setString(2, psw);
			
			ResultSet rs= st.executeQuery();
			
			if(rs.next()) {
				isValidUser= true;
			}
			con.close();			
			
			}catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
		
		if(isValidUser) {
			HttpSession ses = request.getSession();
			ses.setAttribute("name",uname);
			ses.setAttribute("pwd",psw);
			
			r.run(); 
			
			if(uname.equals("admin")) {
			RequestDispatcher rdadmin = request.getRequestDispatcher("AdminDash.jsp"); 
			rdadmin.forward(request, response); 
			}else {
			RequestDispatcher rdhome = request.getRequestDispatcher("home.jsp"); 
			rdhome.forward(request, response); 
			}
		}else {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.write("THE USER NAME OR PASSWORD IS INCORRECT");
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.jsp");
			rd.include(request, response);
		}
		
		
	}

}
