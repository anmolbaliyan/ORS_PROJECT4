package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.ServletUtility;

/**
 * 
 * Controller class for handling user registration operations.
 * 
 * <p>
 * 
 * This servlet is mapped to the URL pattern <b>/UserRegistrationCtl</b>
 * 
 * and is responsible for processing user registration requests.
 * 
 * It interacts with the view (JSP) and model layers to create
 * 
 * new user records in the system.
 * 
 * </p>
 *
 * 
 * 
 * <p>
 * 
 * This class extends {@code BaseCtl}, inheriting common controller
 * 
 * functionalities such as request handling, validation, and navigation.
 * 
 * </p>
 *
 * 
 * 
 * @author Anmol Kumar Baliyan
 * 
 * 
 * 
 */
@WebServlet("/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {

	public static final String OP_SIGN_UP = "Sign Up";

	/**
	 * 
	 * Validates user input data received from the registration form.
	 * 
	 * <p>
	 * 
	 * This method checks all required fields such as first name, last name,
	 * 
	 * login (email), password, confirm password, gender, date of birth,
	 * 
	 * and mobile number. It also validates the format and constraints
	 * 
	 * (e.g., email format, password strength, mobile number length).
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * If any validation fails, appropriate error messages are set as
	 * 
	 * request attributes and the method returns {@code false}.
	 * 
	 * If all validations pass, it returns {@code true}.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest object containing user input data
	 * 
	 * @return {@code true} if all inputs are valid, {@code false} otherwise
	 *
	 * 
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "firstName is required");
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "lastName is required");
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", "login is required");
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", "Invalid login id");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", "password is required");
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "confirmPassword is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", "gender is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", "dob is required");
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", "Invalid date of birth");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "mobileNo is required");
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		return pass;
	}

	/**
	 * 
	 * Populates a UserBean with request parameters received from the registration
	 * form.
	 * 
	 * <p>
	 * 
	 * This method extracts form data such as first name, last name, login,
	 * 
	 * password, confirm password, gender, date of birth, and mobile number
	 * 
	 * from the request and sets them into a UserBean object.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It also sets the default role as STUDENT and calls populateDTO()
	 * 
	 * to fill common attributes.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest object containing user input data
	 * 
	 * @return populated UserBean object
	 *
	 * 
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(RoleBean.STUDENT);

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * 
	 * Handles HTTP GET requests for the User Registration page.
	 * 
	 * <p>
	 * 
	 * This method forwards the request to the registration view (JSP page)
	 * 
	 * where the user can fill in registration details.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest object containing the client request
	 * 
	 * @param response HttpServletResponse object used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input or output error occurs
	 *
	 * 
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in UserRegistrationCtl doGet method");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * 
	 * Handles HTTP POST requests for user registration operations.
	 * 
	 * <p>
	 * 
	 * This method processes form submission based on the operation:
	 * 
	 * </p>
	 * 
	 * <ul>
	 * 
	 * <li><b>OP_SIGN_UP</b>: Registers a new user by populating the bean
	 * 
	 * and saving it using the UserModel.</li>
	 * 
	 * <li><b>OP_RESET</b>: Redirects the user to reset the registration form.</li>
	 * 
	 * </ul>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It also handles exceptions such as duplicate login ID and application errors,
	 * 
	 * and sets appropriate success or error messages in the request.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest object containing form data
	 * 
	 * @param response HttpServletResponse object used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input or output error occurs
	 *
	 * 
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in UserRegistrationCtl doPost method");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Registration successful!", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
			ServletUtility.forward(getView(), request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

	}

	/**
	 * 
	 * Returns the view page for the User Registration module.
	 * 
	 * <p>
	 * 
	 * This method provides the path of the registration JSP page
	 * 
	 * defined in ORSView.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the path of the user registration view page
	 *
	 * 
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}