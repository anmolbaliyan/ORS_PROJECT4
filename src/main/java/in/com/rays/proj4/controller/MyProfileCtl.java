package in.com.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.com.rays.proj4.bean.BaseBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.UserModel;
import in.com.rays.proj4.util.DataUtility;
import in.com.rays.proj4.util.DataValidator;
import in.com.rays.proj4.util.PropertyReader;
import in.com.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling My Profile operations such as
 * viewing and updating logged-in user details.
 * <p>
 * This servlet is mapped to <b>/ctl/MyProfileCtl</b> and is responsible
 * for managing user profile information including personal details
 * and navigation to change password functionality.
 * </p>
 *
 * <p>
 * It supports operations like Save (update profile) and
 * Change Password, following MVC architecture.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {

    /** Operation constant for changing password */
    public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

    /**
     * Validates user profile input data.
     * <p>
     * This method validates fields such as first name, last name,
     * gender, mobile number, and date of birth.
     * Validation is skipped when the operation is Change Password.
     * </p>
     *
     * @param request HttpServletRequest containing input parameters
     * @return true if validation passes, false otherwise
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
            return pass;
        }

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

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
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

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a UserBean with request parameters.
     * <p>
     * This method extracts user profile details from request
     * and sets them into a UserBean object.
     * </p>
     *
     * @param request HttpServletRequest containing input data
     * @return populated UserBean object
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        UserBean bean = new UserBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET request to display logged-in user's profile.
     * <p>
     * Fetches the current user from session and retrieves
     * updated details from database.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        UserModel model = new UserModel();

        if (id > 0) {
            try {
                UserBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request for profile operations.
     * <p>
     * Supports:
     * </p>
     * <ul>
     *   <li><b>Save</b>: Updates user profile</li>
     *   <li><b>Change Password</b>: Redirects to change password page</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            UserBean bean = (UserBean) populateBean(request);

            try {
                if (id > 0) {
                    user.setFirstName(bean.getFirstName());
                    user.setLastName(bean.getLastName());
                    user.setGender(bean.getGender());
                    user.setMobileNo(bean.getMobileNo());
                    user.setDob(bean.getDob());
                    model.update(user);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Profile has been updated Successfully.", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for My Profile.
     *
     * @return path of MyProfileView JSP
     * @author Anmol Kumar Baliyan
     */
    @Override
    protected String getView() {
        return ORSView.MY_PROFILE_VIEW;
    }
}