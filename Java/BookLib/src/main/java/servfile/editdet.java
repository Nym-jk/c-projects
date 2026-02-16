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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.Session;

import classFile.objClass;

/**
 * Servlet implementation class editdet
 */
@WebServlet("/editdet")
public class editdet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Runnable r = () -> {
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
		
		String uname = request.getParameter("username");
		String psw = request.getParameter("userpassword");
		
		HttpSession ses = request.getSession(false);
		String user = "",pass="";
		
        user = (String) ses.getAttribute("name"); 
        pass = (String) ses.getAttribute("pwd");
        
        try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("update users set username=? where username=? and password=?");
			st.setString(1, uname);
			st.setString(2, user);
			st.setString(3, pass);
			
			
			PreparedStatement st1 = con.prepareStatement("update users set password=? where username=? and password=?");
			st1.setString(1, psw);
			st1.setString(2, user);
			st1.setString(3, pass);
			
			int rs= st.executeUpdate();
			int rs1= st.executeUpdate();
			ses.setAttribute("name",uname);
			ses.setAttribute("pwd",psw);
			
			response.setContentType("text/html");
			r.run();
			RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
			rd.forward(request, response);
			
			con.close();			
			}catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
	
	}

}
