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
 * Servlet implementation class toprofile
 */
@WebServlet("/toprofile")
public class toprofile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public toprofile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String topage=request.getParameter("val");
		
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
			}
			catch(Exception e) {
				System.out.println("Exception happened "+e);
				}
		
		}; 
		
		r.run();
		
		if(topage.equals("profile")) {
			RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
			rd.forward(request, response);
		}else if(topage.equals("home")) {
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
		}else {
			System.out.println("Exception happened in Changing profile");
		}
		
	}

}
