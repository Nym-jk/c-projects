package servfile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uname = request.getParameter("username");
		String psw = request.getParameter("password");	
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("insert into users(username,password) values(?,?);");
			st.setString(1, uname);
			st.setString(2, psw);
			
			int rs= st.executeUpdate();
			
			con.close();
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.write("New USER NAME ADDED TO RECORDS, NOW LOGIN WITH YOUR CREDENTIALS ");
			RequestDispatcher rd = request.getRequestDispatcher("LoginPage.jsp");
			rd.include(request, response);
		}
		
		catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
		
	}

}
