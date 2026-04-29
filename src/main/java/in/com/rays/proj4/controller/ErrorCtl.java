package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling application error page.
 * <p>
 * This servlet is mapped to <b>/ErrorCtl</b> and is responsible
 * for forwarding requests to the error view page.
 * </p>
 *
 * <p>
 * It acts as a centralized error handler in the application,
 * displaying error messages when exceptions occur or when
 * explicitly redirected by other components.
 * </p>
 *
 * <p>
 * Both GET and POST requests are forwarded to the same error view.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet("/ErrorCtl")
public class ErrorCtl extends BaseCtl {

    /**
     * Handles HTTP GET request.
     * <p>
     * Forwards the request to the error view page.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request.
     * <p>
     * Forwards the request to the error view page.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for error handling.
     *
     * @return path of ErrorView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.ERROR_VIEW;
    }
}