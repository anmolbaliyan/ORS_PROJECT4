package in.com.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.RoleModel;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * 
 * Controller class for handling user management operations such as
 * 
 * adding, updating, and viewing user details.
 * 
 * <p>
 * 
 * This servlet is mapped to <b>/ctl/UserCtl</b> and is responsible for
 * 
 * processing user form data, performing validation, and interacting
 * 
 * with the UserModel to persist data.
 * 
 * </p>
 *
 * 
 * 
 * <p>
 * 
 * It supports operations like Save, Update, Reset, and Cancel,
 * 
 * and follows the MVC architecture by coordinating between
 * 
 * view (JSP) and model layers.
 * 
 * </p>
 *
 * 
 * 
 * @author Anmol Kumar Baliyan
 * 
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	/**
	 * 
	 * Preloads data required for the user form.
	 * 
	 * <p>
	 * 
	 * This method retrieves the list of roles from the RoleModel and
	 * 
	 * sets it into the request scope to populate role dropdown
	 * 
	 * in the user view.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest object to store preload data
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel roleModel = new RoleModel();
		try {
			List<RoleBean> roleList = roleModel.list();
			System.out.println("roleList size ==> " + roleList.size());
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Validates user input data received from the request.
	 * 
	 * <p>
	 * 
	 * This method checks all mandatory fields such as first name,
	 * 
	 * last name, login, password, confirm password, gender,
	 * 
	 * date of birth, role, and mobile number.
	 * 
	 * It also validates format constraints like email format,
	 * 
	 * password strength, and phone number format.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * If validation fails, error messages are set in request attributes
	 * 
	 * and the method returns false.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest containing input parameters
	 * 
	 * @return true if all inputs are valid, false otherwise
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
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
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		return pass;
	}

	/**
	 * 
	 * Populates a UserBean with request parameters.
	 * 
	 * <p>
	 * 
	 * This method extracts form input values such as id, first name,
	 * 
	 * last name, login, password, confirm password, gender, date of birth,
	 * 
	 * mobile number, and roleId, and sets them into a UserBean object.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It also calls populateDTO() to set common attributes.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request HttpServletRequest containing user input data
	 * 
	 * @return populated UserBean object
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * 
	 * Handles HTTP GET requests to display user details.
	 * 
	 * <p>
	 * 
	 * If a user ID is provided, it retrieves the user record
	 * 
	 * from the database and sets it in the request for display.
	 * 
	 * Otherwise, it simply forwards to the user form view.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest containing client request
	 * 
	 * @param response HttpServletResponse used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input/output error occurs
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();

		if (id > 0) {
			try {
				UserBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * 
	 * Handles HTTP POST requests for user operations.
	 * 
	 * <p>
	 * 
	 * This method processes different operations based on the request:
	 * 
	 * </p>
	 * 
	 * <ul>
	 * 
	 * <li><b>Save</b>: Adds a new user</li>
	 * 
	 * <li><b>Update</b>: Updates existing user details</li>
	 * 
	 * <li><b>Cancel</b>: Redirects to user list page</li>
	 * 
	 * <li><b>Reset</b>: Reloads the user form</li>
	 * 
	 * </ul>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * It also handles duplicate record exceptions and sets appropriate
	 * 
	 * success or error messages in the request.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @param request  HttpServletRequest containing form data
	 * 
	 * @param response HttpServletResponse used to send response
	 * 
	 * @throws ServletException if a servlet-specific error occurs
	 * 
	 * @throws IOException      if an input/output error occurs
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * 
	 * Returns the view page for user operations.
	 * 
	 * <p>
	 * 
	 * This method provides the path of the user JSP page
	 * 
	 * defined in ORSView.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the path of the user view page
	 * 
	 * @author Anmol Kumar Baliyan
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}
}
