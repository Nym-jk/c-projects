package servfile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import classFile.objClass;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String bk = request.getParameter("bkname");
		String topage=(String) request.getParameter("val");
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("select bname,coverloc,bloc from books where bname=?;");
			st.setString(1, bk);
			
			ResultSet rs= st.executeQuery();
			ArrayList<objClass> ar = new ArrayList<>();
			
			while(rs.next()){
				
				String name = rs.getString("bname"),cloc = rs.getString("coverloc"),bloc=rs.getString("bloc");
				
				ar.add(new objClass(name,cloc,bloc));
			}
		
			
			request.setAttribute("searches", ar);
			request.setAttribute("issearch", true);
			
			
			if(topage.equals("home")) {
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}else if(topage.equals("uprof")){
				RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
				rd.forward(request, response);
			}else if(topage.equals("admin")){
				RequestDispatcher rd = request.getRequestDispatcher("AdminDash.jsp");
				rd.forward(request, response);
			}
			
			}catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
	}

}
