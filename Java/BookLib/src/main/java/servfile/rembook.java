package servfile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import classFile.objClass;

/**
 * Servlet implementation class rembook
 */
@WebServlet("/rembook")
public class rembook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rembook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bname = (String)request.getParameter("removebookname");
		
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
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("delete from books where bname=?;");
			st.setString(1, bname);
			int rs= st.executeUpdate();
			
			r.run();
			
			RequestDispatcher rd = request.getRequestDispatcher("AdminDash.jsp");
			rd.forward(request, response);
			
			con.close();			
			}catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
	}

}
