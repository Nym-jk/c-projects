package servfile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
 * Servlet implementation class savefile
 */
@WebServlet("/savefile")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, // 1 MB 
maxFileSize = 1024 * 1024 * 10, // 10 MB 
maxRequestSize = 1024 * 1024 * 50 // 50 MB 
)
public class savefile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public savefile() {
        super();
        // TODO Auto-generated constructor stub
    }

	
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
			}
			catch(Exception e) {
				System.out.println("Exception happened "+e);
				}
		
		}; 
		
		//insert details into database
		Runnable ins = () -> {
			
		
		}; 
		
		// Get the uploaded file part 
		
		Part filePart = request.getPart("file"); 
		String fileName = filePart.getSubmittedFileName();
		
		String uploadPath = "D:/uploads"; //required space
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) uploadDir.mkdir();
		filePart.write(uploadPath + File.separator + fileName);
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/epost","root","0987");
			PreparedStatement st = con.prepareStatement("insert into books values(?,?,?);");
			st.setString(1,fileName);
			st.setString(2, uploadPath+"one.png");
			st.setString(3, uploadPath+fileName);
			int rs= st.executeUpdate();
			}
		catch(Exception e) {
			System.out.println("Exception happened "+e);
			}
		r.run();
		RequestDispatcher rd = request.getRequestDispatcher("AdminDash.jsp");
		rd.forward(request, response);
	}

}
