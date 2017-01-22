package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storage.Database;
import storage.Storage;

@SuppressWarnings("serial")
public class DisplayPicture extends HttpServlet{
	//This servlet is to get the images in the storage
	//And give the user the right to select points on the image. 
	protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) {
		//Input = the coordinates of points
		//Output = the OCR text in XML
	}
	protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();
        out.println("<html><title>HeavyWater OCR Text</title><body>");
		String OCRPage = request.getParameter("page");
		if(OCRPage == null || OCRPage.equals("0")) {
			//Get the image/image list			
			ArrayList<String> imageList = Database.getImageRealPathList();
	        out.println("<h1>This is the list of Pictures</h1></br>");
	        out.println("<link rel=\"stylesheet\" href=\"styles/styles.css\" type=\"text/css\" media=\"screen\" />");
			out.println("<body background=\"styles/background.jpg\">");
	        if(imageList != null && !imageList.isEmpty()) {
	        	int i = 1;
	        	for(String s: imageList) {
	            	out.println("<a href='"+s+"'>" + s.substring(s.lastIndexOf('/') + 1) + "</a>");
	            	out.println("<span style='width:50px;'></span>");
	            	out.println("<form action='OcrResult.jsp' method='get'>");
	            	out.println("<input type='submit' value='OCR Result'/>");
	            	out.println("<input type='hidden' name='page' value='" + Integer.toString(i) + "'/>");
	            	out.println("<input type='hidden' name='valX' value='-1'/>");
	            	out.println("<input type='hidden' name='valY' value='-1'/></form>");
	            	i++;
	            }
	        }
	        else {
	        	out.println("<h2>No files uploaded!</h2></br>");
	        }
	        out.println("</br><a href='homepage'>Back</a>");
	        
		}
		out.println("</body></html>");
		out.flush();
		out.close();
		
		
	}
}
