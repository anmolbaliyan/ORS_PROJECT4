package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.util.ServletUtility;

/**
 * 
 * Controller class for handling welcome page requests.
 * 
 * <p>
 * 
 * This servlet is mapped to the URL pattern <b>/WelcomeCtl</b>
 * 
 * and is responsible for forwarding the user to the welcome view.
 * 
 * It acts as the entry point of the application after login or initial access.
 * 
 * </p>
 *
 * 
 * 
 * <p>
 * 
 * This class extends {@code BaseCtl}, utilizing common controller
 * 
 * functionalities such as request handling and view navigation.
 * 
 * </p>
 *
 * 
 * 
 * @author Anmol Kumar Baliyan
 * 
 */

@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

	/**
	 * 
	 * Handles HTTP GET requests for the Welcome Controller.
	 * 
	 * <p>
	 * 
	 * This method forwards the request to the corresponding view page.
	 * 
	 * It is typically invoked when the user accesses the welcome URL.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest object that contains the client request
	 * 
	 * @param response HttpServletResponse object that contains the response to be
	 *                 sent
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input or output error occurs
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in welcomeCtl doGet method");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * 
	 * Returns the view page associated with this controller.
	 * 
	 * <p>
	 * 
	 * This method provides the path of the welcome view (JSP page)
	 * 
	 * which will be used to forward the request for display.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the path of the welcome view page defined in ORSView
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}

}
