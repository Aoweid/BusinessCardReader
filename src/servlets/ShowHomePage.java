package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowHomePage extends HttpServlet{
	
	protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
		/**TODO: Get the index.html*/
		response.sendRedirect("index.html");
	}
}
