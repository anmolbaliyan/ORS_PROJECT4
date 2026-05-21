package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Login operations such as authentication,
 * logout, and navigation to registration.
 * <p>
 * This servlet is mapped to <b>/LoginCtl</b> and is responsible for validating
 * login credentials, authenticating users, managing session, and redirecting to
 * appropriate views.
 * </p>
 *
 * <p>
 * It supports operations like Sign In, Sign Up, and Logout, and follows MVC
 * architecture.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(LoginCtl.class);

	/** Operation constants */
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";
	public static final String OP_LOG_OUT = "Logout";

	/**
	 * Validates login input data.
	 * <p>
	 * Validates login ID (email format) and password. Skips validation for Sign Up
	 * and Logout operations.
	 * </p>
	 *
	 * @param request HttpServletRequest containing input parameters
	 * @return true if valid, false otherwise
	 * @author Anmol Kumar Baliyan
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl validate() called");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		log.debug("Validation completed. Status = " + pass);

		return pass;
	}

	/**
	 * Populates UserBean with login credentials.
	 *
	 * @param request HttpServletRequest containing input data
	 * @return populated UserBean object
	 * @author Anmol Kumar Baliyan
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("LoginCtl populateBean() called");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		return bean;
	}

	/**
	 * Handles HTTP GET request.
	 * <p>
	 * Used for logout operation. Invalidates session and displays success message.
	 * </p>
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @author Anmol Kumar Baliyan
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("LoginCtl doGet() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op)) {

			log.info("Logout operation invoked");

			HttpSession session = request.getSession();

			session.invalidate();

			ServletUtility.setSuccessMessage("Logout Successful!", request);

			log.info("User session invalidated successfully");
		}

		ServletUtility.forward(getView(), request, response);

		log.info("doGet() forwarded to view : " + getView());
	}

	/**
	 * Handles HTTP POST request for login operations.
	 * <p>
	 * Supports:
	 * </p>
	 * <ul>
	 * <li><b>Sign In</b>: Authenticates user and creates session</li>
	 * <li><b>Sign Up</b>: Redirects to registration page</li>
	 * </ul>
	 *
	 * @param request  HttpServletRequest containing form data
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @author Anmol Kumar Baliyan
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("LoginCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		log.info("Operation = " + op);

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();
		HttpSession session = request.getSession();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {

				log.info("Authenticating user : " + bean.getLogin());

				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {

					log.info("Authentication successful for user : " + bean.getLogin());

					session.setAttribute("user", bean);

					RoleBean rolebean = role.findByPk(bean.getRoleId());

					if (rolebean != null) {

						session.setAttribute("role", rolebean.getName());

						log.info("Role assigned : " + rolebean.getName());
					}

					log.info("Redirecting to Welcome Controller");

					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;

				} else {

					log.warn("Invalid Login Id and Password");

					ServletUtility.setBean(bean, request);

					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {

				log.error("ApplicationException in LoginCtl doPost()", e);

				e.printStackTrace();
				return;
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			log.info("Sign Up operation invoked");

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);

			log.info("Redirected to User Registration Controller");

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.info("doPost() forwarded to view : " + getView());
	}

	/**
	 * Returns the view page for Login.
	 *
	 * @return path of LoginView JSP
	 * @author Anmol Kumar Baliyan
	 */
	@Override
	protected String getView() {

		log.debug("Returning Login view page");

		return ORSView.LOGIN_VIEW;
	}
}
