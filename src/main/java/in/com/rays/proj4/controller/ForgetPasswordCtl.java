package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.RecordNotFoundException;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Forgot Password functionality.
 * <p>
 * This servlet is mapped to <b>/ForgetPasswordCtl</b> and is responsible
 * for validating user email input and triggering the password recovery process.
 * </p>
 *
 * <p>
 * It interacts with the UserModel to send the password to the registered
 * email address if the user exists in the system.
 * </p>
 *
 * <p>
 * It supports retrieving password via email and displays appropriate
 * success or error messages based on the operation result.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

    /**
     * Validates the login (email) input.
     * <p>
     * Ensures that the email field is not empty and follows valid email format.
     * </p>
     *
     * @param request HttpServletRequest containing user input
     * @return true if input is valid, false otherwise
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates UserBean with login (email) from request.
     *
     * @param request HttpServletRequest containing form data
     * @return populated UserBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        UserBean bean = new UserBean();

        bean.setLogin(DataUtility.getString(request.getParameter("login")));

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * <p>
     * Simply forwards the request to the Forget Password view page.
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
     * Handles HTTP POST request for password recovery.
     * <p>
     * If operation is "GO":
     * </p>
     * <ul>
     *   <li>Sends password to the registered email if user exists</li>
     *   <li>Shows error if email is not found</li>
     *   <li>Handles application errors (e.g., network issues)</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing user input
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @author Anmol Kumar Baliyan
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        UserBean bean = (UserBean) populateBean(request);

        UserModel model = new UserModel();

        if (OP_GO.equalsIgnoreCase(op)) {

            try {

                boolean flag = model.forgetPassword(bean.getLogin());

                if (flag) {
                    ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
                }

            } catch (RecordNotFoundException e) {

                ServletUtility.setErrorMessage(e.getMessage(), request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
            }

            ServletUtility.forward(getView(), request, response);
        }
    }

    /**
     * Returns the view page for Forgot Password.
     *
     * @return path of ForgetPasswordView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.FORGET_PASSWORD_VIEW;
    }
}